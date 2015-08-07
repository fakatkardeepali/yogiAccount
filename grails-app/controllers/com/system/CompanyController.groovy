package com.system

import com.annotation.ParentScreen
import com.common.AccountFeature
import com.common.StatutoryInfo
import com.common.TaxSetting
import com.master.AccountGroup
import com.master.AccountLedger
import com.master.MasterService
import com.master.VoucherType
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND

@ParentScreen(name = "Utilities", fullName = "Company", sortList = 1, link = "/company/create")
@Transactional(readOnly = true)
class CompanyController {

    MasterService masterService
    SystemService systemService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Company.list(params), model: [companyInstanceCount: Company.count()]
    }

    def show(Company companyInstance) {
        respond companyInstance
    }

    def create() {
        respond new Company(params)
    }

    @Transactional
    def save(Company companyInstance) {
        bindData(companyInstance, params, [exclude: ['booksBeginigFrom', 'financialFrom']])
        companyInstance.booksBeginigFrom = params.booksBeginigFrom ? Date.parse("yyyy-MM-dd", params.booksBeginigFrom) : new Date();
        companyInstance.financialFrom = params.financialFrom ? Date.parse("yyyy-MM-dd", params.financialFrom) : new Date();
        companyInstance.lastUpdatedBy = session['activeUser'].user;
        companyInstance.isManual = true;
        if (!companyInstance.organization) {
            companyInstance.organization = session['activeUser'].organization;
        }
        if (companyInstance == null) {
            notFound()
            return
        }
        // create two ledger of this company by default
        // 1. cash
        // 1. profit and loss Acc
        // DataFeedService.dataFeedLedger()

        // create 33 group by default for this company
        // DataFeedService.dataFeedGroup()

        if (companyInstance.hasErrors()) {
            render companyInstance.errors;
        } else {
            companyInstance.save flush: true
            if (!session['company']) {
                session['verify'] = "single";
                session['company'] = companyInstance;
                session['activeUser'].companyId = [companyInstance];
            }
            render 1
        }
    }

    def edit(Company companyInstance) {
        def companyList = systemService.getCompanyById(companyInstance.id);
        if (companyList) {
            render companyList as JSON;
        } else {
            render "[]";
        }
    }

    @Transactional
    def update(Company companyInstance) {


        if (companyInstance == null) {
            notFound()
            return
        }

        bindData(companyInstance, params, [exclude: ['booksBeginigFrom', 'financialFrom']])
        companyInstance.booksBeginigFrom = params.booksBeginigFrom ? Date.parse("yyyy-MM-dd", params.booksBeginigFrom) : "";
        companyInstance.financialFrom = params.financialFrom ? Date.parse("yyyy-MM-dd", params.financialFrom) : "";

        if (companyInstance.hasErrors()) {
            render companyInstance.errors;
//            respond companyInstance.errors, view: 'create'
//            return
        } else {
            companyInstance.save flush: true
            render 1
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'Company.label', default: 'Company'), companyInstance.id])
//                redirect companyInstance
//            }
//            '*' { respond companyInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete(Long id) {
        def companyInstance = Company.findById(id);
        if (companyInstance == null) {
            notFound()
            return
        }
        AccountGroup.findAllByCompany(companyInstance,[sort:'id',order: 'desc']).each { d-> d.delete(flush: true)}
        TaxSetting.findAllByCompany(companyInstance,[sort:'id',order: 'desc']).each { d-> d.delete(flush: true)}
        StatutoryInfo.findByCompany(companyInstance).delete(flush: true)
        AccountFeature.findByCompany(companyInstance).delete(flush: true)
        AccountLedger.findAllByCompany(companyInstance,[sort:'id',order: 'desc']).each { d-> d.delete(flush: true)}
        VoucherType.findAllByCompany(companyInstance,[sort:'id',order: 'desc']).each { d-> d.delete(flush: true)}

        if (companyInstance.delete(flush: true)) {
            render ""
        } else {
            render true;
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AccountGroup.label', default: 'AccountGroup'), accountGroupInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
    }
    /*def delete(Company companyInstance) {

     if (companyInstance == null) {
         notFound()
         return
     }

     if (companyInstance.delete(flush: true)) {
         render 1
     } else {
         render companyInstance.errors;
     }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Company.label', default: 'Company'), companyInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
 }*/

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    //bhupendra 05-02-2015
    //getting a list of all country in the database
    def countryList() {
        def getList = systemService.getCountryByList();
        if (getList) {
            render getList as JSON;
        } else {
            render "[]" as JSON;
        }
    }

    def findStateByCountry() {
        if (params?.id) {
            def getList = systemService.getStateAllByCountry(params?.id as Long);
            render getList as JSON;
        } else {
            render "[]";
        }

    }

//===================================================== start ng - grid ===================================================
    def gridData() {

        String compId = session['company'].id;
        def extraPrarams = [
                isDisplay: true
        ]
        params.isDisplay = true;
        def searchResult = systemService.getGridData(controllerClass, params)
        def results = searchResult.result.collect {
            [
                    id  : it.id,
                    org : it?.organization?.name ?: "",
                    name: it?.name ?: ""
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
//        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
//                '<button type="submit" id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
//                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
//                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'
        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#company/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#company/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
                [field: 'org', displayName: 'Organization', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
                [field: 'name', displayName: 'Company Name', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2]
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

//    ========================================== Setting Work =========================================================

    def findAccountFeatureSetting() {
        Company company = systemService.getCompanyObjectById(session['company'].id as Long)
        def data = systemService.getAccountSettingByCompany(company)
        if (data) {
            def result = [
                    id                                        : data.id,
                    incomeExpenseStatementInsteadOfPL         : data.incomeExpenseStatementInsteadOfPL,
                    allowMultipleCurrency                     : data.allowMultipleCurrency,
                    useDebitCreditNotes                       : data.useDebitCreditNotes,
                    maintainBillWiseDetails                   : data.maintainBillWiseDetails,
                    enableChequePrinting                      : data.enableChequePrinting,
                    forNontradingAccountAlso                  : data.forNontradingAccountAlso,
                    usedAdvanceParameters                     : data.usedAdvanceParameters,
                    activeIntersetCalculation                 : data.activeIntersetCalculation,
                    useNameInReport                           : data.useNameInReport,
                    useAliasNameInReport                      : data.useAliasNameInReport,
                    useAliasNameAlongWithNameinForms          : data.useAliasNameAlongWithNameinForms,
                    dateFormat                                : data.dateFormat,
                    decimalCharacterToUseInAmount             : data.decimalCharacterToUseInAmount,
                    usePerfixSymbolForDebitAmount             : data.usePerfixSymbolForDebitAmount,
                    usePostfixSymbolForDebitAmount            : data.usePostfixSymbolForDebitAmount,
                    usePerfixSymbolFoCreditAmount             : data.usePerfixSymbolFoCreditAmount,
                    usePostfixSymbolForCreditAmount           : data.usePostfixSymbolForCreditAmount,
                    useAddressForLedgerAccounts               : data.useAddressForLedgerAccounts,
                    useContactsDetailsForLedgerAccounts       : data.useContactsDetailsForLedgerAccounts,
                    useSingleEntryModeForPurchase             : data.useSingleEntryModeForPurchase,
                    useSingleEntryModeForSale                 : data.useSingleEntryModeForSale,
                    useSingleEntryModeForPayment              : data.useSingleEntryModeForPayment,
                    useSingleEntryModeForRecepit              : data.useSingleEntryModeForRecepit,
                    useSingleEntryModeForContra               : data.useSingleEntryModeForContra,
                    useSingleEntryModeForDebit                : data.useSingleEntryModeForDebit,
                    useSingleEntryModeForCredit               : data.useSingleEntryModeForCredit,
                    usePaymentRecepitAsContra                 : data.usePaymentRecepitAsContra,
                    useCrDrInsteadOfToBy                      : data.useCrDrInsteadOfToBy,
                    warnOnNegativeCashBalance                 : data.warnOnNegativeCashBalance,
                    allowCashAccountsInJournals               : data.allowCashAccountsInJournals,
                    showbillwiseDetails                       : data.showbillwiseDetails,
                    allowIncomeAccountsInSaleVouchers         : data.allowIncomeAccountsInSaleVouchers,
                    showLedgerBalance                         : data.showLedgerBalance,
                    showBalanceAsOnVoucherDate                : data.showBalanceAsOnVoucherDate,
                    allowOthersDetails                        : data.allowOthersDetails,
                    allowExpensesFixedAssetsInPurchaseAccounts: data.allowExpensesFixedAssetsInPurchaseAccounts,

            ]
            render result as JSON
        } else {
            render '[]'
        }
    }

    @Transactional
    def updateAccountFeature(AccountFeature accountInstance) {

        if (accountInstance == null) {
            notFound()
            return
        }

//        bindData(accountInstance,params,[exclude:['regularVatApplicableFrom','dateOfReg']])

        if (accountInstance.hasErrors()) {
            render accountInstance.errors;

        } else {
            accountInstance.save flush: true
            render 1
        }
    }


    def findStatutorySetting() {
        Company company = systemService.getCompanyObjectById(session['company'].id as Long)
        def data = systemService.getStatutorySettingByCompany(company)
        if (data) {
            def result = [
                    id                                         : data?.id ?: "",
                    allowSelectOfVatTaxDuringEntry             : data.allowSelectOfVatTaxDuringEntry,
                    activateE1OrE2TransactionVat               : data.activateE1OrE2TransactionVat,
                    allowAlteraionOfTdsRateForLowerDeductions  : data.allowAlteraionOfTdsRateForLowerDeductions,
                    allowAlteraionOfTdsNatureOfPaymentInExpense: data.allowAlteraionOfTdsNatureOfPaymentInExpense,
                    enableValueAddedTax                        : data.enableValueAddedTax,
                    vatAlterDetails                            : data.vatAlterDetails,
                    state                                      : data.state?.id ?: "",
                    typeOfDealer                               : data.typeOfDealer?.id ?: "",
                    regularVatApplicableFrom                   : data.regularVatApplicableFrom ?: "",
                    tdsEnableTaxDeductedAtSource               : data.tdsEnableTaxDeductedAtSource,
                    tdsAlterDetails                            : data.tdsAlterDetails,
                    tdsTaxAssessmentNo                         : data.tdsTaxAssessmentNo ?: "",
                    tdsIncomeTaxCircleward                     : data.tdsIncomeTaxCircleward ?: "",
                    tdsDeductorCollectorType                   : data.tdsDeductorCollectorType?.id ?: "",
                    tdsResponsiblePerson                       : data.tdsResponsiblePerson ?: "",
                    tdsDesignation                             : data.tdsDesignation ?: "",
                    enableServiceTax                           : data.enableServiceTax,
                    serviceTaxAlterDetails                     : data.serviceTaxAlterDetails,
                    serviceTaxRegNo                            : data.serviceTaxRegNo ?: "",
                    dateOfReg                                  : data.dateOfReg ?: "",
                    assesseeCode                               : data.assesseeCode ?: "",
                    premisesCodeNo                             : data.premisesCodeNo ?: "",
                    typeOfOrganization                         : data.typeOfOrganization?.id ?: "",
                    isLargeTaxPayer                            : data.isLargeTaxPayer,
                    largeTaxPayerUnit                          : data.largeTaxPayerUnit ?: "",
                    divisionCode                               : data.divisionCode ?: "",
                    divisionName                               : data.divisionName ?: "",
                    rangeCode                                  : data.rangeCode ?: "",
                    rangeName                                  : data.rangeName ?: "",
                    commisionerateCode                         : data.commisionerateCode ?: "",
                    commisionerateName                         : data.commisionerateName ?: "",
                    tcsEnableTaxCollectedAtSource              : data.tcsEnableTaxCollectedAtSource,
                    tcsAlterDetails                            : data.tcsAlterDetails,
                    tcsTaxAssessmentNo                         : data.tcsTaxAssessmentNo ?: "",
                    tcsIncomeTaxCircleward                     : data.tcsIncomeTaxCircleward ?: "",
                    tcsDeductorCollectorType                   : data.tcsDeductorCollectorType?.id ?: "",
                    tcsResponsiblePerson                       : data.tcsResponsiblePerson ?: "",
                    tcsDesignation                             : data.tcsDesignation ?: "",
                    vatTinComposition                          : data.vatTinComposition ?: "",
                    vatTinRegular                              : data.vatTinRegular ?: "",
                    localSalesTaxNumber                        : data.localSalesTaxNumber ?: "",
                    interStateSalesTaxNumber                   : data.interStateSalesTaxNumber ?: "",
                    panTaxNo                                   : data.panTaxNo ?: "",
            ]
            render result as JSON
        } else {
            render '[]'
        }
    }

    @Transactional
    def updateStatutory(StatutoryInfo statutoryInstance) {

        if (statutoryInstance == null) {
            notFound()
            return
        }

        bindData(statutoryInstance, params, [exclude: ['regularVatApplicableFrom', 'dateOfReg']])

        if (statutoryInstance.hasErrors()) {
            render statutoryInstance.errors;

        } else {
            statutoryInstance.save flush: true
            Company company = systemService.getCompanyObjectById(session['company'].id as Long)
            masterService.updateTaxSettingObjectByStatutoryAndCompany(statutoryInstance, company)
            render 1
        }
    }

    def findAllOptions() {
        def data = systemService.getAllOptions();
        if (data) {
            render data as JSON;
        } else {
            render '[]'
        }
    }

    def findAllState() {
        def data = systemService.getAllState()
        if (data) {
            render data as JSON
        } else {
            render '[]'
        }
    }

}
