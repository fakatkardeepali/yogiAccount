package com.reports

import com.annotation.ParentScreen


@ParentScreen(name = "Account Books", fullName = "Purchase Register", sortList = 1, link = "/reports/purchaseRegister")
class PurchaseRegisterReportController {

    def index() {}
}
