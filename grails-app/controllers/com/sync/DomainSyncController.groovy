package com.sync

import com.helpers.DomainHelpers
import com.master.AccountLedger

//import utils.JSONUtils

class DomainSyncController {
    DomainSyncService domainSyncService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(){
      Map domainInstanceProperties = [
              domainClass:"Party",
              name :"ABC",
              lastUpdatedBy:[
                      mailId:"ravi@gmail.com"
                           ],
              partyType:[
                      enumDescription:"Supplier"
              ],
                company: [
                attachment: null,
                locationNo: null,
                workAddress: null,
                stcNo: null,
                ifscCode: null,
                weekAlert1: null,
                weekAlert3: null,
                weekAlert2: null,
                weekAlert5: null,
                weekAlert4: null,
                weekAlert7: null,
                weekAlert6: null,
                vattin: null,
                dateCreated: null,
                bankBranch: null,
                regNo: "123",
                certificationNo: null,
                address: null,
                exciseQuaterly: false,
                csttin: null,
                companyStandard: null,
                branchNameId: 1,
                webHosting: false,
                division: null,
                isActive: true,
                commissionerate: null,
                bankName: null,
                accountNo: null,
                lastUpdated: null,
                country: null,
                localHosting: false,
                telephoneNo: null,
                faxNo: null,
                panNo: null,
                pLANo: null,
                exciseMonthly: false,
                email: null,
                lbtNo: null,
                stockOpBalanceDate: null,
                eCCNo: null,
                ecNo: null,
                organizationNameId: 1,
                serviceTaxNo: null,
                alert3: null,
                alert2: null,
                rangeNew: null,
                alert1: null,
                bankAccountName: null,
                days: 0,
                createdDate: null,
                place: null,
                bstNo: null,
                alert5: null,
                currentYear: null,
                alert4: null
        ]]

        Map configMap = DomainHelpers.getConfigMapForDomain("Party")

//        print configMap
//        Object companyInstance = DomainHelpers.getDomainInstanceByQuery("company",configMap,domainInstanceProperties)
//        Object companyInstance = DomainHelpers.getDomainInstanceByQuery("underGroup",configMap,domainInstanceProperties)
        //println AccountGroup.findByPartyType.call("Customer");

//        Map diffProperties = DomainHelpers.populateDiffProperties(configMap,domainInstanceProperties)
//          def domainInstance = DomainHelpers.createDomainInstance("Party",domainInstanceProperties)
          def sourceProperties = DomainHelpers.populateSourcePropertiesHavingQueryMap(configMap,domainInstanceProperties)
          Object domainInstanceByMethod = DomainHelpers.findDomainInstanceByMethod(configMap.underGroup,domainInstanceProperties,sourceProperties)
          print domainInstanceByMethod

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
//        DomainHelpers.getDomainInstanceByQuery("company",configMap,domainInstanceProperties)

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
}
