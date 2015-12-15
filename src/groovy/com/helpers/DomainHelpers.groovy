package com.helpers

import com.master.AccountLedger
import com.master.VoucherType
import com.sync.ConfigMap
import com.transaction.Voucher
import com.utils.ClassUtils
import com.utils.MapUtils
import com.utils.StringUtils
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

/**
 * Created by pc-2 on 8/9/15.
 */
class DomainHelpers {
    static def SRC_PROP_NAME = "srcPropName"
    static def DOMAIN_CLASS = "domainClass"
    static def QUERY_MAP = "queryMap"
    static def CREATE_NEW_INSTANCE = "createNewInstance"
    static def CONFIG_MAP = "configMap"
    static def PROPERTY_VALUE = "\$value"
    static def DEPENDS_PARENT_CONFIG = "dependsParentConfig"
    static def METHOD = "method"
    static def METHOD_PARAM_VALUE = "methodParamValue"
    static def HAS_MANY = "hasMany"
    static def PROPERTIES = "properties"
    static def PARENT_PROP_NAME = "parentPropName"
    static String MAIN_DOMAIN_INSTANCE = "mainDomainInstance"
    static String DEPENDENT_DOMAIN_INSTANCES = "dependentDomainInstances"


    String domainName
    Map sourceDomainProperties
    ConfigMap config

    // sourceDomainProperties is Json came from ERP
    public DomainHelpers(String domainName, Map sourceDomainProperties) {
        this.domainName = domainName
        this.sourceDomainProperties = sourceDomainProperties
        this.config = new ConfigMap(domainName)
    }

    private static final log = LogFactory.getLog(this)

    Object initialiseDomainInstanceByDomainProperties() {
        log.debug("In method initialiseDomainInstanceByDomainProperties with domain name : ${domainName}")

        Map similarProperties = getSimilarDomainProperties()
        log.debug("Found similar destiantion domain properties : ${similarProperties}")

        def domainInstances = initialiseDomainInstanceByConfig()

        if (domainInstances instanceof Map) {
            def domainInstance = domainInstances[MAIN_DOMAIN_INSTANCE]
            log.debug("Found domain instance properties by config map: ${domainInstance.properties}")

            domainInstance.properties += similarProperties
        } else {
            domainInstances.properties += similarProperties
        }
        return domainInstances
    }

    /** assuming that similar properties have direct values, so no need to find their values*/
    Map getSimilarDomainProperties() {
        def configMapProperties = config.getConfigPropertyList()
        Map destinationDomainProperties = config.getDomainClassProperties()
        log.debug("Checking similar Destination domain properties : ${destinationDomainProperties}")

        def propertyNames = destinationDomainProperties.keySet()

        return sourceDomainProperties.inject([:]) { map, entry ->
            if (propertyNames.contains(entry.key) && !configMapProperties.contains(entry.key)) {
                map[entry.key] = entry.value
            }
            return map;
        }
    }

    Object getDomainInstanceByQueryMap(String propertyName) {
        Map finalQueryMap = [:]
        config.getPropertySourceName(propertyName).each { key, value ->
            if (key.equals(ConfigMap.PROPERTY_VALUE)) {     //pending
                // here expecting value as map having key as property name and value as actual value
                value.each { subKey, subValue ->
                    finalQueryMap[subKey] = subValue
                }
            }
            else if(value instanceof Map && value.containsKey("depends")){
                finalQueryMap[key] = getPropertyValueFromConfigMap(key)
            }
            else {
                Object queryPropertyValue = MapUtils.getMapValueByDeepProperty(sourceDomainProperties, key)     //doubt for splitting single value
                log.debug("Query property value : ${queryPropertyValue}")
                finalQueryMap[value] = queryPropertyValue
            }
        }
        Object domainClass = config.getPropertyDomainClass(propertyName)
        log.debug("Finding domainInstance with parameters : ${finalQueryMap}")
        Object domainInstance = domainClass.findWhere(finalQueryMap)
        log.debug("Found domain instance by query map :${domainInstance?.properties}")
        return domainInstance
    }

    Object getPropertyValueByMethod(String propertyName) {
        Object result;
        def methodPropertyValue = config.getPropertyMethod(propertyName)

        log.debug("Found property having method : ${methodPropertyValue}")
        def srcProp = methodPropertyValue[SRC_PROP_NAME]    // srcProp = ["partyType":"enumDescription"]
        def methodParams = [:]

        def method = config.getPropertyMethod(propertyName)

        if (method instanceof String) {
            def domainClassInstance = config.getPropertyDomainClass(propertyName).newInstance()
            Object[] methodArguments = new Object[srcProp.size()];
            // here srcProp keys are 0,1,2,etc, so need to be sorted in order to pass the method params in the correct order
            srcProp.keySet().sort().each { index ->
                // indexValue is a map, eg. [propertyName:"voucherType",subPropertyName:"id", depends: "Self"]
                int intIndex = Integer.parseInt(index.toString())
                def indexValue = srcProp[index]

                if (indexValue instanceof Map) {
                    if (indexValue.containsKey(ConfigMap.DEPENDS_SELF)) {
                        def propName = indexValue[ConfigMap.PROPERTY_NAME]
                        def propValue = getPropertyValueFromConfigMap(propName, config)

                        if (indexValue[ConfigMap.SUB_PROPERTY_NAME]) {
                            def subPropertyName = indexValue[ConfigMap.SUB_PROPERTY_NAME]
                            //TODO  here assuming that property value is domain instance, so handle the case where property value is not domain instance
                            propValue = propValue."${subPropertyName}"
                        }
                        methodArguments[intIndex] = propValue
                    }
                } else if (indexValue.containsKey(ConfigMap.PROPERTY_VALUE)) {
                    methodArguments[intIndex] = indexValue[ConfigMap.PROPERTY_VALUE]
                }
            }

            def methodToInvoke = ClassUtils.getMethodByName(domainClassInstance.class, method)

            if (methodToInvoke) {
                result = methodToInvoke.invoke(domainClassInstance, methodArguments)
            } else {
                log.error("Failed to find method:${method} on instance:${domainClassInstance} ")
            }
        } else {
            if (srcProp instanceof Map) { //check here if it is instance of Map
                // here value is domain instance property name
                srcProp.each { key, value ->
                    if (value instanceof Map) {
                        def srcPropEntry = value.entrySet().first()        //doubt
                        if (srcPropEntry.key.equals("depends") && srcPropEntry.value.equals("Self")) {
                            log.debug("Found a property that depends on selt config map : ${srcProp}")
                            methodParams[key] = getPropertyValueFromConfigMap(key)
                        } else {
                            log.debug("Invalid config map entry: ${entry.value}")
                        }
                    } else {
                        log.debug("Getting domain instance name separated from property : ${key}")
                        def propValue = MapUtils.getMapValueByDeepProperty(sourceDomainProperties, key)
                        methodParams[value] = propValue
                        // PartyJson[partyType][enumDescription]  (e.g.supplier/customer)
                    }
                }

                log.debug("calling method with params : ${methodParams}")
                result = method.call(methodParams)

            } else {
                log.debug("expecting a method with param value")
                def methodParamValue = config.getPropertyMethodParameter(propertyName)
                result = method.call(methodParamValue)
            }
        }
        return result
    }

    def getPropertyValueFromConfigMap(String propertyName, ConfigMap config = this.config) {
        def result
        def propertyValue = config.getPropertyValue(propertyName)

        //case1 :  propertyValue is a map, complex property
        if (propertyValue instanceof Map) {

            //case2: propertyValue contains key PROPERTY_VALUE, direct value
            if (propertyValue.containsKey(PROPERTY_VALUE)) {
                log.debug("Found property having key PROPERTY_VALUE : ${propertyValue}")
                result = propertyValue[PROPERTY_VALUE]
            }

            //case3: propertyValue contains key QUERY_MAP
            else if (propertyValue[QUERY_MAP]) {
                log.debug("Found property having query map : ${propertyValue}")
                result = getDomainInstanceByQueryMap(propertyName)
            }

            //case4 : propertyValue contains key METHOD
            else if (propertyValue.containsKey(METHOD)) {
                log.debug("Found property having method : ${propertyValue}")
                def srcProp = propertyValue[SRC_PROP_NAME]    // srcProp = ["partyType":"enumDescription"]
                def methodParams = [:]

                def method = config.getPropertyMethod(propertyName)

                if(method instanceof String){
                    def domainClassInstance = config.getPropertyDomainClass(propertyName).newInstance()
                    Object[] methodArguments = new Object[srcProp.size()]
//                    def methodArguments = []
                    // here srcProp keys are 0,1,2,etc, so need to be sorted in order to pass the method params in the correct order
                    srcProp.keySet().sort().each{index->
                        // indexValue is a map, eg. [propertyName:"voucherType",subPropertyName:"id", depends: "Self"]
                        int intIndex = Integer.parseInt(index.toString())
                        def indexValue = srcProp[index]                                                   //e.g.[propertyName: "voucherType", subPropertyName: "id", dependsSelf: true]

                        if(indexValue instanceof Map){
                            if(indexValue.containsKey(ConfigMap.DEPENDS_SELF)){                          //e.g. dependsSelf: true]
                                def propName = indexValue[ConfigMap.PROPERTY_NAME]                       //e.g. [propertyName: "voucherType"
                                def propValue = getPropertyValueFromConfigMap(propName,config)           //e.g. getPropertyValueFromConfigMap(voucherType,config)

                                if(indexValue[ConfigMap.SUB_PROPERTY_NAME]){
                                    def subPropertyName = indexValue[ConfigMap.SUB_PROPERTY_NAME]
                                    //TODO  here assuming that property value is domain instance, so handle the case where property value is not domain instance
                                    propValue = propValue?."${subPropertyName}"
                                }
                                methodArguments[intIndex] = propValue
                                log.debug("Method Arguments ${intIndex} : ${methodArguments}")
                            }
                        }else if(indexValue.containsKey(ConfigMap.PROPERTY_VALUE)){
                            methodArguments[intIndex] = indexValue[ConfigMap.PROPERTY_VALUE]
                            log.debug("Method Arguments ${intIndex} : ${methodArguments}")
                        }
                    }

                    def methodToInvoke = ClassUtils.getMethodByName(domainClassInstance.class,method)

                    if(methodToInvoke){
                        result = methodToInvoke.invoke(domainClassInstance,methodArguments)
                    }else{
                        log.error("Failed to find method:${method} on instance:${domainClassInstance} ")
                    }
                }else{
                    if (srcProp instanceof Map) { //check here if it is instance of Map
                        // here value is domain instance property name
                        srcProp.each { key, value ->
                            if (value instanceof Map) {
                                def srcPropEntry = value.entrySet().first()        //doubt
                                if (srcPropEntry.key.equals("depends") && srcPropEntry.value.equals("Self")) {
                                    log.debug("Found a property that depends on selt config map : ${srcProp}")
                                    methodParams[key] = getPropertyValueFromConfigMap(key)
                                } else {
                                    log.debug("Invalid config map entry: ${entry.value}")
                                }
                            } else {
                                log.debug("Getting domain instance name separated from property : ${key}")
                                def propValue = MapUtils.getMapValueByDeepProperty(sourceDomainProperties, key)
                                methodParams[value] = propValue
                                // PartyJson[partyType][enumDescription]  (e.g.supplier/customer) 
                            }
                        }

                        log.debug("calling method with params : ${methodParams}")
                        result = method.call(methodParams)

                    } else {
                        log.debug("expecting a method with param value")
                        def methodParamValue = config.getPropertyMethodParameter(propertyName)
                        result = method.call(methodParamValue)
                    }
                }
            }

            //case5 : propertyValue depends on parent config
            else if (propertyValue.containsKey(DEPENDS_PARENT_CONFIG)) {
                log.debug("finding value of property having dependency of parent config map: ${propertyName}")
                // TODO if property name is different from original property then it should be defined with srcPropName eg. partyName:[srcPropName:"name",dependsParentConfig: true],
                result = getPropertyValueFromConfigMap(propertyName)
            }

            //case6 : propertyValue has key CREATE_NEW_INSTANCE
            else if (propertyValue[CREATE_NEW_INSTANCE] && propertyValue[ConfigMap.HAS_MANY]) {
                log.debug("Found property value having key CREATE_NEW_INSTANCE and HAS_MANY")
                // this case is handled in initialiseDomainInstanceByConfig method since it requires
                // to add a new domain instance to has many list
            }

            //case7 : propertyValue has key PARENT_PROP_NAME
            else if (propertyValue.containsKey(PARENT_PROP_NAME)) {
                log.debug("Found property that depends on parent property : ${propertyValue}")
                String parentPropertyName = propertyValue[PARENT_PROP_NAME]
                //TODO handle the case where parent values is not domain class instance
                // here parent value is a domain class instance
                def parentValue = getPropertyValueFromConfigMap(parentPropertyName)
                def subPropertyName = propertyValue[ConfigMap.SUB_PROPERTY_NAME]
                result = parentValue."${subPropertyName}"
            }

            //case8: propertyValue has key dateFormat
            else if(propertyValue.containsKey(ConfigMap.DATE_FORMAT)){
                log.debug("Found property with date format : ${propertyName}")
//                def dateProperty = getPropertyValueFromConfigMap(propertyValue[SRC_PROP_NAME])
//                result = Date.parse(ConfigMap.DATE_FORMAT,dateProperty)
                result = new Date()
            }
        }
        //case :  propertyValue is not a map, simple property
        else if (!(propertyValue instanceof Map)) {
            log.debug("Found simple property : ${propertyValue}")
            result = sourceDomainProperties[propertyValue]
        }

        /*if(result == null){
            log.error("Failed to get value of property:${propertyName} from config map")
        }*/
        return result
    }

    def initialiseDomainInstanceByConfig() {
        Map domainInstances = [:]
        def domainInstance = config.getDomainClassInstance()    // target domain class instance
        log.debug("Target domain class : ${domainInstance?.class}")

        Map configProperties = config.getProperties()
        log.debug("Initializing domain instance by config map : ${configProperties}")

        configProperties.each { propertyName, propertyValue ->
            if (propertyValue instanceof Map && propertyValue[ConfigMap.CREATE_NEW_INSTANCE] && propertyValue[ConfigMap.HAS_MANY]) {
                log.debug("Found child properties having keys CREATE_NEW_INSTANCE and HAS_MANY : ${propertyValue}")
                // here expecting propertyValue as a config
                def childConfig = new ConfigMap(propertyName, propertyValue)
                def childDomainInstance = createDomainInstanceByConfig(childConfig)
                log.debug("Adding child domain properties for class : ${childDomainInstance} to parent class")
                domainInstance."addTo${StringUtils.capitalizeFirstLetter(propertyName)}"(childDomainInstance)
            } else if (propertyName.equals(ConfigMap.AFTER_INSERT)) {
                // expecting propertyValue as list of config maps
                def dependentDomainInstances = propertyValue.inject([]) { list, dependentInstanceConfig ->

                    if(dependentInstanceConfig.containsKey(ConfigMap.AFTER_INSERT_METHOD)){
                        def dependentInstancePropertyValue = dependentInstanceConfig[ConfigMap.AFTER_INSERT_METHOD]
                        def srcProp = dependentInstancePropertyValue[SRC_PROP_NAME]    // srcProp = ["partyType":"enumDescription"]
                        def method = dependentInstancePropertyValue[METHOD]

                        def domainClassInstance = dependentInstancePropertyValue[DOMAIN_CLASS].newInstance()
                        Object[] methodArguments = new Object[srcProp.size()]

                        srcProp.keySet().sort().each{index->
                            int intIndex = Integer.parseInt(index.toString())
                            def indexValue = srcProp[index]

                            if(indexValue instanceof Map){
                                if(indexValue.containsKey(ConfigMap.DEPENDS_PARENT_CONFIG)){

                                    def propName = indexValue[ConfigMap.PROPERTY_NAME]                       //e.g. [propertyName: "voucherType"
                                    def propValue = getPropertyValueFromConfigMap(propName,config)           //e.g. getPropertyValueFromConfigMap(voucherType,config)
                                    methodArguments[intIndex] = propValue
                                    log.debug("Method Arguments ${intIndex} : ${methodArguments}")
                                }
                            }else if(indexValue.containsKey(ConfigMap.PROPERTY_VALUE)){
                                methodArguments[intIndex] = indexValue[ConfigMap.PROPERTY_VALUE]
                                log.debug("Method Arguments ${intIndex} : ${methodArguments}")
                            }

                        }
                        def methodToInvoke = ClassUtils.getMethodByName(domainClassInstance.class,method)
                        if(method=="parametersInsert"){
                            Voucher.parametersInsert(methodArguments[0],methodArguments[1])
                        }
                        if(methodToInvoke){
                            try{
//                                methodToInvoke.invoke(domainClassInstance,methodArguments)
                                log.debug("method invoked : ${method}")
                            }
                            catch (Exception e){
                                e.printStackTrace()
                            }


                        }else{
                            log.error("Failed to find method:${method} on instance:${domainClassInstance} ")
                        }
                    }
                    else{
                        ConfigMap configMap = new ConfigMap(null,dependentInstanceConfig)
                        list.add(createDomainInstanceByConfig(configMap,domainInstance))
                        return list
                    }
                    return list
                }
                domainInstances[DEPENDENT_DOMAIN_INSTANCES] = dependentDomainInstances
            } else {
                domainInstance."${propertyName}" = getPropertyValueFromConfigMap(propertyName)
            }
        }
        domainInstances[MAIN_DOMAIN_INSTANCE] = domainInstance
        return domainInstances
    }

    def createDomainInstanceByConfig(ConfigMap config, def parentDomainInstance = null) {
        def domainInstance = config.getDomainClassInstance()
        def properties = config.getProperties()
        properties.each { configPropertyName, propertyValue ->
            if (propertyValue instanceof Map && propertyValue.containsKey(ConfigMap.DEPENDS_PARENT_DOMAIN_INSTANCE)) {
                // handled case dependsParentDomainInstance:true here since we require parent domain instance which is available here
                domainInstance."${configPropertyName}" = parentDomainInstance
            } else {
                domainInstance."${configPropertyName}" = getPropertyValueFromConfigMap(configPropertyName, config)
            }
        }
        return domainInstance
    }

    def createDomainInstance(boolean isUpdate = false) {
        log.debug("Domain Name from ERP is : ${domainName}")
        def domainInstance;
        switch (domainName) {
            case 'Party':
                Map targetDomainProperties = getPropertiesForDomainInstance()
                log.debug("Total populated target domain properties : ${targetDomainProperties}")

                if (isUpdate) {
                    log.debug("Updating domain instance : ${domainName}")
                    log.debug("Finding Party by id ${sourceDomainProperties.id}")
                    domainInstance = AccountLedger.findByPartyId(sourceDomainProperties.id)
                    if (domainInstance) {
                        log.debug("Found domain instance from Account : ${domainInstance?.properties}")
                        domainInstance.properties = targetDomainProperties

                    } else {
                        log.debug("Could not find party by id : ${sourceDomainProperties.id}")
                    }
                } else {
                    log.debug("Creating new instance of Account Ledger with properties : ${targetDomainProperties}")
                    domainInstance = initialiseDomainInstanceByDomainProperties()
                    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                }
                return domainInstance
                break;

            case 'InvoiceEntry':
                Map targetDomainProperties = getPropertiesForDomainInstance()
                Date date = Date.parse("yyyy-MM-dd", targetDomainProperties?.date)
                targetDomainProperties.date = date
                targetDomainProperties.partyAccount.billDate = date
//                targetDomainProperties.voucherType = VoucherType.findBy
                if (targetDomainProperties?.company?.id) {
                    targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company, "Sale")
                } else {
                    log.error("Could not determine Voucher type because could not get Company id")
                }
                if (targetDomainProperties?.voucherType?.id && targetDomainProperties?.date) {
                    targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id, targetDomainProperties?.date, null)
                } else {
                    log.error("Could not get voucher number because could not get voucher type and date")
                }

                log.debug("Total populated target domain properties : ${targetDomainProperties}")

                if (isUpdate) {
                    log.debug("Updating domain instance : ${domainName}")
                    log.debug("Finding Party by id ${sourceDomainProperties.id}")
                    domainInstance = Voucher.findById(sourceDomainProperties.id)
                    if (domainInstance) {
                        log.debug("Found domain instance from Account : ${domainInstance?.properties}")
                        domainInstance.properties = targetDomainProperties

                    } else {
                        log.debug("Could not find Voucher by id : ${sourceDomainProperties.id}")
                    }
                } else {
                    log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
                    domainInstance = initialiseDomainInstanceByDomainProperties()
                }
                return domainInstance
                break;
        }
    }

/*
Returns a Map of domain properties. If a property is domain instance then its properties are collected.
e.g. accountLedger.properties
This method is used instead of converting a domain instance into JSON.*/

    static def buildPropertiesMap(Object domainInstance) {
        return domainInstance.properties.inject([:]) { map, propEntry ->
            log.debug("Processing entry ${propEntry},class  ${propEntry.value.class.name}")
            map[propEntry.key] = propEntry.value
            if (DomainClassArtefactHandler.isDomainClass(propEntry.value.class)) {
                log.debug("${propEntry.value} is domain instance")
                map[propEntry.key] = propEntry.value.properties
            }
            return map
        }
    }
}