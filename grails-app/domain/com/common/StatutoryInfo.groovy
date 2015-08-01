package com.common

import com.system.Company
import com.system.State
import grails.rest.Resource


@Resource(uri = "/statutoryInfo", formats = ['json', 'xml'])
class StatutoryInfo {

    Integer uniqueKey = 0
    Company company
    boolean allowSelectOfVatTaxDuringEntry = false
    boolean activateE1OrE2TransactionVat = false
    boolean allowAlteraionOfTdsRateForLowerDeductions = false
    boolean allowAlteraionOfTdsNatureOfPaymentInExpense = false
    boolean enableValueAddedTax = false
    boolean vatAlterDetails = false
    State state
    AccountFlag typeOfDealer
    Date regularVatApplicableFrom
    //TDS
    boolean tdsEnableTaxDeductedAtSource = false
    boolean tdsAlterDetails = false
    String tdsTaxAssessmentNo
    String tdsIncomeTaxCircleward
    AccountFlag tdsDeductorCollectorType
    String tdsResponsiblePerson
    String tdsDesignation

// Service Tax
    boolean enableServiceTax = false
    boolean serviceTaxAlterDetails = false
    String serviceTaxRegNo
    Date dateOfReg
    String assesseeCode
    String premisesCodeNo
    AccountFlag typeOfOrganization
    boolean isLargeTaxPayer = false
    String largeTaxPayerUnit
    String divisionCode
    String divisionName
    String rangeCode
    String rangeName
    String commisionerateCode
    String commisionerateName
    //TCS
    boolean tcsEnableTaxCollectedAtSource = false
    boolean tcsAlterDetails = false
    String tcsTaxAssessmentNo
    String tcsIncomeTaxCircleward
    AccountFlag tcsDeductorCollectorType
    String tcsResponsiblePerson
    String tcsDesignation

    String vatTinComposition
    String vatTinRegular
    String localSalesTaxNumber
    String interStateSalesTaxNumber
    String panTaxNo


    static constraints = {

        state nullable: true
        typeOfDealer nullable: true
        typeOfOrganization nullable: true
        regularVatApplicableFrom nullable: true
        tdsDeductorCollectorType nullable: true
        tcsDeductorCollectorType nullable: true
        tdsDesignation nullable: true
        tdsIncomeTaxCircleward nullable: true
        tdsResponsiblePerson nullable: true
        tdsTaxAssessmentNo nullable: true
        tcsDesignation nullable: true
        tcsIncomeTaxCircleward nullable: true
        tcsResponsiblePerson nullable: true
        tcsTaxAssessmentNo nullable: true
        panTaxNo nullable: true
        serviceTaxRegNo nullable: true
        dateOfReg nullable: true
        assesseeCode nullable: true
        premisesCodeNo nullable: true
        largeTaxPayerUnit nullable: true
        divisionCode nullable: true
        divisionName nullable: true
        rangeCode nullable: true
        rangeName nullable: true
        commisionerateCode nullable: true
        commisionerateName nullable: true
        vatTinComposition nullable: true
        vatTinRegular nullable: true
        localSalesTaxNumber nullable: true
        interStateSalesTaxNumber nullable: true
    }

    static mapping = {
        regularVatApplicableFrom type: 'date'
        dateOfReg type: 'date'
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
