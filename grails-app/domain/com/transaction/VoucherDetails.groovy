package com.transaction

import com.common.VoucherStatus
import com.master.AccountLedger
import com.system.Company

class VoucherDetails {


    Integer uniqueKey = 0
    Company company
    AccountLedger particulars
    BigDecimal rate = 0
    BigDecimal amount = 0
    String amountStatus
    String narration


    static belongsTo = [voucher: Voucher]

    static constraints = {
        particulars nullable: true
        amountStatus nullable: true
        narration nullable: true, size: 0..2048
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }

    static childStatus(Voucher v) {
        def data = VoucherStatus.findByVoucherProperty(v.voucherType.property)
        if (data) {

            return data.childStatus

        }
        return ""
    }

    static parentStatus(Voucher v) {
        def data = VoucherStatus.findByVoucherProperty(v.voucherType.property)
        if (data) {
            return data.parentStatus
        }
        return ""
    }


    static buildFromJSON(org.codehaus.groovy.grails.web.json.JSONObject boDet, Company company, Voucher voucher) {
        return new VoucherDetails(company: company,
                particulars: AccountLedger.get(boDet.ledger as Long),
                amount: boDet.debit as BigDecimal,
                rate: 0,
                amountStatus: voucher ? childStatus(voucher) : "",
                narration: boDet.narration ?: ""
        )
    }
}
