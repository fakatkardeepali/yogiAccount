package com.master

import com.common.AccountFlag
import com.system.Company
import com.system.State
import com.system.User
import grails.rest.Resource

@Resource(uri = "/accountLedger", formats = ['json', 'xml'])
class AccountLedger {

    String name
    String alias
    AccountGroup underGroup
    String note
    Boolean usePrimaryGroup = false
    Date reconciliationDate
    boolean maintainBill = false
    Integer creditDays = 0
    String accountNo
    String accountName
    String branchName
    String bsrCode
    String ifscCode
    String mailingName
    String address
    State state
    String pinCode
    String contactPerson
    String telephoneNo
    String mobileNo
    String faxNo
    String email
    boolean provideBankDetails = false
    String panNo
    String salesTaxNo
    String cstNo
    AccountFlag typeOfDuty
    AccountFlag dutyHead
    Integer percentageOfCalculation = 0
    AccountFlag methodOfCalculation
    AccountFlag roundMethod
    Integer uniqueKey = 0
    Company company
    User lastUpdatedBy
    Date dateCreated
    Date lastUpdated
    BigDecimal openingBalance = 0
    String status
    boolean activeInterestCalculation = false
    String eccNo

// statutory form members
    // statutory tds
    boolean isTdsDeductee = false
    AccountFlag deducteeType
// statutory service
    boolean isServiceTax = false
    AccountFlag category
    String notificationNo
    boolean isAbatement = false
    Integer percentage = 0
    // statutory vat
    boolean isVat = false
    AccountFlag taxClass
// statutory tcs
    boolean isTcsApplicable = false
    AccountFlag buyerLessee
    boolean noCollectionApplicable = false
    String sectionNumber
    Integer tcsLowerRate = 0
    boolean ignoreSurchargeExceptionLimit = false


    static constraints = {
        name nullable: false
        alias nullable: true
        underGroup nullable: false
        note nullable: true
        accountNo nullable: true
        accountName nullable: true
        branchName nullable: true
        bsrCode nullable: true
        ifscCode nullable: true
        mailingName nullable: true
        address nullable: true
        state nullable: true
        pinCode nullable: true
        contactPerson nullable: true
        telephoneNo nullable: true
        mobileNo nullable: true
        faxNo nullable: true
        email nullable: true
        eccNo nullable: true
        panNo nullable: true
        salesTaxNo nullable: true
        cstNo nullable: true
        typeOfDuty nullable: true
        dutyHead nullable: true
        methodOfCalculation nullable: true
        roundMethod nullable: true
        deducteeType nullable: true
        category nullable: true
        notificationNo nullable: true
        taxClass nullable: true
        buyerLessee nullable: true
        reconciliationDate nullable: true
        state nullable: true
        sectionNumber nullable: true
    }

    static hasMany = [interestParameter: InterestParameters]


    static mapping = {
        reconciliationDate type: 'date'
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
