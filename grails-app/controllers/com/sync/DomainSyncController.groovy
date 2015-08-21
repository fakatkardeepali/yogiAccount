package com.sync

import com.master.AccountLedger
import utils.JSONUtils

class DomainSyncController {
    DomainSyncService domainSyncService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def save(){
        //make hash table here
        println "Called save of domain sync controller"

        //println "params: ${params}"
        //println "request : ${request.getJSON().getClass()}"

        def domainInstanceProperties = request.getJSON()
        domainSyncService.save(domainInstanceProperties.className,domainInstanceProperties)
        println "ledger count : ${AccountLedger.count()}"
        println "ledger properties : ${AccountLedger.last().properties}"
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
