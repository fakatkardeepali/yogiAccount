package com.master

import com.annotation.ParentScreen
import com.system.SystemService
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@ParentScreen(name = "Master", fullName = "VoucherType", sortList = 1, link = "/voucherType/create")
@Transactional(readOnly = true)
class VoucherTypeController {

    MasterService masterService
    SystemService systemService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond VoucherType.list(params), model: [voucherTypeInstanceCount: VoucherType.count()]
    }

    def show(VoucherType voucherTypeInstance) {
        respond voucherTypeInstance
    }

    def create() {
        respond new VoucherType(params)
    }

    @Transactional
    def save(VoucherType voucherTypeInstance) {
        bindData(voucherTypeInstance, params, [exclude: ['lastUpdatedBy', 'company']])

        voucherTypeInstance.lastUpdatedBy = systemService.getUserById(session['activeUser'].user.id as Long);
        voucherTypeInstance.company = systemService.getCompanyObjectById(session['company'].id as Long);

        if (params.child) {
            def child = JSON.parse(params.child);
            if (child) {
                child.each { c ->
                    voucherTypeInstance.addToParameters(Parameters.buildFromJSON(c, session['company']));
                }
            }
        }

        if (voucherTypeInstance == null) {
            notFound()
            return
        }

        if (voucherTypeInstance.hasErrors()) {
            render voucherTypeInstance.errors
        } else {
            voucherTypeInstance.save(flush: true)
            render true;
        }
    }

    def edit(VoucherType voucherTypeInstance) {
        def vType = masterService.findVoucherTypeById(voucherTypeInstance.id as Long);
        if (vType) {
            render vType as JSON;
        } else {
            render "[]";
        }
    }

    @Transactional
    def update(VoucherType voucherTypeInstance) {
        if (voucherTypeInstance == null) {
            notFound()
            return
        }

        VoucherType.executeUpdate("delete Parameters as r where r.voucherType.id=:id", [id: voucherTypeInstance.id]);
        voucherTypeInstance.save(flush: true);
        voucherTypeInstance.properties = params

        if (params.child) {
            def child = JSON.parse(params.child);
            if (child) {
                child.each { c ->
                    voucherTypeInstance.addToParameters(Parameters.buildFromJSON(c, session['company']));
                }
            }
        }

        if (voucherTypeInstance.hasErrors()) {
            render voucherTypeInstance.errors
        } else {
            voucherTypeInstance.save(flush: true)
            render true;
        }
    }

    @Transactional
    /*def delete(VoucherType voucherTypeInstance) {

        if (voucherTypeInstance == null) {
            notFound()
            return
        }

        if (voucherTypeInstance.delete(flush: true)) {
            render true;
        } else {
            render voucherTypeInstance.errors;
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'VoucherType.label', default: 'VoucherType'), voucherTypeInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }*/
    def delete(Long id) {
        def voucherTypeInstance = VoucherType.findById(id);
        if (voucherTypeInstance == null) {
            notFound()
            return
        }

        if (voucherTypeInstance.delete(flush: true)) {
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
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'voucherType.label', default: 'VoucherType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def findVoucherList() {
        def data = masterService.findAllVoucherTypeByCompanyId(session["company"].id as Long);
        if (data) {
            render data as JSON;
        } else {
            render "[]"
        }
    }

    //===================================================== start ng - grid ===================================================
    def gridData() {

        String compId = session['company'].id;
        params.extraParams = [
                company: compId,
                orderBy: "id desc",
        ]

        def searchResult = systemService.getGridData(controllerClass, params)
        def results = searchResult.result.collect {
            [
                    id                      : it.id,
                    type                    : it?.typeOfVoucher?.name ?: "",
                    name                    : it?.name ?: "",
                    methodOfVoucherNumbering: (it?.methodOfVoucherNumbering == (1.toString())) ? "Auto" : "Mannual"
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
        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button type="submit" id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
                [field: 'name', displayName: 'Name', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
                [field: 'type', displayName: 'Voucher Type', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
                [field: 'methodOfVoucherNumbering', displayName: 'Method Of Voucher Numbering', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2]
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
