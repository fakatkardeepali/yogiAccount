package test

import com.helpers.DomainHelpers
import com.master.AccountLedger
import com.system.Company
import com.system.Organization
import com.system.User
import com.test.AssertUtils
import com.utils.MapUtils
import utils.TestData

/**
 * Created by gvc on 15-09-2015.
 */
class PartySyncTest {

    static def configMap = DomainHelpers.getConfigMapForDomain("Party")
    static def userName = MapUtils.getMapValueByDeepProperty(TestData.domainInstanceProperties,"lastUpdatedBy.mailId")
    static def companyName = MapUtils.getMapValueByDeepProperty(TestData.domainInstanceProperties,"company.name")
    static String organizationName = "org1"
    static String ledgerName = "ABC1"

    static def checkPrerequisites(){

        createTestData()
        checkQueryMapProperties()
        testGetDomainInstanceByQueryMap()
        testFindDomainInstanceByMethod()
        testCreateDomainInstance()
        testUpdateDomainInstance()

        def company = Company.findByRegistrationNo("123")
        AssertUtils.assertNotNull(company)
        //assert company.registrationNo.equalsIgnoreCase("123")
        AssertUtils.equals(company.registrationNo,"123")
        clearTestData()
    }

    static def testUpdateDomainInstance() {
        def ledgerInstance=AccountLedger.findByName(TestData.domainInstanceProperties.name)
        AssertUtils.assertNotNull(ledgerInstance)
        AssertUtils.assertNotNull(ledgerInstance.partyId)
        TestData.updatedDomainInstanceProperties["id"]=ledgerInstance.partyId
        def instance = DomainHelpers.createDomainInstance("Party",TestData.updatedDomainInstanceProperties,true)
        AssertUtils.assertNotNull(instance)
        AssertUtils.assertNotNull(instance.company)
        AssertUtils.assertNotNull(instance.underGroup)
        AssertUtils.assertNotNull(instance.lastUpdatedBy)
        instance.save()
//        AssertUtils.equals(instance.name,TestData.updatedDomainInstanceProperties.name)
//        AssertUtils.equals(instance.underGroup.name,AccountGroup.SUNDRY_DEBTORS)
    }

    static def checkQueryMapProperties(){
        def props = DomainHelpers.populateSourcePropertiesHavingQueryMap(configMap,TestData.domainInstanceProperties)
        AssertUtils.assertNotNull(props)
        AssertUtils.assertNotNull(props.company)
        AssertUtils.assertNotNull(props.lastUpdatedBy)
    }

    static def testFindDomainInstanceByMethod(){
        def props = DomainHelpers.populateSourcePropertiesHavingQueryMap(configMap,TestData.domainInstanceProperties)
        def instance = DomainHelpers.findDomainInstanceByMethod(configMap.underGroup, TestData.domainInstanceProperties, props)
        AssertUtils.assertNotNull(instance)
        AssertUtils.assertNotNull(instance.company)
        AssertUtils.assertNotNull(instance.underGroup)
        AssertUtils.assertNotNull(instance.lastUpdatedBy)

    }

    static def testCreateDomainInstance(){
        def instance = DomainHelpers.createDomainInstance("Party",TestData.domainInstanceProperties)
        AssertUtils.assertNotNull(instance)
        AssertUtils.assertNotNull(instance.company)
        AssertUtils.assertNotNull(instance.underGroup)
        AssertUtils.assertNotNull(instance.lastUpdatedBy)
        instance.save()
        instance = AccountLedger.findByName(TestData.domainInstanceProperties.name)
        AssertUtils.assertNotNull(instance)
        AssertUtils.assertNotNull(instance.partyId)
        AssertUtils.equalsLong(instance.partyId,1)
//        AssertUtils.equals(instance.name,TestData.domainInstanceProperties.name)
//        AssertUtils.equals(instance.underGroup.name,AccountGroup.SUNDRY_CREDITORS)
    }

    static def testGetDomainInstanceByQueryMap(){
        def instance = DomainHelpers.getDomainInstanceByQueryMap("company", configMap, TestData.domainInstanceProperties)
        AssertUtils.assertNotNull(instance)
    }

    public static void main(String[] args) {
        checkPrerequisites()
    }

    static def createTestData(){

        def org = new Organization(name: organizationName)
        org.save()

        def user = new User(username: userName,password: "u123",organization: org)
        user.save()

        AssertUtils.assertNotNull(User.findByUsername(userName))

        def company = new Company(name: companyName,registrationNo: "123",organization: org,financialFrom: new Date(),booksBeginigFrom: new Date(),lastUpdatedBy: user)
        company.save()
        AssertUtils.assertNotNull(Company.findByName(companyName))

    }

    static def clearTestData(){

        try{
            def ledger=AccountLedger.findByName(ledgerName)
            ledger.delete(flush: true)
            ledger=AccountLedger.findByName(ledgerName)
            AssertUtils.assertNull(ledger)
        }
        catch(Exception e){
            e.printStackTrace()
        }

        try{
            def company = Company.findByName(companyName)
            company.lastUpdatedBy=null
            company.delete(flush: true)
            company =Company.findByName(companyName)
            AssertUtils.assertNull(company)
        }
        catch(Exception e){
            e.printStackTrace()
        }

        try{
            def org = Organization.findByName(organizationName)
            org.delete(flush: true)
            org=Organization.findByName(organizationName)
            AssertUtils.assertNull(org)
        }
        catch(Exception e){
            e.printStackTrace()
        }




    }
}
