package com.helpers

import com.master.AccountGroup
import com.master.AccountLedger
import com.system.Company
import com.system.User
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

/**
 * Created by pc-2 on 8/9/15.
 */
class DomainHelpers {
    static def createDomainInstance(String domainName, def domainProperties,boolean isUpdate=false) {
        switch (domainName) {
            case 'Party':


                Map targetDomainProperties = getPropertiesForDomainInstance(domainName, domainProperties,new AccountLedger().properties)
                def domainInstance;
                if(isUpdate){
                    println "finding party by id ${domainProperties.id}"
                    domainInstance = AccountLedger.findByPartyId(domainProperties.id)
                    if(domainInstance){
                        domainInstance.properties = targetDomainProperties
                    }else{
                        println "could not find party by id : ${domainProperties.id}"
                    }
                }else {
                    domainInstance = new AccountLedger(targetDomainProperties)    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)

                }
                return domainInstance
                break;

            case 'Invoice':
                return getMapForOutstanding(domainProperties)
                break;
        }
    }

//    static Map getMapForParty(String domainName, def domainProperties) {
//        Map partyDomainProperties = [:]
//        partyDomainProperties.name = domainProperties.name
//        partyDomainProperties.creditDays = Integer.parseInt(domainProperties.creditDays)
//        partyDomainProperties.address = domainProperties.officeAddress
//        partyDomainProperties.telephoneNo = domainProperties.telephoneNo1
//        partyDomainProperties.faxNo = domainProperties.faxNo
//        partyDomainProperties.company = Company.get(domainProperties.company as long)
//        partyDomainProperties.lastUpdatedBy = User.get(domainProperties.lastUpdatedBy as long)
//        partyDomainProperties.status = domainProperties.status
//        partyDomainProperties.underGroup = AccountGroup.get(domainProperties.underGroup as long)
//        //assign party id for the ledger for which party is added in ERP
//        partyDomainProperties.partyId = domainProperties.id
//        return partyDomainProperties
//        //return array from here
//    }

    static Map getPropertiesForDomainInstance(String domainName, def domainProperties,Map destinationProperties) {
        Map accountLedgerProperties = getSimilarDestinationDomainPropertiesMap(domainProperties,destinationProperties)
        Map configMap=getConfigMapForDomain(domainName)
        Map remainingPropMap =  populateDiffProperties(configMap,domainProperties)
        return accountLedgerProperties + remainingPropMap
    }

    static Map getSimilarDestinationDomainPropertiesMap(Map sourceDomainProperties,Map destinationDomainProperties){
        println destinationDomainProperties
        def propertyNames = destinationDomainProperties.keySet()
        return sourceDomainProperties.inject([:]){map,entry->
            if(propertyNames.contains(entry.key)){
                map[entry.key] = entry.value
            }
            return map;
        }
    }


    static Map populateDiffProperties(Map configMap,Map srcMap){
        return configMap.inject([:]){dMap,entry->
            //println "is instance if map ${dMap instanceof Map}"

            if(entry.value instanceof Map){
                if(entry.value.method){
                    dMap[entry.key] = findDomainInstanceByMethod(entry.value,srcMap)
//                entry.value = [domainClass:AccountGroup,scrPropName:["partyType":"enumDescription"],method:AccountGroup.findByPartyType]
//                srcMap = Party Json came form ERP
                }else{
                    dMap[entry.key] = getDomainInstanceByQuery(entry.key,configMap,srcMap)
                }
            }else{
                dMap[entry.key] = srcMap[entry.value]
            }
            return dMap
        }
    }

    static def findDomainInstanceByMethod(def configMapEntryValue,def srcMap){
        def srcProp = configMapEntryValue["scrPropName"];    // srcProp = ["partyType":"enumDescription"]
        def paramValue
        if(srcProp instanceof Map){
            def srcPrpertyEntry = srcProp.entrySet().first()  // "partyType":"enumDescription"
            paramValue = srcMap[srcPrpertyEntry.key][srcPrpertyEntry.value]   // PartyJson[partyType][enumDescription]  (e.g.supplier/customer)
        }
        else {
            paramValue = srcMap[srcProp]
        }

//        if(srcProp instanceof Map){
//            def srcPrpertyEntry = srcProp.entrySet().first()
//            paramValue = srcMap[srcPrpertyEntry.key][srcPrpertyEntry.value]
//        }
//        else {
//            paramValue = srcMap[srcProp]
//        }

        return configMapEntryValue["method"].call(paramValue)
    }

    static Map getConfigMapForDomain(String domainName){
        switch (domainName){
            case "Party":
                return [
                        address:"officeAddress",
                        telephoneNo:"telephoneNo1",
                        partyId:"id",
                        cstTin:"cstNo",
                        salesTaxNo:"serviceTaxNo",
                        company:[domainClass:Company,scrPropName:"company",queryMap: [regNo:"registrationNo"]],
                        lastUpdatedBy:[domainClass:User,scrPropName:"lastUpdatedBy",queryMap: [mailId:"username"]],
//                        underGroup:[domainClass:AccountGroup,scrPropName:"underGroup",destPropName:"underGroup",primaryKes:['name','partyType']]
                        underGroup:[domainClass:AccountGroup,scrPropName:["partyType":"enumDescription"],method:AccountGroup.findByPartyType]
                ]
                break
            case "Tax":
                return [
                        name:"taxName"
//                        underGroup: here undergroup should be "Duties And Taxes"
                ]
                break

            case "InvoiceEntry":
                return [
                        voucherNo:"invoiceNo",
                        date:"invoiceDate",
                        referenceNo:"challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        narration:"description",
                        amount:"grandTotal",
                        amountStatus:"Dr",
                ]
                break

            case "InvoiceEntryLC":
                return [
                        voucherNo:"invoiceNo",
                        date:"invoiceDate",
                        referenceNo:"challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount:"grandTotal",
                        amountStatus:"Dr",
                ]
                break

            case "ProFormaInvoice":
                return [
                        voucherNo:"invoiceNo",
                        date:"invoiceDate",
                        referenceNo:"challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount:"grandTotal",
                        amountStatus:"Dr",
                ]
                break
            case "PurchaseReturn":
                return [
                        voucherNo:"invoiceNo",
                        date:"invoiceDate",
                        referenceNo:"challanNo",
//                        voucherType: here voucherType should be "Sales"
//                        partyName: object of Party (Ledger in Account)
                        amount:"grandTotal",
                        amountStatus:"Dr",
                ]
                break
            case "BillPassing":
                return [
                        voucherNo:"voucherNo",
                        date:"billDate",
                        referenceNo:"billNo",
//                        voucherType: here voucherType should be "Purchase"
//                        partyName: object of Party (Ledger in Account)
                        amount:"grandTotal",
                        amountStatus:"Cr",
                ]
                break


        }
    }

    static Map getMapForOutstanding(def params) {
        // do stuff here
    }

    /*
    propertyName='comapany'

    configMap=address:"officeAddress",
                        telephoneNo:"telephoneNo1",
                        partyId:"id",
                        cstTin:"cstNo",
                        salesTaxNo:"serviceTaxNo",
                        company:[domainClass:Company,scrPropName:"company",queryMap: [regNo:"registrationNo"]],
                        lastUpdatedBy:[domainClass:User,scrPropName:"lastUpdatedBy"],
//                        underGroup:[domainClass:AccountGroup,scrPropName:"underGroup",destPropName:"underGroup",primaryKes:['name','partyType']]
                        underGroup:[domainClass:AccountGroup,scrPropName:"underGroup",destPropName:"underGroup",queryList:['name','company']]

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

    static Object getDomainInstanceByQuery(String propertyName,Map configMap,Map sourceDomainProperties){
        Map queryMap1 = configMap[propertyName].queryMap
        def queryMapEntry= queryMap1.entrySet().first()
        Object queryPropertyValue=sourceDomainProperties[propertyName][queryMapEntry.key] /*123*/
        Object domainClass = configMap[propertyName].domainClass

        Object domainInstance


            Map finalQueryMap = [:]
           finalQueryMap[queryMapEntry.value]=queryPropertyValue
            //Company.findWhere([regNo:"123"])
            domainInstance=domainClass.findWhere(finalQueryMap)

        return domainInstance
    }

/*
    Returns a Map of domain properties. If a property is domain instance then its properties are collected.
    e.g. accountLedger.properties

    This method is used instead of converting a domain instance into JSON.
*/
    static def buildPropertiesMap(Object domainInstance){
        return domainInstance.properties.inject([:]) { map, propEntry->
            println "processing entry ${propEntry},class  ${propEntry.value.class.name}"
            map[propEntry.key] = propEntry.value
            if(DomainClassArtefactHandler.isDomainClass(propEntry.value.class)){
                println "${propEntry.value} is domain instance"
                map[propEntry.key] = propEntry.value.properties
            }
            return map
        }
    }
}
