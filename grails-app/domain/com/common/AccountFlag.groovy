package com.common

import grails.rest.Resource


@Resource(uri = "/accountFlag", formats = ['json', 'xml'])
class AccountFlag {

    String name
    String description
    String remark
    Integer uniqueKey = 0
    AccountFlag parent


    static constraints = {
        name nullable: true
        description nullable: true
        remark nullable: true
        parent nullable: true
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
