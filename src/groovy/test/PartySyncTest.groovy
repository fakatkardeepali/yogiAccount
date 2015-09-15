package test

import com.helpers.DomainHelpers
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

    static def checkPrerequisites(){

        createTestData()

        checkQueryMapProperties()
        testGetDomainInstanceByQueryMap()
        testFindDomainInstanceByMethod()
        testCreateDomainInstance()

        def company = Company.findByRegistrationNo("123")
        AssertUtils.assertNotNull(company)
        //assert company.registrationNo.equalsIgnoreCase("123")
        AssertUtils.equals(company.registrationNo,"123")

        clearTestData()
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
    }

    static def testGetDomainInstanceByQueryMap(){
        def instance = DomainHelpers.getDomainInstanceByQueryMap("company", configMap, TestData.domainInstanceProperties)
        AssertUtils.assertNotNull(instance)
    }

    public static void main(String[] args) {
        checkPrerequisites()
    }

    static def createTestData(){

        def org = new Organization(name: "org1")
        org.save()

        def user = new User(username: userName,password: "u123",organization: org)
        user.save()

        AssertUtils.assertNotNull(User.findByUsername(userName))

        def company = new Company(name: companyName,registrationNo: "123",organization: org,financialFrom: new Date(),booksBeginigFrom: new Date(),lastUpdatedBy: user)
        company.save()
        AssertUtils.assertNotNull(Company.findByName(companyName))

    }

    static def clearTestData(){
        def company = Company.findByName(companyName)
        company.delete()
    }
}
