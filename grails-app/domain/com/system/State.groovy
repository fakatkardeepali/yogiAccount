package com.system

import grails.rest.Resource


@Resource(uri = "/state", formats = ['json', 'xml'])
class State {

    String name
    Country country

    Date lastUpdated, dateCreated
//    User lastUpdatedBy

    Integer uniqueKey = 0

    static constraints = {
        name nullable: false
        country nullable: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
