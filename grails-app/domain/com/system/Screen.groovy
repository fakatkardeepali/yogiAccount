package com.system

import grails.rest.Resource


@Resource(uri = "/screen", formats = ['json', 'xml'])
class Screen {

    String name, controller, filter, domainName
    Screen parentScreen
    Integer sortList = 1
    Integer uniqueKey = 0
    String link

    static constraints = {
        name nullable: true
        controller nullable: true
        filter nullable: true
        domainName nullable: true
        parentScreen nullable: true
        link nullable: true
    }

    def beforeInsert() {
        uniqueKey = (last()?.uniqueKey ?: 0) + 1;
    }
}
