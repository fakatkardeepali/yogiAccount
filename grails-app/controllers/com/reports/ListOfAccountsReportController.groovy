package com.reports

import com.annotation.ParentScreen

@ParentScreen(name = "Statistics", fullName = "List Of Accounts", sortList = 1, link = "/reports/listOfAccountsReport")
class ListOfAccountsReportController {

    def index() {}
}
