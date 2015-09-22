package com.sync

import com.helpers.DomainHelpers
import com.master.AccountLedger
import com.system.Company
import grails.converters.JSON
import utils.TestData

//import utils.JSONUtils

class DomainSyncController {
    DomainSyncService domainSyncService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(){
//        PartySyncTest.checkPrerequisites()
        Map domainInstanceProperties = TestData.domainInstanceProperties
        Map configMap = DomainHelpers.getConfigMapForDomain("Party")

//        print configMap
        //Object companyInstance = DomainHelpers.getDomainInstanceByQueryMap("company",configMap,domainInstanceProperties)
//        Object companyInstance = DomainHelpers.getDomainInstanceByQueryMap("underGroup",configMap,domainInstanceProperties)
        //println AccountGroup.findByPartyType.call("Customer");

        Map diffProperties = DomainHelpers.populateDiffProperties(configMap,domainInstanceProperties)
          //def domainInstance = DomainHelpers.createDomainInstance("Party",domainInstanceProperties)
          //print domainInstance
        def srcPropertiesByQueryMap = DomainHelpers.populateSourcePropertiesHavingQueryMap(configMap,domainInstanceProperties)
        //Object companyInstance = DomainHelpers.findDomainInstanceByMethod(configMap.underGroup,domainInstanceProperties,srcPropertiesByQueryMap)
        //println companyInstance
        //println diffProperties

    }

    def save(){
        //make hash table here
        println "Called save of domain sync controller"

        //println "params: ${params}"
        //println "request : ${request.getJSON().getClass()}"



        Map domainInstanceProperties = request.getJSON()
//        Object destinationProperties = AccountLedger.get(1)
        Map configMap = DomainHelpers.getConfigMapForDomain("Party")
        println configMap
//        DomainHelpers.getDomainInstanceByQueryMap("company",configMap,domainInstanceProperties)

//        println domainInstanceProperties

       def res = domainSyncService.save(domainInstanceProperties.className,domainInstanceProperties)
//        println "ledger count : ${AccountLedger.count()}"
//        println "ledger properties : ${AccountLedger.last().properties}"

//        def res=[1:true]
//        res.success = "rest call successfull"

//        respond res,[formats:['json']]
        respond ""

    }

    def update() {

        println "Called update of domain sync controller"
        //println "params: ${params}"
        //println "request : ${request.getJSON().getClass()}"

        def domainInstanceProperties = request.getJSON()
        domainSyncService.update(domainInstanceProperties.className, domainInstanceProperties)

        println "ledger count : ${AccountLedger.count()}"
        println "ledger properties : ${AccountLedger.findByPartyId(domainInstanceProperties.partyId).properties}"
        //println "ledger properties : ${AccountLedger.last().properties.partyId}"
    }

    def findAccountLedgersByMailId(){
        def getData = request.getJSON()
        Company compObj = Company.findByEmail(getData.mailId)
//        def accountData = AccountLedger.findAllByCompany(compObj)
        def accountData = AccountLedger.createCriteria().list{
            projections {
                property("id")
                property("name")
            }
        }
        render accountData as JSON
    }
}
