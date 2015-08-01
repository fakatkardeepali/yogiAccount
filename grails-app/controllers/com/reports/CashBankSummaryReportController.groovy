package com.reports

import com.annotation.ParentScreen
import com.report.MasterReportService
import grails.converters.JSON

@ParentScreen(name = "Account Books", fullName = "Cash/Bank Summary", sortList = 1, link = "/reports/cashBankSummary")
class CashBankSummaryReportController {
    def jasperService
    MasterReportService masterReportService

    def index() {}

    def cashBankSummaryReport() {
        def reportDetails = [];
        def Data = [];
        def paramsData = JSON.parse(params.groupObject);

        params._format = "PDF"

        if (Boolean.parseBoolean(params.isGroup)) {
            ArrayList<String> arr = params.array.split(",");

            if (arr) {
                Data = masterReportService.findAllCashBankSummaryReport(arr, session["company"], paramsData.fromDate, paramsData.toDate);

                if (Data) {
                    reportDetails.push(Data);
                }
            }
            params._file = "../reports/groupSummary"
        } else {
            Data = masterReportService.findAllCashBankSummaryReportByGroupId(paramsData?.id as Long, session["company"], paramsData.fromDate, paramsData.toDate);

            if (Data) {
                reportDetails.push(Data);
            }
            params._file = "../reports/groupVouchers"
        }
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller: 'cashBankSummaryReport', action: 'generateReport', model: [data: reportDetails], params: params)
    }

    def generateReport() {
        try {
            def reportModel = this.getProperties().containsKey('chainModel') ? chainModel : null
            def report = jasperService.buildReportDefinition(params, request.getLocale(), reportModel)
//            addJasperPrinterToSession(request.getSession(), report.jasperPrinter)
            generateResponse(report)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    /**
     * Code borrowed from JasperController. Currently JasperController doesn't allow for custom
     * names for the attachment. This gets us past that.
     *
     * http://jira.codehaus.org/browse/GRAILSPLUGINS-2516
     */
    def generateResponse = { reportDef ->
        if (!reportDef.fileFormat.inline && !reportDef.parameters._inline) {
            //response.setHeader("Content-disposition", "attachment; filename=\"${reportDef.parameters._name ?: reportDef.name}.${reportDef.fileFormat.extension}\"");
            response.contentType = reportDef.fileFormat.mimeTyp
            response.characterEncoding = "UTF-8"
            response.outputStream << reportDef.contentStream.toByteArray()
        } else {
            render(text: reportDef.contentStream, contentType: reportDef.fileFormat.mimeTyp, encoding: reportDef.parameters.encoding ? reportDef.parameters.encoding : 'UTF-8');
        }
    }
}
