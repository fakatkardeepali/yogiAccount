package com.common

import com.system.Company
import grails.rest.Resource


@Resource(uri = "/taxSetting", formats = ['json', 'xml'])
class TaxSetting {

    AccountFlag tax
    boolean isDisplay = false
    Company company
    Integer uniqueKey = 0

    Date dateCreated, lastUpdated

    static constraints = {

    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
