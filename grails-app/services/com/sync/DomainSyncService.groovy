package com.sync

import grails.transaction.Transactional
import utils.DomainUtils

@Transactional
class DomainSyncService {

    def serviceMethod() {

    }

   static def save(String domainName,def hashMap){

        def domainInstance = DomainUtils.createDomainInstance(domainName,hashMap)
        domainInstance.save()

       /*
        Our steps to save any map of class
         1.Catch the parameters from API in controller of perticular class
         2.Parse it to get map of key-value pairs
         3.Get className from the map
         4.Get map for perticular class in Account project using switch case
         5.Create instance of that class using CreateDomain method in DomainUtil class
         6.Save the perticular Instance of class
        */

    }

    static def update(int id,String domainName,def hashMap,Object grailsApplication){

        def domainInstance = DomainUtils.updateDomainInstance(id,domainName,hashMap,grailsApplication)


    }

    static def delete(int id){

    }
}
