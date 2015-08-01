package com.reports

import com.annotation.ParentScreen


@ParentScreen(name = "Account Books", fullName = "Sales Register", sortList = 1, link = "/reports/salesRegister")
class SalesRegisterReportController {

    def index() {}
}
