package com.transaction

import com.common.VoucherStatus
import com.master.AccountLedger
import com.master.Parameters
import com.master.VoucherType
import com.system.Company
import com.system.User
import grails.rest.Resource


@Resource(uri = "/voucher", formats = ['json', 'xml'])
class Voucher {

    String voucherNo
    String referenceNo
    VoucherType voucherType
    Integer uniqueKey = 0
    Date date
    //todayDay
    AccountLedger partyName
    AccountLedger salesLedger
    String narration

    //creditOrDebit
    BigDecimal amount = 0
    String amountStatus
    String rowStatus

    boolean isPostDated = false

    String deliveryNoteNo
    String despatchDocNo
    String despatchThrough
    String destination

    String oredrNo
    String mode_TremsOfPayment
    String otherReference
    String termsOfDelivery

    BigDecimal rate = 0
    String buyers
    String buyerAddress
    String cosigneeName
    String cosigneeAddress
    String tIn_SalesTaxNo
    Company company
    User lastUpdatedBy
    Date dateCreated
    Date lastUpdated

    Voucher voucher


    static hasMany = [voucherDetails: VoucherDetails, partyAccount: PartyAccount, voucherBillDetails: VoucherBillDetails]

    static constraints = {

        voucherNo nullable: true
        date nullable: true
        referenceNo nullable: true
        voucherType nullable: true
        partyName nullable: true
        salesLedger nullable: true
        narration size: 0..2048, nullable: true
        amountStatus nullable: true
        rowStatus nullable: true

        deliveryNoteNo nullable: true
        despatchDocNo nullable: true
        despatchThrough nullable: true
        destination nullable: true
        oredrNo nullable: true
        mode_TremsOfPayment nullable: true
        otherReference nullable: true
        termsOfDelivery nullable: true
        buyers nullable: true
        buyerAddress nullable: true
        cosigneeName nullable: true
        cosigneeAddress nullable: true
        tIn_SalesTaxNo nullable: true

        voucher nullable: true
    }

    static mapping = {
        date type: "date"
        company lazy: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }

    static parametersInsert(VoucherType voucherType, Date date) { //parameter update
        if (voucherType.methodOfVoucherNumbering == "1") {
            if (voucherType.useAdavanceConfiguration) {
                def data = Parameters.findByVoucherTypeAndApplicableFromGreaterThanEqualsAndApplicableToLessThanEquals(voucherType, date, date);
                if (data) {
                    data.latestNumber = data.latestNumber + 1;
                    data.save();
                }
            } else {
//                def data = VoucherType.findById(voucherType.id);
                if (voucherType) {
                    voucherType.lastNumber = voucherType.lastNumber + 1;
                    voucherType.save(flush: true);

                }
            }
        }


    }


    static getVoucherNumber(Long id, Date date, String no) {
        def data = VoucherType.findById(id);
        if (data) {
            if (data.methodOfVoucherNumbering == "1") {
                if (data.useAdavanceConfiguration) {
                    return Parameters.findByVoucherTypeAndApplicableFromGreaterThanEqualsAndApplicableToLessThanEquals(data, date, date).latestNumber + 1;
                } else {
                    return (data.lastNumber>=0) ? (data.lastNumber + 1) : 0;
                }
            } else {
                return no;
            }

        }
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

    static buildFromJSON(org.codehaus.groovy.grails.web.json.JSONObject boDet, Company company, Voucher voucher, User user) {
        return new Voucher(partyName: AccountLedger.get(boDet.ledger as Long),
                amount: boDet.debit as BigDecimal,
                rate: 0,
                amountStatus: voucher ? childStatus(voucher) : "",
                narration: boDet.narration ?: "",
                lastUpdatedBy: user,
                company: company,
                voucher: voucher,
                date: voucher.date)
    }
}
