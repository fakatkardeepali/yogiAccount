package com.helpers

import com.common.AccountFlag
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.system.User
import com.transaction.PartyAccount
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
    private static final log = LogFactory.getLog(this)
//    static Logger log = Logger.getLogger(getClass())
    static Map getConfigMapForDomain(String domainName) {

        //in config map, key is destination property whereas value is source property
        log.debug("Getting config map for ERP domain : ${domainName}")
        switch (domainName) {
            case "Party":
                return [
                        address      : "officeAddress",
                        telephoneNo  : "telephoneNo1",
                        partyId      : "id",
                        cstTin       : "cstNo",
                        salesTaxNo   : "serviceTaxNo",
                        company      : [domainClass: Company, srcPropName: ["company.regNo":"registrationNo"],queryMap: true],
                        lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId":"username"],queryMap: true],
                        underGroup   : [domainClass: AccountGroup, srcPropName: ["partyType.enumDescription": "name", "company": [depends:"QM"]], method: AccountGroup.findByPartyTypeAndCompany]
                       ]
                break
            case "Tax":
                return [
                        name: "taxName"
//                        underGroup: here undergroup should be "Duties And Taxes"
                       ]
                break

            case "InvoiceEntry":
                return [
                        voucherNo   : "",
                        date        : "invoiceDate",
                        referenceNo : "challanNo",
                        narration   : "description",
                        amount      : "grandTotal",
                        amountStatus: [$value: "Dr"],
                        createNewInstance:true,
                        partyName   : [domainClass: AccountLedger,srcPropName: ["customer.name":"name"],queryMap: true],
                        company     : [domainClass: Company, srcPropName: ["company.regNo":"registrationNo"],queryMap: true],
                        lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId":"username"],queryMap: true],
                        partyAccount : [domainClass: PartyAccount,createNewInstance:true,
                                        configMap:[
                                                   partyName:[srcPropName:"partyName",$dependsParentConfig:true],
                                                   typeOfRef:[method:AccountFlag.findByNameClosure,methodParamValue:"New Ref."],
                                                   billNo: "invoiceNo",
                                                   billDate: "invoiceDate",
                                                   crDays: [selfPropName: "partyName.creditDays"],
                                                   amount: "grandTotal",
                                                   amountStatus: [$value:"Dr"] ,
                                                   narration: [$value:""],
                                                   remainAmount: "grandTotal",
                                                   lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId":"username"],queryMap: true]
                                                  ],
                                       ],


                       ]
                break

            case "InvoiceEntryLC":
                return [
                        voucherNo   : "invoiceNo",
                        date        : "invoiceDate",
                        referenceNo : "challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount      : "grandTotal",
                        amountStatus: "Dr",
                       ]
                break

            case "ProFormaInvoice":
                return [
                        voucherNo   : "invoiceNo",
                        date        : "invoiceDate",
                        referenceNo : "challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount      : "grandTotal",
                        amountStatus: "Dr",
                       ]
                break
            case "PurchaseReturn":
                return [
                        voucherNo   : "invoiceNo",
                        date        : "invoiceDate",
                        referenceNo : "challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount      : "grandTotal",
                        amountStatus: "Dr",
                       ]
                break
            case "BillPassing":
                return [
                        voucherNo   : "voucherNo",
                        date        : "billDate",
                        referenceNo : "billNo",
//                        voucherType: here voucherType should be "Purchase"
//                        partyName: object of Party (Ledger in Account)
                        amount      : "grandTotal",
                        amountStatus: "Cr",
                       ]
                break


        }
    }

    static def createDomainInstance(String domainName, def domainProperties, boolean isUpdate = false) {
        log.debug("Domain Name from ERP is : ${domainName}")
        switch (domainName) {
            case 'Party':
                Map targetDomainProperties = getPropertiesForDomainInstance(domainName, domainProperties, new AccountLedger().properties)
                log.debug("Total populated target domain properties : ${targetDomainProperties}")
                def domainInstance;
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
                Map targetDomainProperties = getPropertiesForDomainInstance(domainName, domainProperties, new Voucher().properties)
                log.debug("Total populated target domain properties : ${targetDomainProperties}")
                targetDomainProperties.date = Date.parse("yyyy-MM-dd",targetDomainProperties?.date)
//                targetDomainProperties.voucherType = VoucherType.findBy
                if(targetDomainProperties?.company?.id){
                    targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company,"Sale")
                }
                else{
                    log.error("Could not determine Voucher type because could not get Company id not")
                }
                if(targetDomainProperties?.voucherType?.id && targetDomainProperties?.date){
                    targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id,targetDomainProperties?.date,null)
                }
                else{
                    log.error("Could not get voucher number because could not get voucher type and date")
                }

                //make new instance of Party Account And Voucher Bill Details
//                  targetDomainProperties?.addToPartyAccount(new PartyAccount(company: targetDomainProperties?.company,
//                            partyName: targetDomainProperties?.partyName,
//                            typeOfRef: AccountFlag.findByName("New Ref."),
//                            billNo: domainProperties?.invoiceNo?:"",
//                            billDate: targetDomainProperties?.date,
//                            crDays: targetDomainProperties?.partyName?.creditDays?:0,
//                            amount: targetDomainProperties?.amount,
//                            amountStatus: "Dr",
//                            narration: "",
//                            remainAmount: targetDomainProperties?.amount,
//                            lastUpdatedBy: targetDomainProperties?.lastUpdatedBy))

//                log.debug("addToPartyAccount : ${targetDomainProperties}")
//

//                new VoucherBillDetails(company: targetDomainProperties?.company,
//                        partyName: targetDomainProperties?.partyName,
//                        typeOfRef: AccountFlag.findByName("New Ref."),
//                        billNo: domainProperties?.invoiceNo?:"",
//                        billDate: targetDomainProperties?.date,
//                        crDays: targetDomainProperties?.partyName?.creditDays?:0,
//                        amount: targetDomainProperties?.amount,
//                        amountStatus: "Dr",
//                        narration: "",
//                        lastUpdatedBy: targetDomainProperties?.lastUpdatedBy)


//                //make new instance of voucher against each ledger id
//                //netAmountLedgerId:netAmount,packingLedgerId:packingAmount,freightLedgerId:freightAmount,insuranceLedgerId:insuranceAmount
//                if(domainProperties?.netAmountLedgerId){
//                    new Voucher(
//                            partyName: AccountLedger.get(domainProperties?.netAmountLedgerId as Long),
//                            amount: domainProperties.netAmount as BigDecimal,
//                            rate: 0,
//                            amountStatus: targetDomainProperties ? Voucher.childStatus(targetDomainProperties) : "",
//                            narration: "",
//                            lastUpdatedBy: targetDomainProperties?.lastUpdatedBy,
//                            company: targetDomainProperties?.company,
//                            voucher: targetDomainProperties as Voucher,
//                            date: targetDomainProperties?.date)
//                }


            log.debug("PartyAccountDetails : ${targetDomainProperties?.partyAccount}")

                def domainInstance;
                if (isUpdate) {
                    log.debug("Updating domain instance : ${domainName}")
                    log.debug("Finding Party by id ${domainProperties.id}")
                    domainInstance = Voucher.findById(domainProperties.id)
                    if (domainInstance) {
                        log.debug("Found domain instance from Account : ${domainInstance?.properties}")
                        domainInstance.properties = targetDomainProperties

//                        //add party account instance
//                        domainInstance.addToPartyAccount(new PartyAccount(company: targetDomainProperties?.company,
//                                partyName: targetDomainProperties?.partyName,
//                                typeOfRef: AccountFlag.findByName("New Ref."),
//                                billNo: domainProperties?.invoiceNo?:"",
//                                billDate: targetDomainProperties?.date,
//                                crDays: targetDomainProperties?.partyName?.creditDays?:0,
//                                amount: targetDomainProperties?.amount,
//                                amountStatus: "Dr",
//                                narration: "",
//                                remainAmount: targetDomainProperties?.amount,
//                                lastUpdatedBy: targetDomainProperties?.lastUpdatedBy))
//
//                        //add voucher bill details instance
//                        domainInstance.addToVoucherBillDetails(new VoucherBillDetails(company: targetDomainProperties?.company,
//                        partyName: targetDomainProperties?.partyName,
//                        typeOfRef: AccountFlag.findByName("New Ref."),
//                        billNo: domainProperties?.invoiceNo?:"",
//                        billDate: targetDomainProperties?.date,
//                        crDays: targetDomainProperties?.partyName?.creditDays?:0,
//                        amount: targetDomainProperties?.amount,
//                        amountStatus: "Dr",
//                        narration: "",
//                        lastUpdatedBy: targetDomainProperties?.lastUpdatedBy))
//
//                        //add voucher details Instance
//
//                        domainInstance.addToVoucherDetails(new Voucher(
//                            partyName: AccountLedger.get(domainProperties?.netAmountLedgerId as Long),
//                            amount: domainProperties.netAmount as BigDecimal,
//                            rate: 0,
//                            amountStatus: targetDomainProperties ? Voucher.childStatus(targetDomainProperties) : "",
//                            narration: "",
//                            lastUpdatedBy: targetDomainProperties?.lastUpdatedBy,
//                            company: targetDomainProperties?.company,
//                            voucher: targetDomainProperties as Voucher,
//                            date: targetDomainProperties?.date)
//                        )

                    } else {
                        log.debug("Could not find Voucher by id : ${domainProperties.id}")
                    }
                }
                else {
                    log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
                    domainInstance = new Voucher(targetDomainProperties)
                    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                }
                return domainInstance
                break;
        }
    }

    static Map getPropertiesForDomainInstance(String domainName, def domainProperties, Map destinationProperties) {
        log.debug("In method getPropertiesForDomainInstance with domain name : ${domainName}")
        Map similarProperties = getSimilarDestinationDomainPropertiesMap(domainProperties, destinationProperties)
        log.debug("Found similar destiantion domain properties : ${similarProperties}")
        Map configMap = getConfigMapForDomain(domainName)
        Map remainingPropMap = populatePropertiesByConfigMap(configMap, domainProperties)
        log.debug("Found different destination properties : ${remainingPropMap}")
        return similarProperties + remainingPropMap
    }

    static Map getSimilarDestinationDomainPropertiesMap(Map sourceDomainProperties, Map destinationDomainProperties) {
        log.debug("Checking similar Destination domain properties : ${destinationDomainProperties}")
        def propertyNames = destinationDomainProperties.keySet()

        return sourceDomainProperties.inject([:]) { map, entry ->
            if (propertyNames.contains(entry.key)) {
                map[entry.key] = entry.value
            }
            return map;
        }
    }


    // domainProperties is Json came from ERP
    static Map populatePropertiesByConfigMap(Map configMap, Map domainProperties) {

        log.debug("Populating different domain properties..")

        // simple + direct value
        def simpleProperties = populateSimpleProperties(configMap,domainProperties)

        def srcPropertiesByQueryMap = populateSourcePropertiesHavingQueryMap(configMap, domainProperties)
        log.debug("Found properties having query map : ${srcPropertiesByQueryMap}")

        def srcPropertiesByMethod = populatePropertiesByMethod(configMap,domainProperties,srcPropertiesByQueryMap)

        def srcPropertiesWithChildDomainInstanceProperties = populateChildDomainInstanceProperties(configMap,domainProperties)

        return simpleProperties + srcPropertiesByQueryMap + srcPropertiesByMethod + srcPropertiesWithChildDomainInstanceProperties
    }

    static Map populateSourcePropertiesHavingQueryMap(Map configMap, Map srcMap) {
        log.debug("Checking if source properties having query map..")
        return configMap.inject([:]) { dMap, entry ->
            if (entry.value instanceof Map && entry.value[QUERY_MAP]) {
                log.debug("This entry is having query map : ${entry.value}")
                dMap[entry.key] = getDomainInstanceByQueryMap(entry.key, configMap, srcMap)
            }
            return dMap
        }
    }

    static def populatePropertiesByMethod(Map configMap, def domainProperties, def srcPropertiesByQueryMap) {

        return configMap.inject([:]){propertiesMap,entry->
            log.debug("Checking if source properties having method : ${entry.key}")

            if (entry.value instanceof Map && entry.value.keys().contains(METHOD)) {
                def srcProp = entry.value[SRC_PROP_NAME];    // srcProp = ["partyType":"enumDescription"]
                def methodParams = [:]
                if (srcProp instanceof Map) { //check here if it is instance of Map
                    // here value is domain instance property name
                    srcProp.each { key, value ->
                        def propValue = MapUtils.getMapValueByDeepProperty(domainProperties, key)
                        if(value instanceof Map){
                            def srcPropEntry = value.entrySet().first()
                            if(srcPropEntry.key.equals("depends") && srcPropEntry.value.equals("QM")){
                                log.debug("Found a property that depends on query map : ${srcProp}")
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
                    propertiesMap[entry.key] =  entry.value[METHOD].call(methodParams)

                } else {
                    // here expecting a method with param value
                    log.debug("expecting a method with param value")
                    def methodParamValue = entry.value[METHOD_PARAM_VALUE]
                    def method = entry.value[METHOD]
                    propertiesMap[entry.key] = method.call(methodParamValue)
                }
            }
            return  propertiesMap
        }
    }

    static Object getDomainInstanceByQueryMap(String propertyName, Map configMap, Map sourceDomainProperties) {
        log.debug("In method getDomainInstanceByQueryMap.. ")
        log.debug("Finding domain instance for property : ${propertyName} and source property : ${configMap[propertyName][SRC_PROP_NAME]}")
        def entry = configMap[propertyName][SRC_PROP_NAME].entrySet().first()
        Object queryPropertyValue = MapUtils.getMapValueByDeepProperty(sourceDomainProperties,entry.key) /*123*/
        log.debug("Query property value : ${queryPropertyValue}")
        Map finalQueryMap = [:]
        finalQueryMap[entry.value] = queryPropertyValue

        Object domainClass = configMap[propertyName].domainClass
        //Company.findWhere([regNo:"123"])
        log.debug("Finding domainInstance with parameters : ${finalQueryMap}")
        Object domainInstance = domainClass.findWhere(finalQueryMap)
        log.debug("Found domain instance by query map :${domainInstance.properties}")
        return domainInstance
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

    static def createChildDomainInstance(Map configMap, Map.Entry configEntry, Map domainProperties){
        log.debug("New domain instance is to be created for this property : ${entry.key}")
        Map propertyConfigMap = configEntry.value[CONFIG_MAP]
        def childDomainProperties = configMap.inject([:]) { resultMap, entry ->
                def simpleProperties = populateSimpleProperties(propertyConfigMap,domainProperties)
                def queryMapProperties = populateSourcePropertiesHavingQueryMap(propertyConfigMap,domainProperties)
                def methodProperties = populatePropertiesByMethod(propertyConfigMap)
                def parentConfigProperties = populatePropertiesDependsOnParentConfigMap(propertyConfigMap,configMap)

                resultMap += simpleProperties + queryMapProperties + methodProperties + parentConfigProperties

                return resultMap
        }

        def domainClass = configEntry[DOMAIN_CLASS]
        return domainClass.class.newInstance(childDomainProperties)
    }

    static def populateChildDomainInstanceProperties(Map configMap,Map domainProperties){
        log.debug("populating child domain instance properties.")
        return configMap.inject([:]) { resultMap, entry ->
            if (entry.value instanceof Map && entry.value[CREATE_NEW_INSTANCE]) {
                log.debug("New domain instance is to be created for this property : ${entry.key}")
                resultMap[entry.key] = createChildDomainInstance(configMap,entry,domainProperties)
            }
            return resultMap
        }
    }

    static def populateSimpleProperties(Map configMap, Map domainProperties){
        return configMap.inject([:]) { dMap, entry ->
            if (!(entry.value instanceof Map)) {

                log.debug("finding value of simple property : ${entry.key}")
                dMap[entry.key] = domainProperties[entry.value]

            }else if (entry.value instanceof Map && entry.value.keys().contains(PROPERTY_VALUE)) {
                log.debug("finding value of simple property having direct value: ${entry.key}")
                dMap[entry.key] = entry.value[PROPERTY_VALUE]
            }
            return dMap
        }
    }


    static def populatePropertiesDependsOnParentConfigMap(Map configMap,Map parentConfigMap){
        return configMap.inject([:]) { dMap, entry ->
            if (entry.value instanceof Map && entry.value.keys().contains(DEPENDS_PARENT_CONFIG)) {
                log.debug("finding value of property having dependency of parent config map: ${entry.key}")
                def value = parentConfigMap[entry[SRC_PROP_NAME]]
                dMap[entry.key] = value
            }
            return dMap
        }
    }

    /*static def populatePropertiesByMethodWithParamValue(Map configMap){
        return configMap.inject([:]) { dMap, entry ->
            def properties = entry.value.keys()
            if (entry.value instanceof Map && properties.contains(METHOD) && properties.contains(METHOD_PARAM_VALUE)) {
                log.debug("finding value of property having method and param value: ${entry.key}")
                def methodParamValue = entry.value[METHOD_PARAM_VALUE]
                def method = entry.value[METHOD]
                dMap[entry.key] = method.call(methodParamValue)
            }
            return dMap
        }
    }*/

    /*
    propertyName='comapany'

    configMap=address:"officeAddress",
                        telephoneNo:"telephoneNo1",
                        partyId:"id",
                        cstTin:"cstNo",
                        salesTaxNo:"serviceTaxNo",
                        company:[domainClass:Company,srcPropName:"company",queryMap: [regNo:"registrationNo"]],
                        lastUpdatedBy:[domainClass:User,srcPropName:"lastUpdatedBy"],
//                        underGroup:[domainClass:AccountGroup,srcPropName:"underGroup",destPropName:"underGroup",primaryKes:['name','partyType']]
                        underGroup:[domainClass:AccountGroup,srcPropName:"underGroup",destPropName:"underGroup",queryList:['name','company']]

    sourceDomainProperties=[company: [
            ecNo: null,
            serviceTaxNo: null,
            eCCNo: ACNPS7018MEM001,
            stockOpBalanceDate: 2014-06-01,
            bstNo: null,
            alert4: 2016-02-27,
            currentYear: 2014-06-01,
            alert5: 2016-03-06,
            alert1: 2016-02-06,
            rangeNew: 04(RANGE-IV(KURULIRANGE),
            alert2: 2016-02-13,
            alert3: 2016-02-20,
            place: null,
            createdDate: null,
            bankAccountName: null,
            telephoneNo: 9975020000,
            webHosting: true,
            class: com.utilities.CompanyCreation,
            lastUpdated: 2015-03-08,
            bankName: null,
            accountNo: null,
            companyName: adityaaenterprises,
            country: null,
            isActive: true,
            pLANo: 0,
            panNo: null,
            email: shinde@adityaagroup.com,
            lbtNo: null,
            branchName: [
                id: 2,
                class: com.utilities.Branch
            ],
            faxNo: null,
            exciseQuaterly: false,
            companyStandard: null,
            csttin: 27910025298C,
            organizationName: [
                id: 2,
                class: com.utilities.Organization
            ],
            exciseMonthly: false,
            id: 2,
            division: 02(DIVISION-II(ALANDIDIVISION),
            commissionerate: EF(PUNE-IV),
            localHosting: true,
            weekAlert2: 2016-02-29,
            weekAlert3: 2016-03-01,
            weekAlert1: 2016-02-28,
            weekAlert6: 2016-03-04,
            weekAlert7: 2016-03-05,
            weekAlert4: 2016-03-02,
            weekAlert5: 2016-03-03,
            attachment: adityaaenterprises.jpeg,
            ifscCode: null,
            workAddress: null,
            stcNo: null,
            locationNo: null,
            certificationNo: null,
            days: 365,
            address: VISHNUBALANAGAR,
            SONAWANEVASTI,
            OPPBHARATFORGE,
            CHAKAN,
            TAL-KHED,
            DISTPUNE-410501,
            dateCreated: 2014-07-28,
            vattin: 27910025298V,
            regNo: ACNPS7018MEM001,
            bankBranch: null
        ],
        ]

     */

}
