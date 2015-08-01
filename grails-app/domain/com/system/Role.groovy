package com.system

import grails.rest.Resource


@Resource(uri = "/role", formats = ['json', 'xml'])
class Role {


    String name

    Date lastUpdated, dateCreated
    User lastUpdatedBy
    Company company
    Integer uniqueKey = 0


    static hasMany = [roleChild: RoleChild]

    static constraints = {

        name nullable: false
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
