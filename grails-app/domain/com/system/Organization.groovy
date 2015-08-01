package com.system

import grails.rest.Resource


@Resource(uri = "/organization", formats = ['json', 'xml'])
class Organization {

    String name


    Date lastUpdated, dateCreated
//    User lastUpdatedBy
//    Company company
    Integer uniqueKey = 0

    static constraints = {
        name nullable: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
