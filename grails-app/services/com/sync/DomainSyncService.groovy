package com.sync

import com.helpers.DomainHelpers
import grails.transaction.Transactional

@Transactional
class DomainSyncService {

   def save(String domainName,Map domainProperties){
        def domainInstances = new DomainHelpers(domainName,domainProperties).initialiseDomainInstanceByDomainProperties()
        log.debug("Final Domain Instance To Be Saved....${domainInstances}" )

       if(domainProperties instanceof Map){
           def mainDomainInstance = domainInstances[DomainHelpers.MAIN_DOMAIN_INSTANCE]
           if(mainDomainInstance.save(flush:true)){
               def dependentDomainInstances = domainInstances[DomainHelpers.DEPENDENT_DOMAIN_INSTANCES]
               dependentDomainInstances.each{dependentDomainInstance->
                   dependentDomainInstance.save()
               }
           }else{
               //TODO handle error
           }
       }else{
           return domainInstances.save()
       }

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

   def update(String domainName,def domainProperties){
        def domainInstances = new DomainHelpers(domainName,domainProperties).getUpdatedDomainInstanceByDomainProperties()
       if(domainInstances){
           return domainInstances.save()
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
