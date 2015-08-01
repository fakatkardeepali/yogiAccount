package com.master

import com.annotation.ParentScreen
import com.system.Company
import com.system.SystemService
import com.system.User
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@ParentScreen(name = "Master", fullName = "Group", sortList = 1, link = "/group/create")

@Transactional(readOnly = true)
class AccountGroupController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MasterService masterService
    SystemService systemService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccountGroup.list(params), model: [accountGroupInstanceCount: AccountGroup.count()]
    }

    def show(AccountGroup accountGroupInstance) {
        respond accountGroupInstance
    }

    def create() {
        respond new AccountGroup(params)
    }

    @Transactional
    def save(AccountGroup accountGroupInstance) {
        bindData(accountGroupInstance, params, [exclude: ['lastUpdatedBy', 'company']])
        accountGroupInstance.lastUpdatedBy = (User) session['activeUser'].user;
        accountGroupInstance.company = (Company) session['company'];
        accountGroupInstance.isDisplay = true

        if (accountGroupInstance == null) {
            notFound()
            return
        }

        if (accountGroupInstance.hasErrors()) {
            render accountGroupInstance.errors
//            return
        } else {
            accountGroupInstance.save(flush: true)
            render 1
        }
    }

    def edit(AccountGroup accountGroupInstance) {
        def accountGroupData = masterService.getAccountGroupObjectById(accountGroupInstance.id as Long);
        if (accountGroupData) {
            render accountGroupData as JSON;
        } else {
            render "[]";
        }
    }

    @Transactional
    def update(AccountGroup accountGroupInstance) {
        if (accountGroupInstance == null) {
            notFound()
            return
        }

        if (accountGroupInstance.hasErrors()) {
            render accountGroupInstance.errors;
//            respond companyInstance.errors, view: 'create'
//            return
        } else {
            accountGroupInstance.save flush: true
            render 1
        }
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'AccountGroup.label', default: 'AccountGroup'), accountGroupInstance.id])
//                redirect accountGroupInstance
//            }
//            '*' { respond accountGroupInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete(Long id) {
        def accountGroupInstance = AccountGroup.findById(id);
        if (accountGroupInstance == null) {
            notFound()
            return
        }

        if (accountGroupInstance.delete(flush: true)) {
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

    protected void notFound() {
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountGroup.label', default: 'AccountGroup'), params.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NOT_FOUND }
//        }
    }


    def findNonPrimaryGroup() {
        Company company = systemService.getCompanyObjectById(session['company'].id as Long);
        def data = masterService.findAllAccountGroupByCompany(company).sort { it.name };
        if (data) {
            render data as JSON;
        } else {
            render '[]';
        }
    }

    def findPrimaryGroup() {
        Company company = systemService.getCompanyObjectById(session['company'].id as Long);
        def data = masterService.findAllGroupPrimaryByCompany(company)
        if (data) {
            render data as JSON;
        } else {
            render '[]';
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
                isDisplay: true,
                company: compId,
                orderBy: "id desc",
        ]
        def searchResult = systemService.getGridData(controllerClass, params)
        def results = searchResult.result.collect {
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
        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#group/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#group/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
