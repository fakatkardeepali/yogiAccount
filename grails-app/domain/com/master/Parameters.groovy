package com.master

import com.system.Company
import grails.rest.Resource

@Resource(uri = "/parameters", formats = ['json', 'xml'])
class Parameters {

    Integer uniqueKey = 0
    Date applicableFrom
    Date applicableTo
    Integer startNumber = 0
    Integer latestNumber = 0
    String prefix
    String postfix
    Company company

    static belongsTo = [voucherType: VoucherType]

    static constraints = {
        applicableFrom nullable: true
        applicableTo nullable: true
        prefix nullable: true
        postfix nullable: true
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
        latestNumber = startNumber;
    }

    static buildFromJSON(org.codehaus.groovy.grails.web.json.JSONObject boDet, company) {
        return new Parameters(applicableFrom: Date.parse("yyyy-MM-dd", boDet.applicableFrom),
                applicableTo: Date.parse("yyyy-MM-dd", boDet.applicableTo),
                startNumber: boDet.startNumber as Integer,
                prefix: boDet?.prefix ?: "", postfix: boDet?.postfix ?: "",
                company: Company.get(company.id as Long))

    }
}
