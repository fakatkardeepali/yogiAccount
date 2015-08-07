package com.transaction

import com.annotation.ParentScreen
import com.common.AccountFlag
import com.master.MasterService
import com.report.MasterReportService
import com.system.SystemService
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND

@ParentScreen(name = "Transaction", fullName = "Voucher", sortList = 1, link = "/voucher/create")
@Transactional(readOnly = true)
class VoucherController {

    SystemService systemService
    MasterService masterService
    TransactionService transactionService
    MasterReportService masterReportService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Voucher.list(params), model: [voucherInstanceCount: Voucher.count()]
    }

    def show(Voucher voucherInstance) {
        respond voucherInstance
    }

    def create() {
        respond new Voucher(params)
    }

    @Transactional
    def save(Voucher voucherInstance) {
        ArrayList<Voucher> voucher = [];

        bindData(voucherInstance, params, [exclude: ['lastUpdatedBy', 'company', 'date']])

        voucherInstance.lastUpdatedBy = systemService.getUserById(session['activeUser'].user.id as Long);
        voucherInstance.company = systemService.getCompanyObjectById(session['company'].id as Long);
        voucherInstance.date = Date.parse("yyyy-MM-dd", params.date);
        voucherInstance.amountStatus = VoucherDetails.parentStatus(voucherInstance);
        voucherInstance.voucherNo = Voucher.getVoucherNumber(voucherInstance.voucherType.id, voucherInstance.date, params.voucherNo)

        if (voucherInstance == null) {
            notFound()
            return
        }

        if (params.child) {
            def child = JSON.parse(params.child);
            if (child) {
                child.each { c ->
//                    voucherInstance.addToVoucherDetails(VoucherDetails.buildFromJSON(c, session['company'], voucherInstance));
                    voucher.add(Voucher.buildFromJSON(c, session['company'], voucherInstance, voucherInstance.lastUpdatedBy));
                }
            }

            if (voucherInstance.voucherType.property == "Contra") {
                new PartyAccount(billNo: voucherInstance?.voucherNo ?: 0, company: voucherInstance.company,
                        partyName: voucherInstance, crDays: 0,
                        voucher: voucherInstance ?: null,
                        amount: voucherInstance.amount as BigDecimal,
                        remainAmount: voucherInstance.amount as BigDecimal,
                        amountStatus: voucherInstance.amountStatus,
                        lastUpdatedBy: voucherInstance.lastUpdatedBy,
                        narration: voucherInstance?.narration ?: "",
                        billDate: voucherInstance?.lastUpdated ? voucherInstance?.lastUpdated : null,
                        typeOfRef: AccountFlag.findByName("On Account")).save();
            }
        }

        if (voucherInstance.partyName.maintainBill) {
            if (params.billChild) {
                def child = JSON.parse(params.billChild);
                if (child) {
                    child.each { c ->
                        def accountFlag = AccountFlag.get(c?.typeRef as Long);
                        if (accountFlag.name == "Agst Ref.") {
                            PartyAccount.updateChildByBillSatus(c, session['company'], voucherInstance.partyName)
                            def partyAccount = PartyAccount.updateChildByBillSatus(c, session['company'], voucherInstance.partyName)
                            partyAccount.save();
                        } else {
                            voucherInstance.addToPartyAccount(PartyAccount.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                        }
                        voucherInstance.addToVoucherBillDetails(VoucherBillDetails.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                    }
                }
            }
        }

        if (voucherInstance.hasErrors()) {
            render voucherInstance.errors
        } else {
            voucherInstance.save()
            Voucher.saveAll(voucher);
            Voucher.parametersInsert(voucherInstance.voucherType, voucherInstance.date);

            render true;
        }
    }

    def edit(Voucher voucherInstance) {
        def data = transactionService.findVoucherById(voucherInstance.id as Long);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    @Transactional
    def update(Voucher voucherInstance) {

        if (voucherInstance == null) {
            notFound()
            return
        }

        def voucherBillDetails = VoucherBillDetails.findAllByVoucher(voucherInstance);
        if (voucherBillDetails.size() > 0) {
            voucherBillDetails.each { c ->
                if (c.typeOfRef.name == "Agst Ref.") {
                    def partyAccount = PartyAccount.findByBillNoAndPartyName(c.billNo, c.partyName);
                    if (partyAccount) {
                        partyAccount.remainAmount = partyAccount.remainAmount + c.amount
                        partyAccount.save();
                    }
                }
            }
        }

        def voucherEdit = Voucher.findAllByVoucher(voucherInstance);
        if (voucherEdit) {
            voucherEdit.each { c ->
                c.delete(flush: true);
            }
        }

        Voucher.executeUpdate("delete VoucherDetails as r where r.voucher.id=:id", [id: voucherInstance.id]);
//      Voucher.executeUpdate("delete PartyAccount as r where r.voucher.id=:id", [id: voucherInstance.id]);
        Voucher.executeUpdate("delete VoucherBillDetails as r where r.voucher.id=:id", [id: voucherInstance.id]);

        voucherInstance.properties = params
        bindData(voucherInstance, params, [exclude: ['date']])
        ArrayList<Voucher> voucher = [];
        voucherInstance.date = Date.parse("yyyy-MM-dd", params.date);
        voucherInstance.amountStatus = VoucherDetails.parentStatus(voucherInstance);
//        voucherInstance.voucherNo = Voucher.getVoucherNumber(voucherInstance.voucherType.id,voucherInstance.date,params.voucherNo)

        if (params.child) {
            def child = JSON.parse(params.child);
            if (child) {
                child.each { c ->
//                   voucherInstance.addToVoucherDetails(VoucherDetails.buildFromJSON(c, session['company'], voucherInstance));
                    voucher.add(Voucher.buildFromJSON(c, session['company'], voucherInstance, voucherInstance.lastUpdatedBy));
                }
            }
        }

        if (params.billChild && voucherInstance.partyName.maintainBill) {
            def child = JSON.parse(params.billChild);
            if (child) {
                child.each { c ->
                    def accountFlag = AccountFlag.get(c?.typeRef as Long);
                    if (accountFlag.name == "Agst Ref.") {
                        def partyAccount = PartyAccount.updateChildByBillSatus(c, session['company'], voucherInstance.partyName)
                        partyAccount.save();
                    } else {
                        voucherInstance.addToPartyAccount(PartyAccount.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                    }
                    voucherInstance.addToVoucherBillDetails(VoucherBillDetails.buildFromJSON(c, session['company'], voucherInstance.partyName, voucherInstance.lastUpdatedBy));
                }
            }
        }

        if (voucherInstance.hasErrors()) {
            render voucherInstance.errors
        } else {
            voucherInstance.save()
            Voucher.saveAll(voucher);
            render true;
        }

    }

    @Transactional
    def delete(Long id) {
        def voucherInstance = Voucher.findById(id);

        def voucherBillDetails = VoucherBillDetails.findAllByVoucher(voucherInstance);
        if (voucherBillDetails.size() > 0) {
            voucherBillDetails.each { c ->
                if (c.typeOfRef.name == "Agst Ref.") {
                    def partyAccount = PartyAccount.findByBillNoAndPartyName(c.billNo, c.partyName);
                    if (partyAccount) {
                        partyAccount.remainAmount = partyAccount.remainAmount + c.amount
                        partyAccount.save();
                    }
                }
            }
        }

        def voucherEdit = Voucher.findAllByVoucher(voucherInstance);
        if (voucherEdit) {
            voucherEdit.each { c ->
                c.delete(flush: true);
            }
        }

        Voucher.executeUpdate("delete VoucherDetails as r where r.voucher.id=:id", [id: voucherInstance.id]);
//      Voucher.executeUpdate("delete PartyAccount as r where r.voucher.id=:id", [id: voucherInstance.id]);
        Voucher.executeUpdate("delete VoucherBillDetails as r where r.voucher.id=:id", [id: voucherInstance.id]);


        if (voucherInstance == null) {
            notFound()
            return
        }

//        voucherInstance.delete flush: true

        if (voucherInstance.delete(flush: true)) {
            render ""
        } else {
            render true;
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Voucher.label', default: 'Voucher'), voucherInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'voucher.label', default: 'Voucher'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def findledgerList() {
        def arr = params.arr.split(",");
        def data = transactionService.getAllLedgerByComapnyAndProperty(session["company"].id, arr);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def findledgerListNotInProperty() {
        def arr = params.arr.split(",");
        def data = transactionService.getAllLedgerByComapnyAndNotInProperty(session["company"].id, arr);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def findBalanceByLedger() {
        def data = [];
        if (params.id) {
            data = transactionService.getBalanceSumByLedgerIdAndCompanyId(params.id as Long, session['company'].id)
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def findBillRefList() {
        def data = transactionService.getBillRefByRemark("billRef");
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def findAllBillByBillRef() {
        if (params?.id && params?.partyName) {
            def data = transactionService.getAllBillByBillRef(session['company'], params?.partyName as Long);
            if (data) {
                render data as JSON;
            }
        } else {
            render "[]";
        }
    }

    def findVoucherStatusByProperty() {
        if (params?.data) {
            def data = systemService.getVoucherStatusByProperty(params?.data);
            if (data) {
                render data as JSON;
            }
        } else {
            render "[]"
        }
    }

    //ledger report list 
    def generateLedgerReport() {
        if (params?.id) {
            def data = masterReportService.getLedgerByCompanyAndLedgerId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            if (data) {
                render data as JSON;
            } else {
                render "[]";
            }
        }
    }

    //Sales Register Report Data
    def generateSalesRegisterReport() {
        def data = masterReportService.getSalesRegisterByCompanyAndDateBetween(session["company"].id, params.id as Long, params?.fromDate, params?.toDate, params.property);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    //funcion for ledger report and from date and to date
    def findAccountGroup() {
        def data = masterService.findAllAccountGroupByCompany(session["company"]);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def findAccountGroupByName() {
        ArrayList<String> arr = params.arr.split(",");
        if (arr) {
            def data = masterReportService.findAllAccountGroupByCompanyAndName(arr, session["company"]);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    // Find All Voucher Type
    def findVoucherType() {
        def data = masterService.findAllVoucherTypeByCompanyAndProperty(session["company"], params.property);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    //function for group wise summary and from date and to date report
    def generateGroupSummaryReport() {
        if (params?.id) {
            def data = masterReportService.getGroupSummaryByCompanyAndgroupId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            if (data) {
                render data as JSON;
            } else {
                render "[]";
            }
        }
    }


    def generateGroupVoucherReport() {
        if (params?.id) {
//            def data = masterReportService.getGroupVoucherByCompanyAndgroupId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            def data = masterReportService.getGroupVoucherByCompanyAndLedgerId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            if (data) {
                render data as JSON;
            } else {
                render "[]";
            }
        }
    }

    def generateLedgerOutstandingReport() {
        if (params?.id) {
            def data = masterReportService.getLedgerOutstandingByCompanyAndgroupId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            if (data) {
                render data as JSON;
            } else {
                render "[]";
            }
        }
    }

    def generateGroupOutstandingReport() {
        if (params?.id) {
            def data = masterReportService.getGroupOutstandingByCompanyAndgroupId(session["company"].id, params?.id as Long, params?.fromDate, params?.toDate);
            if (data) {
                render data as JSON;
            } else {
                render "[]";
            }
        }
    }

    // find All Payables Outstanding Report
    def generatePayablesOutstandingReport() {
//        def data = masterReportService.getSalesRegisterByCompanyAndDateBetween(session["company"].id, params.property);
        def data = masterReportService.getPayablesOutstandingReportByCompanyAndProperty(session["company"].id, params.property, params.fromDate, params.toDate, params.reportName);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    // Find All Statistics Report
    def generateStatisticsReport() {
        def data = masterReportService.getStatisticsReport(session["company"].id);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    def generateCashBankSummaryReport() {
        ArrayList<String> arr = params.array.split(",");
        if (arr) {
            def data = masterReportService.findAllCashBankSummaryReport(arr, session["company"], params.fromDate, params.toDate);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    def generateCashBankSummaryReportByGroupId() {
        if (params?.id) {
            def data = masterReportService.findAllCashBankSummaryReportByGroupId(params?.id as Long, session["company"], params.fromDate, params.toDate);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    def generateTrailBalanceReport() {
        ArrayList<String> arr = params.array.split(",");
        if (arr) {
            def data = masterReportService.findAllTrailBalanceReport(arr, session["company"], params.fromDate, params.toDate);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    def generateProfitAndLossReport() {
        ArrayList<String> arr = params.array.split(",");
        if (arr) {
            def data = masterReportService.findAllProfitAndLossReport(arr, session["company"], params.fromDate, params.toDate);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    def generateBalanceSheetReport() {
        ArrayList<String> arr = params.array.split(",");
        if (arr) {
            def data = masterReportService.findAllBalanceSheetReport(arr, session["company"], params.fromDate, params.toDate);
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    // List Of Accounts Report
    def generateListOfAccounts() {
        def data = masterReportService.getListOfAccounts(session["company"].id);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    // Find Day Book Report
    def generateDayBook() {
        def data = masterReportService.getDayBookReport(session["company"].id, params.fromDate, params.toDate);
        if (data) {
            render data as JSON;
        } else {
            render "[]";
        }
    }

    //===================================================== start ng - grid ===================================================
    def gridData() {
//        int count1 = 0
//        String compId = session['company'].id;
//        params.extraParams = [
//                company: compId
//        ]
//
//        def searchResult = systemService.getGridData(controllerClass, params)
////        def results = searchResult.result.collect {
////            if(it?.voucherType?.id){[
////                    id         : it.id,
////                    voucherType: it?.voucherType,
////                    voucherNo  : it?.voucherNo,
////                    date       : it?.date
////            ]}
////        }
//
//        def data = searchResult.result ;
//        def results = [];
//        
//        if(data){
//            data.each {d ->
//                if(d?.voucherNo){
//                    count1++;
//                    results.push([
//                            id         : d.id,
//                            voucherType: d?.voucherType,
//                            voucherNo  : d?.voucherNo,
//                            date       : d?.date
//                    ])
//
//                }
//            }
//            
//        }
//        def respo = [data: results, total: count1];
//        render respo as JSON;
        def page = Integer.parseInt(params.page), pageSize = Integer.parseInt(params.pageSize);
        def search = JSON.parse(params?.search ?: '[]');
        def hqxl = new ArrayList<String>();
        if (search.size() > 0) {
            search.each { p ->

                if (p) {
                    if (p?.searchText) {
                        if (p?.searchText?.contains("*")) {
                            hqxl.add('u.' + p.dataNM.trim() + " like '" + p.searchText.replace("*", "%") + "'");
                        } else if (p?.searchText?.contains("<")) {
                            hqxl.add('u.' + p.dataNM.trim() + " < '" + p.searchText.replace("<", "") + "'");
                        } else if (p?.searchText?.contains(">")) {
                            hqxl.add('u.' + p.dataNM.trim() + " > '" + p.searchText.replace(">", "") + "'");
                        } else {
                            hqxl.add('u.' + p.dataNM.trim() + " like '" + p.searchText + "%'");
                        }
                    }
                }
            }
        }
        def hqxlTxt = '';
        String compId = session['company'].id;
        String year = session['financialYear'];
        if (hqxl.size() > 0) {
            hqxlTxt = 'from Voucher as u where u.company = ' + compId + " and u.voucherNo IS NOT NULL " + " and " + hqxl.join(' and ') + " order by u.id desc";
        } else {
            hqxlTxt = "from Voucher as u where u.company = " + compId + " and u.voucherNo IS NOT NULL " + " order by u.id desc";
        }
        def results = Voucher.findAll(hqxlTxt, [max: pageSize, offset: (page - 1) * pageSize]).collect {
            [
                    id         : it.id,
                    voucherType: it?.voucherType,
                    voucherNo  : it?.voucherNo,
                    date       : it?.date
            ]
        };
        def respo = [data: results, total: Voucher.findAll(hqxlTxt).size()];
        render respo as JSON
    }

    def gridDef() {
        def columns = [];
//        def d = ColumnsSetting.findByScreenAndUser(session['activeScreen'] as Screen, User.findById(session['activeUser'].id));
//        if (d) {
//            columns = [];
//            d.child.sort { it.columnsIndex }.each { col ->
//                columns.push([
//                        displayName          : col?.displayName ?: '',
//                        "col.index"          : col?.columnsIndex,
//                        visible              : col?.visible ?: false,     //boolean
//                        sortable             : col?.sortable ?: false,      //boolean
//                        resizable            : col?.resizable ?: false,     //boolean
//                        groupable            : col?.groupable ?: false,   //boolean
//                        pinned               : col?.pinned ?: false, // boolean
//                        enableCellEdit       : col?.enableCellEdit ?: false,  //boolean
//                        cellEditableCondition: col?.cellEditableCondition ?: false,//boolean
//                        editableCellTemplate : col?.editableCellTemplate ?: false,//boolean
//                        width                : col.width,                // numeric
//                        minWidth             : col.minWidth,         // numeric
//                        maxWidth             : col.maxWidth,        // numeric
//                        columnsIndex         : col.columnsIndex,// numeric
//                        sortFn               : col?.sortFn ?: '',     //String
//                        cellTemplate         : col?.cellTemplate ?: '',   //String
//                        cellClass            : col?.cellClass ?: '',       //String
//                        headerClass          : col?.headerClass ?: '',//String
//                        headerCellTemplate   : col?.headerCellTemplate ?: '',  //String
//                        cellFilter           : col?.cellFilter ?: '',         //String
//                        aggLabelFilter       : col?.aggLabelFilter ?: '',  //String
//                        field                : col?.field ?: '' //String
//                ])
//            }
//        } else {

        def ngClass = "'colt' + col.index";
        // {{showSearch?searchHeight:' +"searchHeight0"+'}}
//        def elemnt = ' <input  ui-event="{ blur : \'showMSG($event,col)\' }" class="searchText" ng-show="showSearch" type="text" ng-model="col.searchText" placeholder="{{col.displayName}}"> </input>';
        def elemnt = ' <input  ng-change="showMSG($event,col)" class="searchText" ng-show="showSearch" type="text" ng-model="col.searchText" placeholder="{{col.displayName}}"> </input>';
        def elemntDate = ' <input ng-change="showMSG($event,col)" class="searchText" ng-show="showSearch" type="date" ng-model="col.searchText" placeholder="{{col.displayName}}"> </input>';

        def headerT1 = '<div class="ngHeaderSortColumn {{col.headerClass}} searchHeight " ng-style="{cursor: col.cursor}" ng-class="{ ngSorted: !noSortVisible }">' +
                //'<div ng-click="col.sort($event)" ng-class="' + ngClass +'" class="ngHeaderText">{{col.displayName}}</div>'+
                '<div ng-click="col.sort($event)"  ng-class="' + ngClass + '" class="ngHeaderText"> <span ng-show="!showSearch"> {{col.displayName}} </span> ';
        def headerT2 = '</div>' +
                '<div class="ngSortButtonDown" ng-click="col.sort($event)" ng-show="col.showSortButtonDown()"></div>' +
                '<div class="ngSortButtonUp" ng-click="col.sort($event)" ng-show="col.showSortButtonUp()"></div>' +
                '<div class="ngSortPriority">{{col.sortPriority}}</div>' +
                '<div ng-class="{ ngPinnedIcon: col.pinned, ngUnPinnedIcon: !col.pinned }" ng-click="togglePin(col)" ng-show=\"(col.pinnable && !showSearch)\"></div>' +
                '</div>' +
                '<div ng-show="col.resizable" class="ngHeaderGrip" ng-click="col.gripClick($event)" ng-mousedown="col.gripOnMouseDown($event)"></div>'


//
//        def editDeleteButton = '<form method="post" action="/' + grailsApplication.config.projectName + '/' + controllerName + '/list?scrid="  style="padding: 5px; page-break-before: avoid; page-break-after: avoid; margin: 0px" >' +
//                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
//                '<button type="submit" id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
///*
//                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
//*/
//                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a></form>'

        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#voucher/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#voucher/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

//                    '<webcam:webcamAnchor/>'+
//            '<a id="webCamDiv" href=\"/'+grailsApplication.config.erpName+'/static/plugins/web-snap-0.1/swf/WebCam.swf\"><img src=\"/'+ grailsApplication.config.erpName+'/images/webcam_icon.jpg\" border="0" width="40" height="40"/></a>';

        def linkCellTemplate = '<div class="ngCellText" ng-class="col.colIndex()">' +
                '  <a href="http://{{row.getProperty(col.field)}}" ng-bind="row.getProperty(col.field)" target="_blank"></a>' +
                '</div>';
        def mailCellTemplate = '<div class="ngCellText" ng-class="col.colIndex()">' +
                '  <a href="mailto:{{row.getProperty(col.field)}}" ng-bind="row.getProperty(col.field)"></a>' +
                '</div>';
        def checkboxHeader = '<input type="checkbox" ng-model="bool" ng-change="checkAll(bool)" style="width:18px;margin-top:5px;margin-left: 5px;"/><span class="lbl" style="padding-left: 5px;padding-right: 5px;"></span>';
        def checkboxCell = '<input type="checkbox" ng-model="row.entity.sendMailBool"  style="width:18px;margin-top:5px;margin-left: 5px;"/><span class="lbl" style="padding-left: 5px;padding-right: 5px;"></span>';
        def actionName = '<span>Action </span>';

        columns = [
                [field: '', displayName: 'Action', width: '70', cellTemplate: editDeleteButton, pinned: true, headerCellTemplate: actionName],
                [field: 'voucherType.name', displayName: 'Voucher Type', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
                [field: 'voucherNo', displayName: 'Voucher No', width: "200", headerCellTemplate: headerT1 + elemnt + headerT2],
                [field: 'date', displayName: 'Date', width: "200", headerCellTemplate: headerT1 + elemntDate + headerT2, cellFilter: 'date:\'dd-MM-yyyy\'']
//                    [field: 'accountGroup.accountName', displayName: 'Account Group', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
        ];
//        }
        render columns as JSON;
    }

//    def menuDef() {
//        if (params.columnsField) {
//            def columns = JSON.parse((params.columnsField).replaceAll("amp", "&"));
//
//
//            def columnsInstance = ColumnsSetting.findByScreenAndUser(session['activeScreen'] as Screen, User.findById(session['activeUser'].id));
//            if (columnsInstance) {
//                ColumnsSetting.executeUpdate("delete ColumnsSettingChild c where c.parent.id=:id", [id: columnsInstance.id]);
//                columnsInstance.save();
//                columns.each { col ->
//                    columnsInstance.addToChild(ColumnsSettingChild.saveData(col))
//                }
//                columnsInstance.save();
//
//            } else {
//                ColumnsSetting columnsInstance1 = new ColumnsSetting();
//                columnsInstance1.screen = session['activeScreen'] as Screen;
//                columnsInstance1.user = User.findById(session['activeUser'].id);
//                columns.each { col ->
//                    columnsInstance1.addToChild(ColumnsSettingChild.saveData(col))
//                }
//                columnsInstance1.save();
//            }
//        }
//
//        render "Table Columns save";
//    }

//===================================================== end ng - grid ===================================================

}
