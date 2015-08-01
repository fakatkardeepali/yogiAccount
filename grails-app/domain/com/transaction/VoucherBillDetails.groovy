package com.transaction

import com.common.AccountFlag
import com.master.AccountLedger
import com.system.Company
import com.system.User
import org.codehaus.groovy.grails.web.json.JSONObject

class VoucherBillDetails {

    Integer uniqueKey = 0
    Company company
    AccountLedger partyName
    AccountFlag typeOfRef
    String billNo
    BigDecimal crDays = 0
    BigDecimal amount = 0
    String amountStatus
    String narration
    Date billDate

//    BigDecimal remainAmount = 0

    Date lastUpdated, dateCreated
    User lastUpdatedBy

    static constraints = {
        partyName nullable: true
        typeOfRef nullable: true
        billNo nullable: true
        amountStatus nullable: true
        narration nullable: true
        voucher nullable: true
        billDate nullable: true
//        remainAmount nullable: true
    }

    static belongsTo = [voucher: Voucher]

    PartyAccount findbyId(Long id) {
        def id1 = PartyAccount.findById(id)
        return id1;
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }

    static mapping = {
        billDate type: 'date'
    }

    static buildFromJSON(JSONObject boDet, Company company, AccountLedger party, User user) {
        return new VoucherBillDetails(company: company,
                partyName: party as AccountLedger,
                typeOfRef: AccountFlag.get(boDet?.typeRef as Long),
                billNo: boDet.billNo,
                billDate: boDet?.billDate ? Date.parse("yyyy-MM-dd", boDet?.billDate) : "",
                crDays: boDet?.crDays as BigDecimal,
                amount: boDet?.amount as BigDecimal,
                amountStatus: boDet?.amountStatus ?: "",
                narration: boDet?.narration ?: "",
//                remainAmount: boDet?.amount as BigDecimal,
                lastUpdatedBy: user as User)
    }
}
