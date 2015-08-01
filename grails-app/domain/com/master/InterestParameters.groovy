package com.master

import com.common.AccountFlag
import com.system.Company
import com.system.SystemService
import grails.rest.Resource
import org.codehaus.groovy.grails.web.json.JSONObject

@Resource(uri = "/interestParameter", formats = ['json', 'xml'])
class InterestParameters {

    Integer srno = 0
    BigDecimal rate = 0
    Integer uniqueKey = 0
    String rateper
    String rateOn
    Date applicableDateFrom
    Date applicableDateTo
    AccountFlag rounding
    Company company
//    AccountLedger accountLedger


    static constraints = {
        rateOn nullable: true
        rateper nullable: true
        applicableDateFrom nullable: true
        applicableDateTo nullable: true
        rounding nullable: true
//        accountLedger nullable: false
    }

    static belongsTo = [accountLedger: AccountLedger]

    static mapping = {
        applicableDateFrom type: 'date'
        applicableDateTo type: 'date'
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }

    static saveChild(JSONObject data, Long id) {
        return new InterestParameters(
                rate: data.rate,
                rateper: data.rateper,
                applicableDateFrom: data?.applicableDateFrom ? Date.parse("yyyy-MM-dd", data?.applicableDateFrom) : null,
                applicableDateTo: data?.applicableDateTo ? Date.parse("yyyy-MM-dd", data?.applicableDateTo) : null,
                company: Company.get(id)
        )
    }
}
