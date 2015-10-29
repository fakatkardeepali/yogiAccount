package com.helpers

import com.master.AccountLedger
import com.master.VoucherType
import com.sync.ConfigMap
import com.transaction.Voucher
import com.utils.MapUtils
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

/**
 * Created by pc-2 on 8/9/15.
 */
class DomainHelpers {
    static def SRC_PROP_NAME="srcPropName"
    static def DOMAIN_CLASS="domainClass"
    static def QUERY_MAP="queryMap"
    static def CREATE_NEW_INSTANCE="createNewInstance"
    static def CONFIG_MAP="configMap"
    static def PROPERTY_VALUE="\$value"
    static def DEPENDS_PARENT_CONFIG="dependsParentConfig"
    static def METHOD="method"
    static def METHOD_PARAM_VALUE="methodParamValue"
    static def HAS_MANY="hasMany"
    static def PROPERTIES = "properties"
    static def PARENT_PROP_NAME="parentPropName"

    String domainName
    Map domainProperties
    ConfigMap config

    // domainProperties is Json came from ERP
    public DomainHelper(String domainName,Map domainProperties){
        this.domainName = domainName
        this.domainProperties = domainProperties
        this.config = new ConfigMap(domainName)
    }

    private static final log = LogFactory.getLog(this)

    Map getPropertiesForDomainInstance() {
        log.debug("In method getPropertiesForDomainInstance with domain name : ${domainName}")

        Map similarProperties = getSimilarDomainProperties()
        log.debug("Found similar destiantion domain properties : ${similarProperties}")

        Map remainingPropMap = getPropertiesByConfigMap()
        log.debug("Found different destination properties : ${remainingPropMap}")
        return similarProperties + remainingPropMap
    }

    Map getSimilarDomainProperties() {
        Map destinationDomainProperties = config.getDestinationDomainClassProperties()
        log.debug("Checking similar Destination domain properties : ${destinationDomainProperties}")

        def propertyNames = destinationDomainProperties.keySet()

        return domainProperties.inject([:]) { map, entry ->
            if (propertyNames.contains(entry.key)) {
                map[entry.key] = entry.value
            }
            return map;
        }
    }

    Map getPropertiesByConfigMap() {
        log.debug("Populating different domain properties..")

        // simple + direct value
        def simpleProperties = getSimpleProperties()

        def srcPropertiesByQueryMap = getSourcePropertiesByQueryMap()
        log.debug("Found properties having query map : ${srcPropertiesByQueryMap}")

        def srcPropertiesByMethod = getPropertiesByMethod()

        return simpleProperties + srcPropertiesByQueryMap + srcPropertiesByMethod
    }

    def getSimpleProperties(){

        def simpleProperties = config.getProperties().inject([:]) { dMap, entry ->
            if (!(entry.value instanceof Map)) {
                log.debug("finding value of simple property : ${entry.key}")
                dMap[entry.key] = domainProperties[entry.value]

            }else if (entry.value instanceof Map && entry.value.containsKey(PROPERTY_VALUE)) {
                log.debug("finding value of simple property having direct value: ${entry.key}")
//                entry.value[PROPERTY_VALUE]
                dMap[entry.key] = config.getPropertyValue(entry.key)
            }
            return dMap
        }
        log.debug("finally populated simple properties : ${simpleProperties}")
        return simpleProperties
    }

    def getSourcePropertiesByQueryMap() {
        log.debug("Checking if source properties having query map..")
        return config.getProperties().inject([:]) { dMap, entry ->
            if (entry.value instanceof Map && entry.value[QUERY_MAP]) {
                log.debug("This entry is having query map : ${entry.value}")
                dMap[entry.key] = getDomainInstanceByQueryMap(entry.key)
            }
            return dMap
        }
    }

    def getPropertiesByMethod() {
        return config.getProperties().inject([:]){propertiesMap,entry->
            log.debug("Checking if source properties having method : ${entry.key}")

            if (entry?.value instanceof Map && entry?.value?.containsKey(METHOD)) {
                log.debug("This entry is having method : ${entry.value}")
                def srcProp = config.getSourcePropertyValue(entry.key);    // srcProp = ["partyType":"enumDescription"]
                def methodParams = [:]
                if (srcProp instanceof Map) { //check here if it is instance of Map
                    // here value is domain instance property name
                    srcProp.each { key, value ->
                        def propValue = MapUtils.getMapValueByDeepProperty(domainProperties, key)
                        if(value instanceof Map){
                            def srcPropEntry = value.entrySet().first()        //doubt
                            if(srcPropEntry.key.equals("depends") && srcPropEntry.value.equals("QM")){
                                log.debug("Found a property that depends on query map : ${srcProp}")
                                def srcPropertiesByQueryMap = getSourcePropertiesByQueryMap()
                                methodParams[key] = MapUtils.getMapValueByDeepProperty(srcPropertiesByQueryMap, key)
                            }else{
                                log.debug("Invalid config map entry: ${entry.value}")
                            }
                        }else{
                            methodParams[value] = propValue  // PartyJson[partyType][enumDescription]  (e.g.supplier/customer) 
                        }
                    }
                    // "partyType":"enumDescription"

                    log.debug("calling method with params : ${methodParams}")
//                    propertiesMap[entry.key] =  entry.value[METHOD].call(methodParams)
                    propertiesMap[entry.key] =  config.getMethodPropertyValue(entry.key).call(methodParams)

                } else {
                    // here expecting a method with param value
                    log.debug("expecting a method with param value")
//                    def methodParamValue = entry.value[METHOD_PARAM_VALUE]
                    def methodParamValue = config.getMethodParamValue(entry.key)
//                    def method = entry.value[METHOD]
                    def method = config.getMethodPropertyValue(entry.key)
                    propertiesMap[entry.key] = method.call(methodParamValue)
                }
            }
            log.debug("Final properties populated by Method : ${propertiesMap}")
            return  propertiesMap
        }
    }

    Object getDomainInstanceByQueryMap(String propertyName) {

        def entry = config.getSourcePropertyValue(propertyName).entrySet().first()
        Object queryPropertyValue = MapUtils.getMapValueByDeepProperty(domainProperties,entry.key)
        log.debug("Query property value : ${queryPropertyValue}")
        Map finalQueryMap = [:]
        finalQueryMap[entry.value] = queryPropertyValue

        Object domainClass = config.getDomainClass(propertyName)
        log.debug("Finding domainInstance with parameters : ${finalQueryMap}")
        Object domainInstance = domainClass.findWhere(finalQueryMap)
        log.debug("Found domain instance by query map :${domainInstance?.properties}")
        return domainInstance
    }

    //TODO
    def getPropertiesDependsOnParentConfig(){
        def propertiesDependingOnParentMap = config.getProperties().inject([:]) { dMap, entry ->
            if (entry.value instanceof Map && entry.value.containsKey(DEPENDS_PARENT_CONFIG)) {
                log.debug("finding value of property having dependency of parent config map: ${entry.key}")
                Map parentDomainPropertiesByQueryMap = [:]
                def value = parentDomainPropertiesByQueryMap[entry.value[SRC_PROP_NAME]]
                dMap[entry.key] = value
            }
            return dMap
        }
        log.debug("finally populated properties depending upon parent config map : ${propertiesDependingOnParentMap}")
        return propertiesDependingOnParentMap
    }


        def createChildDomainInstance(Map childConfigMap){
        log.debug("New domain instance is to be created for this property : ")
//        Map propertyConfigMap = childConfigMap.value[CONFIG_MAP]                       //get config map
        Map propertyConfigMap = childConfigMap[HAS_MANY][PROPERTIES]                       //get config map

        def childDomainProperties = propertyConfigMap.inject([:]) { resultMap, entry ->
                def simpleProperties = getSimpleProperties()
                def queryMapProperties = getSourcePropertiesByQueryMap()
                def methodProperties = getPropertiesByMethod()
                def parentConfigProperties = getPropertiesDependsOnParentConfig()

                resultMap += simpleProperties + queryMapProperties + methodProperties + parentConfigProperties
                log.debug("Final instance for child domain class : ${resultMap}")
                return resultMap
        }

//        def domainClass = configEntry.value[DOMAIN_CLASS]
        def domainClass = childConfigMap[DOMAIN_CLASS]
        log.debug("Creating child instance for domain class : ${domainClass?.name} using properties : ${childDomainProperties}")
        return domainClass.newInstance(childDomainProperties)
    }

    static def populateChildDomainInstanceProperties(Map configMap,Map domainProperties,Map parentDomainPropertiesByQueryMap){
        log.debug("populating child domain instance properties.")
        def childDomainProperties = configMap.inject([:]) { resultMap, entry ->
            if (entry.value instanceof Map && entry.value[CREATE_NEW_INSTANCE]) {
                log.debug("New domain instance is to be created for this property : ${entry.key}")
                resultMap[entry.key] = createChildDomainInstance(configMap,entry,domainProperties,parentDomainPropertiesByQueryMap)
            }
            return resultMap
        }
        log.debug("finally populated properties for child domain instance : ${childDomainProperties}")
        return childDomainProperties
    }







        def createDomainInstance(String domainName, def domainProperties, boolean isUpdate = false) {
        log.debug("Domain Name from ERP is : ${domainName}")
        def domainInstance;
        switch (domainName) {
            case 'Party':
                Map targetDomainProperties = getPropertiesForDomainInstance()
                log.debug("Total populated target domain properties : ${targetDomainProperties}")

                if (isUpdate) {
                    log.debug("Updating domain instance : ${domainName}")
                    log.debug("Finding Party by id ${domainProperties.id}")
                    domainInstance = AccountLedger.findByPartyId(domainProperties.id)
                    if (domainInstance) {
                        log.debug("Found domain instance from Account : ${domainInstance?.properties}")
                        domainInstance.properties = targetDomainProperties

                    } else {
                        log.debug("Could not find party by id : ${domainProperties.id}")
                    }
                }
                else {
                    log.debug("Creating new instance of Account Ledger with properties : ${targetDomainProperties}")
                    domainInstance = new AccountLedger(targetDomainProperties)
                    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                }
                return domainInstance
                break;

            case 'InvoiceEntry':
                Map targetDomainProperties = getPropertiesForDomainInstance()
                Date date = Date.parse("yyyy-MM-dd",targetDomainProperties?.date)
                targetDomainProperties.date = date
                targetDomainProperties.partyAccount.billDate = date
//                targetDomainProperties.voucherType = VoucherType.findBy
                if(targetDomainProperties?.company?.id){
                    targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company,"Sale")
                }
                else{
                    log.error("Could not determine Voucher type because could not get Company id")
                }
                if(targetDomainProperties?.voucherType?.id && targetDomainProperties?.date){
                    targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id,targetDomainProperties?.date,null)
                }
                else{
                    log.error("Could not get voucher number because could not get voucher type and date")
                }

                log.debug("Total populated target domain properties : ${targetDomainProperties}")


                if (isUpdate) {
                    log.debug("Updating domain instance : ${domainName}")
                    log.debug("Finding Party by id ${domainProperties.id}")
                    domainInstance = Voucher.findById(domainProperties.id)
                    if (domainInstance) {
                        log.debug("Found domain instance from Account : ${domainInstance?.properties}")
                        domainInstance.properties = targetDomainProperties

                    } else {
                        log.debug("Could not find Voucher by id : ${domainProperties.id}")
                    }
                }
                else {
                    log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
                    domainInstance = ConfigMap.getDestinationClassInstance(domainName)
                    populateAssociationProperties(domainName,domainInstance,domainProperties)
                    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                }
                return domainInstance
                break;
        }
    }

    static def createNewChildDomainInstance(Map mainConfig, Map childConfig,Map domainProperties){
        def childDomainProperties = childConfig.inject([:]){resultMap,entry->
            def simpleProperties = getSimpleProperties(childConfig,domainProperties)
            def queryMapProperties = getSourcePropertiesByQueryMap(childConfig,domainProperties)

            def methodProperties = getPropertiesByMethod(childConfig,domainProperties,queryMapProperties)

            def parentConfigProperties = getPropertiesDependsOnParentConfig(childConfig,mainConfig,parentDomainPropertiesByQueryMap)

            resultMap += simpleProperties + queryMapProperties + methodProperties + parentConfigProperties
            log.debug("Final instance for child domain class : ${resultMap}")
            return resultMap
        }

    }

    static def populateAssociationProperties(String domainName, Object domainInstance,Map domainProperties) {
        def domainConfigMap = ConfigMap.getProperties(domainName)

        domainConfigMap.each {propertyName,value->
            if(value instanceof Map && value[CREATE_NEW_INSTANCE] && value[HAS_MANY]){
                def childConfigMaps = value[HAS_MANY]
                childConfigMaps.each {childConfigMap->
                    Map actualConfigMap = childConfigMap[CONFIG_MAP]
                    def childDomainInstance = createChildDomainInstance(actualConfigMap)
                }
            }
        }

    }

    /*
    Returns a Map of domain properties. If a property is domain instance then its properties are collected.
    e.g. accountLedger.properties

    This method is used instead of converting a domain instance into JSON.
*/

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

    def getDomainSubproperty(String string){
        def deepKeys = string[PARENT_PROP_NAME].split("\\.")                                   //e.g. lastUpdatedBy.mailId

//        return config.getPropertyValue(deepKeys[0])[deepKeys[1]]
//        return deepKeys.inject (map){val,key->
//            return val[key]
//        }
    }

    def getPropertyValueFromConfigMap(String propertyName){
        def result
        def propertyValue = config.getPropertyValue(propertyName)

        //case1 :  propertyValue is a map and contains key PROPERTY_VALUE
        if(propertyValue instanceof Map){

            //case2: propertyValue contains key PROPERTY_VALUE
            if(propertyValue.containsKey(PROPERTY_VALUE)){
                result = propertyValue[PROPERTY_VALUE]
            }

            //case3: propertyValue contains key QUERY_MAP
            else if(propertyValue[QUERY_MAP]){
                result =  getDomainInstanceByQueryMap(propertyName)
            }

            //case4 : propertyValue contains key METHOD
            else if(propertyValue.containsKey(METHOD)){

                log.debug("This entry is having method : ${propertyValue}")
                def srcProp = propertyValue[SRC_PROP_NAME]    // srcProp = ["partyType":"enumDescription"]
                def methodParams = [:]
                if (srcProp instanceof Map) { //check here if it is instance of Map
                    // here value is domain instance property name
                    srcProp.each { key, value ->
                        def propValue = MapUtils.getMapValueByDeepProperty(domainProperties, key)
                        if(value instanceof Map){
                            def srcPropEntry = value.entrySet().first()        //doubt
                            if(srcPropEntry.key.equals("depends") && srcPropEntry.value.equals("QM")){
                                log.debug("Found a property that depends on query map : ${srcProp}")
                                def srcPropertiesByQueryMap = getSourcePropertiesByQueryMap()
                                methodParams[key] = MapUtils.getMapValueByDeepProperty(srcPropertiesByQueryMap, key)
                            }else{
                                log.debug("Invalid config map entry: ${entry.value}")
                            }
                        }else{
                            methodParams[value] = propValue  // PartyJson[partyType][enumDescription]  (e.g.supplier/customer) 
                        }
                    }

                    log.debug("calling method with params : ${methodParams}")
                    result =  config.getMethodPropertyValue(propertyName).call(methodParams)

                } else {
                    log.debug("expecting a method with param value")
                    def methodParamValue = config.getMethodParamValue(propertyName)
                    def method = config.getMethodPropertyValue(propertyName)
                    result = method.call(methodParamValue)
                }

            }

            //case5 : propertyValue depends on parent config
            else if(propertyValue.containsKey(DEPENDS_PARENT_CONFIG)){
                log.debug("finding value of property having dependency of parent config map: ${propertyName}")
                Map parentDomainPropertiesByQueryMap = [:]
                def value = parentDomainPropertiesByQueryMap[propertyValue[SRC_PROP_NAME]]
                result = value
            }

            //case6 : propertyValue has key CREATE_NEW_INSTANCE
            else if(propertyValue[CREATE_NEW_INSTANCE]){
                log.debug("New domain instance is to be created for this property : ${propertyName}")
                result = createChildDomainInstance(propertyValue)
            }

            //case7 : propertyValue has key PARENT_PROP_NAME
            else if(propertyValue.containsKey(PARENT_PROP_NAME)){
                   Map subPropertyValue = config.getChildProperties(propertyName)
                   String parentPropertyName = subPropertyValue[PARENT_PROP_NAME]
                   def parentValue = getPropertyValueFromConfigMap(parentPropertyName)
            }

        }
        //case :  propertyValue is not a map
        else if(!(propertyValue instanceof Map)){
            result =  domainProperties[propertyValue]                // want to use source domainProperties here
        }
        return result
    }
}
