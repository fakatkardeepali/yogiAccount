package com.system

import grails.rest.Resource


@Resource(uri = "/user", formats = ['json', 'xml'])
class User {

    String username, password
    Date lastUpdated, dateCreated
    Organization organization
    Company company

    Integer uniqueKey = 0
    boolean isAdmin = false, enable = false, isClient = false
    boolean useMultipleCompany = false


    static constraints = {
        username nullable: false
        password nullable: false
        organization nullable: false
        company nullable: true

    }

    static mapping = {
        organization lazy: false
        company lazy: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;

    }


}
