package com.master

import com.system.Company
import com.system.User
import grails.rest.Resource

@Resource(uri = "/voucherType", formats = ['json', 'xml'])
class VoucherType {

    String name
    Integer uniqueKey = 0
    String alias
    VoucherType typeOfVoucher
//  for default voucher updateion of  typeOfVoucher
    boolean isTypeUpdate = false
//  end
    String methodOfVoucherNumbering
    boolean isPreventDuplicates = false
    boolean useAdavanceConfiguration = false
    boolean useEffectiveDatesForVouchers = false
    boolean useCommonNarration = false
    boolean narrationForEachEntry = false
    boolean printAfterSavingVoucher = false
    String defaultPrintTitle
    Integer startNumber = 0
    Integer lastNumber = 0
    Integer withOfNumericalPart = 0
    Integer prefixWithZero = 0
    Company company
    User lastUpdatedBy
    Date dateCreated
    Date lastUpdated

    //property for know this voucher related to which voucher
    String property

    static hasMany = [parameters: Parameters]

    static constraints = {
        name nullable: false
        alias nullable: true
        typeOfVoucher nullable: true
        methodOfVoucherNumbering nullable: true
        defaultPrintTitle nullable: true
        property nullable: true
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
