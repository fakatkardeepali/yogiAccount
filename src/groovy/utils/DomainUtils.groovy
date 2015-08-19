package utils

import com.master.AccountGroup
import com.master.AccountLedger
import com.sync.DomainSyncService
import com.system.Company
import com.system.User


/**
 * Created by pc-2 on 14/8/15.
 */
class DomainUtils {
    DomainSyncService domainSyncService
//    static Map getMap(String domainName,Map domainProperties){
//
//        switch(domainName){
//            case 'Party':   return getMapForParty(domainName,domainProperties)
//                            break;
//            case 'Invoice': return getMapForOutstanding(domainProperties)
//                            break;
//        }
//    }

    static def createDomainInstance(String domainName,def domainProperties){
        switch(domainName){
            case 'Party':
                            Map targetDomainProperties = getMapForParty(domainName,domainProperties)
//                            def domainInstance =  grailsApplication.getDomainClass("com.master.AccountLedger").newInstance()    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
                            def domainInstance =  new AccountLedger(targetDomainProperties)    // here domain name should provide full package name for the class(e.g. com.master.AccountLedger)
//                            domainInstance.properties = targetDomainProperties
//            remove grailsApplication
//            add party id in target domain properties
                            return domainInstance
                            break;


            case 'Invoice':
                            return getMapForOutstanding(domainProperties)
                            break;
        }
    }

    static def updateDomainInstance(String domainName,def domainProperties,Object grailsApplication){
        switch(domainName){
            case 'Party':
                            Map targetDomainProperties = getMapForParty(domainName,domainProperties)
//                            def domainInstance=grailsApplication.getArtefact("Domain","com.master.AccountLedger")?.getClazz()?.get(id)
//                            domainInstance.properties = targetDomainProperties
//                            return domainInstance
                            break;


            case 'Invoice':
                            return getMapForOutstanding(domainProperties)
                            break;
        }
    }

    static Object saveObject(def newInstance) {

    }

    static Map getMapForParty(String domainName,def domainProperties) {
        Map partyDomainProperties = [:]
        partyDomainProperties.name=domainProperties[0].name
        partyDomainProperties.creditDays=Integer.parseInt(domainProperties[0].creditDays)
        partyDomainProperties.address=domainProperties[0].officeAddress
        partyDomainProperties.telephoneNo=domainProperties[0].telephoneNo1
        partyDomainProperties.faxNo=domainProperties[0].faxNo
        partyDomainProperties.company=Company.get(domainProperties[0].company as long)
        partyDomainProperties.lastUpdatedBy= User.get(domainProperties[0].lastUpdatedBy as long)
        partyDomainProperties.status=domainProperties[0].status
        partyDomainProperties.underGroup=AccountGroup.get(domainProperties[0].underGroup as long)
        return partyDomainProperties
        //return array from here
    }
    static Map getMapForOutstanding(def params) {
        // do stuff here

    }

}
