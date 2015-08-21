package utils

import com.master.AccountGroup
import com.master.AccountLedger
import com.system.Company
import com.system.User


/**
 * Created by pc-2 on 14/8/15.
 */
class DomainUtils {

    static def createDomainInstance(String domainName, def domainProperties,boolean isUpdate=false) {
        switch (domainName) {
            case 'Party':
                Map targetDomainProperties = getMapForParty(domainName, domainProperties)
                def domainInstance;
                if(isUpdate){
                    println "finding party by id ${domainProperties.partyId}"
                    domainInstance = AccountLedger.findByPartyId(domainProperties.partyId)
                    if(domainInstance){
                        domainInstance.properties = targetDomainProperties
                    }else{
                        println "could not find party by id : ${domainProperties.partyId}"
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

    static Map getMapForParty(String domainName, def domainProperties) {
        Map partyDomainProperties = [:]
        partyDomainProperties.name = domainProperties.name
        partyDomainProperties.creditDays = Integer.parseInt(domainProperties.creditDays)
        partyDomainProperties.address = domainProperties.officeAddress
        partyDomainProperties.telephoneNo = domainProperties.telephoneNo1
        partyDomainProperties.faxNo = domainProperties.faxNo
        partyDomainProperties.company = Company.get(domainProperties.company as long)
        partyDomainProperties.lastUpdatedBy = User.get(domainProperties.lastUpdatedBy as long)
        partyDomainProperties.status = domainProperties.status
        partyDomainProperties.underGroup = AccountGroup.get(domainProperties.underGroup as long)
        partyDomainProperties.partyId = domainProperties.partyId
        return partyDomainProperties
        //return array from here
    }

    static Map getMapForOutstanding(def params) {
        // do stuff here
    }
}
