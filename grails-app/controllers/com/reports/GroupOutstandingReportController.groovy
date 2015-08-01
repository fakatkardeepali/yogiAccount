package com.reports

import com.annotation.ParentScreen
import com.report.MasterReportService
import grails.converters.JSON

@ParentScreen(name = "Outstanding", fullName = "Group Outstanding", sortList = 1, link = "/reports/groupOutstanding")
class GroupOutstandingReportController {
    def jasperService
    MasterReportService masterReportService

    def index() {}

    def groupOutstandingReport() {
        def reportDetails = [];
        def data = [];
        def paramsData = JSON.parse(params.groupObject);

        params._format = "PDF"
        if (paramsData?.id) {
            if (paramsData.reportStatus == "Group") {
                data = masterReportService.getGroupOutstandingByCompanyAndgroupId(session["company"].id, paramsData?.id as Long, paramsData?.fromDate, paramsData?.toDate);

                if (data) {
                    reportDetails.push(data);
                }
                params._file = "../reports/GroupOutstandingReport"
            } else {
                data = masterReportService.getLedgerOutstandingByCompanyAndgroupId(session["company"].id, paramsData?.id as Long, paramsData?.fromDate, paramsData?.toDate);

                if (data) {
                    reportDetails.push(data);
                }
                params._file = "../reports/ledgerOutstandingReport"
            }
        }

        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller: 'groupOutstandingReport', action: 'generateReport', model: [data: reportDetails], params: params)
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
