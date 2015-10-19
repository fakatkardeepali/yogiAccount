package com.sync

import com.helpers.DomainHelpers
import grails.transaction.Transactional

@Transactional
class DomainSyncService {

   def save(String domainName,def hashMap){

        def domainInstance = DomainHelpers.createDomainInstance(domainName,hashMap)
        log.debug("Final Domain Instance To Be Saved....${domainInstance}" )
        return domainInstance.save(flush:true)

       /*
        Our steps to save any map of class
         1.Catch the parameters from API in controller of particular class
         2.Parse it to get map of key-value pairs
         3.Get className from the map
         4.Get map for perticular class in Account project using switch case
         5.Create instance of that class using CreateDomain method in DomainUtil class
         6.Save the perticular Instance of class
        */
    }

   def update(String domainName,def hashMap){
        def domainInstance = DomainHelpers.createDomainInstance(domainName,hashMap,true/*find existing instead of create new instance*/)
       if(domainInstance){
           return domainInstance.save()
       }else{
           log.debug("Failed to update domain : ${domainName}")
           false
       }
   }

    def delete(String domainName,def hashMap){
        def domainInstance = DomainHelpers.createDomainInstance(domainName,hashMap,false,true/*find existing instance*/)
        if(domainInstance){
            return domainInstance.delete()
        }else{
            log.debug("failed to delete")
            false
        }
    }


}
