package com.system

import com.annotation.ParentScreen
import grails.converters.JSON

@ParentScreen(name = "Utilities", fullName = "Split Company", sortList = 1, link = "/split/page")
class SplitController {

    SystemService systemService

    def index() {}

    def findCompany() {
        def data = systemService.getCompanyById(session['company'].id as Long)
        if (data) {
            render data as JSON
        } else {
            render '[]'
        }
    }

    def save() {
        Long compId = session['company'].id as Long
        int i = systemService.createCompanyFrom(params.from, params.to, session['activeUser'].user.id as Long, compId);
        if (i == 1) {
            i = systemService.createCompanyFrom(params.to, "", session['activeUser'].user.id as Long, compId);
        }
        render i;
    }
}
