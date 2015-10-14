package com.helpers

import com.master.AccountGroup
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.system.User
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
                        amountStatus: "Dr",
                        createNewInstance:true,
                        partyName   : [domainClass: AccountLedger,srcPropName: ["customer.name":"name"],queryMap: true],
                        company     : [domainClass: Company, srcPropName: ["company.regNo":"registrationNo"],queryMap: true],
                        lastUpdatedBy: [domainClass: User, srcPropName: ["lastUpdatedBy.mailId":"username"],queryMap: true]
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
                    targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company?.id,"Sale")
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
                def domainInstance;
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
                    domainInstance = new Voucher(targetDomainProperties)
                    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                }
                return domainInstance
                break;
        }
    }

    static Map getPropertiesForDomainInstance(String domainName, def domainProperties, Map destinationProperties) {
        log.debug("In method getPropertiesForDomainInstance with domain name : ${domainName}")
        Map accountLedgerProperties = getSimilarDestinationDomainPropertiesMap(domainProperties, destinationProperties)
        log.debug("Found similar destiantion domain properties : ${accountLedgerProperties}")
        Map configMap = getConfigMapForDomain(domainName)
        Map remainingPropMap = populateDiffProperties(configMap, domainProperties)
        log.debug("Found different destination properties : ${remainingPropMap}")
        return accountLedgerProperties + remainingPropMap
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


    static Map populateDiffProperties(Map configMap, Map srcMap) {
        log.debug("Populating different domain properties..")
        def srcPropertiesByQueryMap = populateSourcePropertiesHavingQueryMap(configMap, srcMap)
        log.debug("Found properties having query map : ${srcPropertiesByQueryMap}")
        def srcPropertiesByMethod = configMap.inject([:]) { dMap, entry ->
            //println "is instance if map ${dMap instanceof Map}"
            if (entry.value instanceof Map) {
                if (entry.value.method) {
                    dMap[entry.key] = findDomainInstanceByMethod(entry.value, srcMap, srcPropertiesByQueryMap)
//                entry.value = [domainClass:AccountGroup,srcPropName:["partyType":"enumDescription"],method:AccountGroup.findByPartyType]
//                srcMap = Party Json came form ERP
                }
            } else {
                dMap[entry.key] = srcMap[entry.value]
            }

            return dMap
        }
        return srcPropertiesByQueryMap + srcPropertiesByMethod

    }

    static Map populateSourcePropertiesHavingQueryMap(Map configMap, Map srcMap) {
        log.debug("Checking if source properties having query map..")
        return configMap.inject([:]) { dMap, entry ->
            //println "is instance if map ${dMap instanceof Map}"
            if (entry.value instanceof Map && entry.value.queryMap) {
                log.debug("This entry is having query map : ${entry.value}")
                dMap[entry.key] = getDomainInstanceByQueryMap(entry.key, configMap, srcMap)
            }
            return dMap
        }
    }

    static def findDomainInstanceByMethod(def configMapEntryValue, def srcMap, def srcPropertiesByQueryMap) {
        log.debug("Checking if source properties having method : ${configMapEntryValue}")
        def srcProp = configMapEntryValue[SRC_PROP_NAME];    // srcProp = ["partyType":"enumDescription"]
        def methodParams = [:]
        if (srcProp instanceof Map) { //check here if it is instance of Map
            // here value is domain instance property name
            srcProp.each { key, value ->
                def propValue = MapUtils.getMapValueByDeepProperty(srcMap, key)
                if(value instanceof Map){
                    def entry = value.entrySet().first()
                    if(entry.key.equals("depends") && entry.value.equals("QM")){
                        log.debug("Found a property that depends on query map : ${srcProp}")
                        methodParams[key] = MapUtils.getMapValueByDeepProperty(srcPropertiesByQueryMap, key)
                    }else{
                        log.debug("Invalid config map entry: ${configMapEntryValue}")
                    }
                }else{
                    methodParams[value] = propValue  // PartyJson[partyType][enumDescription]  (e.g.supplier/customer) 
                }
            }
            // "partyType":"enumDescription"
        } else {
            log.debug("${srcProp} is not an instance of Map")
        }
        log.debug("calling method with params : ${methodParams}")
        return configMapEntryValue["method"].call(methodParams)
    }

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
}
