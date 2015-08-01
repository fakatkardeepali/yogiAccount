package com.system

import grails.rest.Resource


@Resource(uri = "/country", formats = ['json', 'xml'])
class Country {

    String name

    Date lastUpdated, dateCreated
//    User lastUpdatedBy
    Integer uniqueKey = 0

    static constraints = {
        name nullable: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
