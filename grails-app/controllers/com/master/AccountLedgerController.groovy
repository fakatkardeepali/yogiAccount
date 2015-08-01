package com.master

import com.annotation.ParentScreen
import com.common.AccountFlag
import com.system.Company
import com.system.SystemService
import com.transaction.PartyAccount
import grails.converters.JSON
import grails.transaction.Transactional

@ParentScreen(name = "Master", fullName = "Ledger", sortList = 1, link = "/ledger/create")
@Transactional(readOnly = true)
class AccountLedgerController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "POST"]

    MasterService masterService
    SystemService systemService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccountLedger.list(params), model: [accountLedgerInstanceCount: AccountLedger.count()]
    }

    def show(AccountLedger accountLedgerInstance) {
        respond accountLedgerInstance
    }

    def create() {
        respond new AccountLedger(params)
    }

    @Transactional
    def save(AccountLedger accountLedgerInstance) {
        if (accountLedgerInstance == null) {
            notFound()
            return
        }

        def childs = JSON.parse(params.interestPara)
        def billArray = JSON.parse(params.billJson)

        bindData(accountLedgerInstance, params, [exclude: ["company", "lastUpdatedBy", "reconciliationDate"]])

        accountLedgerInstance.company = systemService.getCompanyObjectById(session['company'].id as Long)
        accountLedgerInstance.lastUpdatedBy = systemService.getUserById(session['activeUser'].user.id as Long)
//        accountLedgerInstance.status = 'dr'

        if (Boolean.parseBoolean(params.showReconciliationDate)) {
            accountLedgerInstance.reconciliationDate = Date.parse("yyyy-MM-dd", params.reconciliationDate);
        }
        if (childs.size() > 0) {
            childs.each { c ->
                accountLedgerInstance.addToInterestParameter(InterestParameters.saveChild(c, session['company'].id as Long))
            }
        }



        if (accountLedgerInstance.hasErrors()) {
            respond accountLedgerInstance.errors
//            return
        } else {
            accountLedgerInstance.save flush: true
            if (accountLedgerInstance.maintainBill) {
                if (billArray.size() > 0) {
                    List<PartyAccount> partyArray = new ArrayList<PartyAccount>()
                    billArray.each { c ->
                        partyArray.add(new PartyAccount(billNo: c.billNo, company: accountLedgerInstance.company, partyName: accountLedgerInstance, crDays: c.crDays as BigDecimal, amount: c.amount as BigDecimal, amountStatus: c.amountStatus, lastUpdatedBy: accountLedgerInstance.lastUpdatedBy, billDate: c?.date ? Date.parse("yyyy-MM-dd", c.date) : null, typeOfRef: AccountFlag.findByName(c.billRef)))
                    }
                    partyArray.add(new PartyAccount(company: accountLedgerInstance.company, partyName: accountLedgerInstance, lastUpdatedBy: accountLedgerInstance.lastUpdatedBy, amount: params.onAccountAmount as BigDecimal, amountStatus: params.onAccountAmountStatus, typeOfRef: AccountFlag.findByName("On Account")))
                    PartyAccount.saveAll(partyArray)
                }
            } else {
                new PartyAccount(billNo: "On Account", company: accountLedgerInstance.company,
                        partyName: accountLedgerInstance, crDays: 0,
                        amount: accountLedgerInstance.openingBalance as BigDecimal,
                        amountStatus: accountLedgerInstance.status,
                        lastUpdatedBy: accountLedgerInstance.lastUpdatedBy,
                        billDate: accountLedgerInstance?.lastUpdated ? accountLedgerInstance?.lastUpdated : null,
                        typeOfRef: AccountFlag.findByName("On Account")).save();
            }

            render 1
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'accountLedger.label', default: 'AccountLedger'), accountLedgerInstance.id])
//                redirect accountLedgerInstance
//            }
//            '*' { respond accountLedgerInstance, [status: CREATED] }
//        }
    }

    def edit(AccountLedger accountLedgerInstance) {
        def child = []
        def child1 = []
        def accountLedgerData = masterService.getAccountLedgerObjectById(accountLedgerInstance.id as Long);
        if (accountLedgerData) {
            if (accountLedgerData.interestParameter.size() > 0) {
                accountLedgerData.interestParameter.each { c ->
                    child.push([
                            rate              : c.rate,
                            rateper           : c.rateper ?: "",
                            applicableDateFrom: c?.applicableDateFrom?.format("yyyy-MM-dd") ?: "",
                            applicableDateTo  : c?.applicableDateTo?.format("yyyy-MM-dd") ?: "",
                    ])
                }
            }
            def billData = masterService.getPartyAccountByLedgerAndVoucherNull(accountLedgerInstance);
            if (billData.size() > 0) {
                billData.each { b ->
                    child1.push([
                            date        : b?.billDate ? b.billDate?.format("yyyy-MM-dd") : "",
                            billNo      : b?.billNo ?: "",
                            crDays      : b.crDays,
                            billRef     : b.typeOfRef.name,
                            amount      : b.amount,
                            amountStatus: b.amountStatus
                    ])
                }
            }


            def result = [
                    id                           : accountLedgerData.id,
                    name                         : accountLedgerData.name,
                    alias                        : accountLedgerData?.alias ?: "",
                    underGroup                   : accountLedgerData?.underGroup?.id ?: "",
                    note                         : accountLedgerData?.note ?: "",
                    reconciliationDate           : accountLedgerData?.reconciliationDate?.format("yyyy-MM-dd") ?: "",
                    maintainBill                 : accountLedgerData.maintainBill,
                    creditDays                   : accountLedgerData.creditDays,
                    accountNo                    : accountLedgerData?.accountNo ?: "",
                    accountName                  : accountLedgerData?.accountName ?: "",
                    branchName                   : accountLedgerData?.branchName ?: "",
                    bsrCode                      : accountLedgerData?.bsrCode ?: "",
                    ifscCode                     : accountLedgerData?.ifscCode ?: "",
                    mailingName                  : accountLedgerData?.mailingName ?: "",
                    address                      : accountLedgerData?.address ?: "",
                    state                        : accountLedgerData?.state?.id ?: "",
                    pinCode                      : accountLedgerData?.pinCode ?: "",
                    contactPerson                : accountLedgerData?.contactPerson ?: "",
                    telephoneNo                  : accountLedgerData?.telephoneNo ?: "",
                    mobileNo                     : accountLedgerData?.mobileNo ?: "",
                    faxNo                        : accountLedgerData?.faxNo ?: "",
                    email                        : accountLedgerData?.email ?: "",
                    provideBankDetails           : accountLedgerData.provideBankDetails,
                    panNo                        : accountLedgerData?.panNo ?: "",
                    salesTaxNo                   : accountLedgerData?.salesTaxNo ?: "",
                    cstNo                        : accountLedgerData?.cstNo ?: "",
                    typeOfDuty                   : accountLedgerData?.typeOfDuty?.id ?: "",
                    dutyHead                     : accountLedgerData?.dutyHead?.id ?: "",
                    percentageOfCalculation      : accountLedgerData.percentageOfCalculation,
                    methodOfCalculation          : accountLedgerData?.methodOfCalculation?.id ?: "",
                    roundMethod                  : accountLedgerData?.roundMethod?.id ?: "",
                    openingBalance               : accountLedgerData.openingBalance,
                    status                       : accountLedgerData.status,
                    activeInterestCalculation    : accountLedgerData.activeInterestCalculation,
                    usePrimaryGroup              : accountLedgerData.usePrimaryGroup ?: false,
// statutory form members
// statutory tds
                    isTdsDeductee                : accountLedgerData.isTdsDeductee,
                    deducteeType                 : accountLedgerData?.deducteeType?.id ?: "",
// statutory service
                    isServiceTax                 : accountLedgerData.isServiceTax,
                    category                     : accountLedgerData?.category?.id ?: "",
                    notificationNo               : accountLedgerData?.notificationNo ?: "",
                    isAbatement                  : accountLedgerData.isAbatement,
                    percentage                   : accountLedgerData.percentage,
                    // statutory vat
                    isVat                        : accountLedgerData.isVat,
                    eccNo                        : accountLedgerData.eccNo,
                    taxClass                     : accountLedgerData?.taxClass?.id ?: "",
// statutory tcs
                    isTcsApplicable              : accountLedgerData.isTcsApplicable,
                    buyerLessee                  : accountLedgerData?.buyerLessee?.id ?: "",
                    noCollectionApplicable       : accountLedgerData.noCollectionApplicable,
                    sectionNumber                : accountLedgerData?.sectionNumber ?: "",
                    tcsLowerRate                 : accountLedgerData.tcsLowerRate,
                    ignoreSurchargeExceptionLimit: accountLedgerData.ignoreSurchargeExceptionLimit,
                    childs                       : child,
                    billData                     : child1
            ]

            render result as JSON;
        } else {
            render "[]";
        }
    }

    @Transactional
    def update(AccountLedger accountLedgerInstance) {
        if (accountLedgerInstance == null) {
            notFound()
            return
        }

        AccountLedger.executeUpdate("delete from InterestParameters as i where i.accountLedger.id=:id", [id: accountLedgerInstance.id])

        AccountLedger.executeUpdate("delete from PartyAccount as p where p.partyName.id=:id and p.voucher.id = null", [id: accountLedgerInstance.id])

        if (params.interestPara) {
            def childs = JSON.parse(params.interestPara)

            if (childs.size() > 0) {
                childs.each { c ->
                    accountLedgerInstance.addToInterestParameter(InterestParameters.saveChild(c, session['company'].id as Long))
                }
            }
        }

        def billArray = []
        if (params.billJson) {
            billArray = JSON.parse(params.billJson)
        }

        bindData(accountLedgerInstance, params, [exclude: ["reconciliationDate"]])

        if (Boolean.parseBoolean(params.showReconciliationDate)) {
            accountLedgerInstance.reconciliationDate = Date.parse("yyyy-MM-dd", params.reconciliationDate);
        }

        if (accountLedgerInstance.hasErrors()) {
            respond accountLedgerInstance.errors
//            return
        } else {
            accountLedgerInstance.save flush: true
            if (accountLedgerInstance.maintainBill) {
                if (billArray.size() > 0) {
                    List<PartyAccount> partyArray = new ArrayList<PartyAccount>()
                    billArray.each { c ->
                        partyArray.add(new PartyAccount(billNo: c.billNo, company: accountLedgerInstance.company, partyName: accountLedgerInstance, crDays: c.crDays as BigDecimal, amount: c.amount as BigDecimal, amountStatus: c.amountStatus, lastUpdatedBy: accountLedgerInstance.lastUpdatedBy, billDate: c?.date ? Date.parse("yyyy-MM-dd", c.date) : null, typeOfRef: AccountFlag.findByName(c.billRef)))
                    }
                    partyArray.add(new PartyAccount(company: accountLedgerInstance.company, partyName: accountLedgerInstance, lastUpdatedBy: accountLedgerInstance.lastUpdatedBy, amount: params.onAccountAmount as BigDecimal, amountStatus: params.onAccountAmountStatus, typeOfRef: AccountFlag.findByName("On Account")))
                    PartyAccount.saveAll(partyArray)
                }
            }
//            else{
//                new PartyAccount(billNo: "On Account", company: accountLedgerInstance.company,
//                        partyName: accountLedgerInstance, crDays: 0,
//                        amount: accountLedgerInstance.openingBalance as BigDecimal,
//                        remainAmount: accountLedgerInstance.openingBalance as BigDecimal,
//                        amountStatus: accountLedgerInstance.status,
//                        lastUpdatedBy: accountLedgerInstance.lastUpdatedBy,
//                        billDate: accountLedgerInstance?.lastUpdated ? accountLedgerInstance?.lastUpdated : null,
//                        typeOfRef: AccountFlag.findByName("On Account")).save();
//            }

            render 1
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'AccountLedger.label', default: 'AccountLedger'), accountLedgerInstance.id])
//                redirect accountLedgerInstance
//            }
//            '*' { respond accountLedgerInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete() {
        AccountLedger accountLedgerInstance = AccountLedger.findById(params.id)
        if (accountLedgerInstance == null) {
            notFound()
//            return
        } else {
            try {
                accountLedgerInstance.delete flush: true

                render 1
            }catch (Exception e){
                e.printStackTrace();
                println "Got Exception"
            }
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AccountLedger.label', default: 'AccountLedger'), accountLedgerInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountLedger.label', default: 'AccountLedger'), params.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NOT_FOUND }
//        }
    }

    def findAllTaxType() {
        Company company = systemService.getCompanyObjectById(session['company'].id as Long)
        def data = masterService.getAllTaxByCompanyAndIsDisplayTrue(company)
        if (data) {
            render data as JSON;
        } else {
            render '[]'
        }
    }

    def findAllTaxChildByParentId() {
        if (params.flagId) {
            def data = masterService.getAllTaxChildByParent(params.flagId as Long)
            if (data) {
                render data as JSON
            } else {
                render '[]'
            }
        }
    }

    def findRoundMethod() {
        def data = masterService.getAllRoundMethodFromAccountFlag()
        if (data) {
            render data as JSON
        } else {
            render '[]'
        }
    }

    //===================================================== start ng - grid ===================================================
    def gridData() {

        String compId = session['company'].id;

        // this is extra params using for set property of domain class and get output from table
        // please use any of them ("groupBy" or "orderBy") as key for fetching data
        // don't use both ("groupBy" or "orderBy")
        // here we must set property name of domain as key eg. below
        params.extraParams = [
                company: compId,
                orderBy: "name"
        ]
//        AccountLedger.findAllByIfscCode().sort{it.name}
        def searchResult = systemService.getGridData(controllerClass, params)
        def results = searchResult.result.sort{it.name}.collect {
            [
                    id  : it.id,
                    name: it.name
            ]
        }
        def respo = [data: results, total: searchResult.count];
        render respo as JSON;
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
        ;

//
        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#ledger/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button type="submit" id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#ledger/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
                [field: 'name', displayName: 'Account Name', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
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
