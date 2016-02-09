package com.sync

import com.helpers.DomainHelpers
import com.master.AccountLedger
import com.master.VoucherType
import com.system.Company
import com.transaction.Voucher
import test.PartySyncTest

//import utils.JSONUtils

class DomainSyncController {
    DomainSyncService domainSyncService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
//    static allowedMethods = [save: "GET", update: "POST", delete: "POST"]


//    def index(){
//        String domainName = InvoiceEntryTestData.data.className
//        Map domainProperties = InvoiceEntryTestData.domainInstanceProperties
//        domainSyncService.save(domainName,domainProperties);
//    }

    def index(){
        PartySyncTest.checkPrerequisites()

//        Map domainInstanceProperties = TestData.domainInstanceProperties
//        Map domainInstanceProperties = TestData.updatedDomainInstanceProperties
        Map configMap = DomainHelpers.getConfigMapForDomain("Party")

//        print configMap
//        Object companyInstance = DomainHelpers.getDomainInstanceByQueryMap("company",configMap,domainInstanceProperties)
//        Object companyInstance = DomainHelpers.getDomainInstanceByQueryMap("underGroup",configMap,domainInstanceProperties)
//        println AccountGroup.findByPartyType.call("Customer");

        Map diffProperties = DomainHelpers.populatePropertiesByConfigMap(configMap,domainInstanceProperties)
          //def domainInstance = DomainHelpers.createDomainInstance("Party",domainInstanceProperties)
          //print domainInstance
        def srcPropertiesByQueryMap = DomainHelpers.getPropertiesByQueryMap(configMap,domainInstanceProperties)
        //Object companyInstance = DomainHelpers.populatePropertiesByMethod(configMap.underGroup,domainInstanceProperties,srcPropertiesByQueryMap)
        //println companyInstance
        //println diffProperties

    }

    def save(){

        //make hash table here
        log.debug("Called save of domain sync controller")

        //println "params: ${params}"
        //println "request : ${request.getJSON().getClass()}"



        Map domainInstanceProperties = request.getJSON()
//        Object destinationProperties = AccountLedger.get(1)
//        Map configMap = DomainHelpers.getConfigMapForDomain("Party")
//        println configMap
//        DomainHelpers.getDomainInstanceByQueryMap("company",configMap,domainInstanceProperties)

//        println domainInstanceProperties
        log.debug("Got properties from ERP to save : ${domainInstanceProperties}")
        def res = domainSyncService.save(domainInstanceProperties.className,domainInstanceProperties)
        log.debug("After Saving Domain Class properties : ${res.properties}")
//        println "ledger count : ${AccountLedger.count()}"
//        println "ledger properties : ${AccountLedger.last().properties}"

//        def res=[1:true]
//        res.success = "rest call successfull"

//        respond res,[formats:['json']]
//        respond "rest call successfull",formats: 'json'

//        def company = Company.findById(6);
//        def vt = VoucherType.findWhere(name:"Sale",company: company)
//        Voucher.parametersInsert(vt, null)

    }

    def update() {

        println "Called update of domain sync controller"
        //println "params: ${params}"
        //println "request : ${request.getJSON().getClass()}"

        def domainInstanceProperties = request.getJSON()
        log.debug("Got properties from ERP to update : ${domainInstanceProperties}")
        domainSyncService.update(domainInstanceProperties.className, domainInstanceProperties)
        respond 11,[formats:'json']
        log.debug("ledger count : ${AccountLedger.count()}")
        log.debug("ledger properties : ${AccountLedger.findByPartyId(domainInstanceProperties.id).properties}")
        //println "ledger properties : ${AccountLedger.last().properties.partyId}"
    }

    def delete(){
        log.debug("Called delete of domain sync controller")
        def domainInstanceProperties = request.getJSON()
        log.debug("Got properties from ERP to delete : ${domainInstanceProperties}")
        domainSyncService.delete(domainInstanceProperties.className, domainInstanceProperties)
    }

    def findAccountLedgersByMailId(){
        def getData = request.getJSON()
        def result=[]
        Company compObj = Company.findByRegistrationNo(getData.regNo)
//        def accountData = AccountLedger.findAllByCompany(compObj)
//        if(accountData){
//            accountData.each {d->
//                result.push([
//                        id:d?.id,
//                        name:d?.name?:""
//                ])
//            }
//
//        }

//        def accountData = AccountLedger.createCriteria().list{
//            projections {
//                property("id")
//                property("name")
//            }
//        }

        def accounts = AccountLedger.findAllByCompany(compObj)
        render(contentType: "application/json") {
            array {
                accounts.each {
                    account id: it.id, name: it.name
                }
            }
        }

//        render result as JSON
    }

//    case InvoiceLC
//    case 'InvoiceEntryLC':
//    Map targetDomainProperties = getPropertiesForDomainInstance(domainName, sourceDomainProperties, new Voucher().properties)
//    Date date = Date.parse("yyyy-MM-dd",targetDomainProperties?.date)
//    targetDomainProperties.date = date
//    if(targetDomainProperties?.company?.id){
//        targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company,"Sale")
//    }
//    else{
//        log.error("Could not determine Voucher type because could not get Company id not")
//    }
//    if(targetDomainProperties?.voucherType?.id && targetDomainProperties?.date){
//        targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id,targetDomainProperties?.date,null)
//    }
//    else{
//        log.error("Could not get voucher number because could not get voucher type and date")
//    }
//
//    log.debug("Total populated target domain properties : ${targetDomainProperties}")
//
//    def domainInstance;
//    if (isUpdate) {
//        log.debug("Updating domain instance : ${domainName}")
//        log.debug("Finding Party by id ${sourceDomainProperties.id}")
//        domainInstance = Voucher.findById(sourceDomainProperties.id)
//        if (domainInstance) {
//            log.debug("Found domain instance from Account : ${domainInstance?.properties}")
//            domainInstance.properties = targetDomainProperties
//
//        } else {
//            log.debug("Could not find Voucher by id : ${sourceDomainProperties.id}")
//        }
//    }
//    else {
//        log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
//        domainInstance = new Voucher(targetDomainProperties)
//    }
//    return domainInstance
//
//    case 'ProFormaInvoice':
//    Map targetDomainProperties = getPropertiesForDomainInstance(domainName, sourceDomainProperties, new Voucher().properties)
//    Date date = Date.parse("yyyy-MM-dd",targetDomainProperties?.date)
//    targetDomainProperties.date = date
//    if(targetDomainProperties?.company?.id){
//        targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company,"Sale")
//    }
//    else{
//        log.error("Could not determine Voucher type because could not get Company id not")
//    }
//    if(targetDomainProperties?.voucherType?.id && targetDomainProperties?.date){
//        targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id,targetDomainProperties?.date,null)
//    }
//    else{
//        log.error("Could not get voucher number because could not get voucher type and date")
//    }
//
//    log.debug("Total populated target domain properties : ${targetDomainProperties}")
//
//    def domainInstance;
//    if (isUpdate) {
//        log.debug("Updating domain instance : ${domainName}")
//        log.debug("Finding Party by id ${sourceDomainProperties.id}")
//        domainInstance = Voucher.findById(sourceDomainProperties.id)
//        if (domainInstance) {
//            log.debug("Found domain instance from Account : ${domainInstance?.properties}")
//            domainInstance.properties = targetDomainProperties
//
//        } else {
//            log.debug("Could not find Voucher by id : ${sourceDomainProperties.id}")
//        }
//    }
//    else {
//        log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
//        domainInstance = new Voucher(targetDomainProperties)
//    }
//    return domainInstance
//
//    case 'PurchaseReturn':
//    Map targetDomainProperties = getPropertiesForDomainInstance(domainName, sourceDomainProperties, new Voucher().properties)
//    Date date = Date.parse("yyyy-MM-dd",targetDomainProperties?.date)
//    targetDomainProperties.date = date
//    if(targetDomainProperties?.company?.id){
//        targetDomainProperties.voucherType = VoucherType.findByCompanyAndName(targetDomainProperties?.company,"Sale")
//    }
//    else{
//        log.error("Could not determine Voucher type because could not get Company id not")
//    }
//    if(targetDomainProperties?.voucherType?.id && targetDomainProperties?.date){
//        targetDomainProperties.voucherNo = Voucher.getVoucherNumber(targetDomainProperties?.voucherType?.id,targetDomainProperties?.date,null)
//    }
//    else{
//        log.error("Could not get voucher number because could not get voucher type and date")
//    }
//
//    log.debug("Total populated target domain properties : ${targetDomainProperties}")
//
//    def domainInstance;
//    if (isUpdate) {
//        log.debug("Updating domain instance : ${domainName}")
//        log.debug("Finding Party by id ${sourceDomainProperties.id}")
//        domainInstance = Voucher.findById(sourceDomainProperties.id)
//        if (domainInstance) {
//            log.debug("Found domain instance from Account : ${domainInstance?.properties}")
//            domainInstance.properties = targetDomainProperties
//
//        } else {
//            log.debug("Could not find Voucher by id : ${sourceDomainProperties.id}")
//        }
//    }
//    else {
//        log.debug("Creating new instance of Voucher with properties : ${targetDomainProperties}")
//        domainInstance = new Voucher(targetDomainProperties)
//    }
//    return domainInstance

    def testVoucher(){
        def company = Company.findById(6);
        def vt = VoucherType.findWhere(name:"Sale",company: company)
        //Voucher.parametersInsert(VoucherType.findById(46),null)
        Voucher.parametersInsert(vt,null)
    }
}
