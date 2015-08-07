package com.system

import com.annotation.ParentScreen
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND

@ParentScreen(name = "Utilities", fullName = "Organization", sortList = 1, link = "/organization/create")
@Transactional(readOnly = true)
class OrganizationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SystemService systemService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Organization.list(params), model: [organizationInstanceCount: Organization.count()]
    }

    def show(Organization organizationInstance) {
        respond organizationInstance
    }

    def create() {
        respond new Organization(params)
    }

    @Transactional
    def save(Organization organizationInstance) {
        if (organizationInstance == null) {
            notFound()
            return
        }

        if (organizationInstance.hasErrors()) {
            render organizationInstance.errors

        } else {
            organizationInstance.save flush: true
            render 1
        }
    }

    def edit(Organization organizationInstance) {
        def orgData = systemService.getOrganizationData(organizationInstance.id);
        if (orgData) {
            render orgData as JSON;
        } else {
            render "[]";
        }

    }

    @Transactional
    def update(Organization organizationInstance) {
        if (organizationInstance == null) {
            notFound()
            return
        }

        if (organizationInstance.hasErrors()) {
            render organizationInstance.errors
        } else {
            organizationInstance.save flush: true
            render 1
        }

    }

    @Transactional
    def delete(Organization organizationInstance) {

        if (organizationInstance == null) {
            notFound()
            return
        }

        if (organizationInstance.delete(flush: true)) {
            render 1
        } else {
            render organizationInstance.errors;
        }


    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
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
//                isDisplay: true,
//                orderBy:"id desc"
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
//        def editDeleteButton = '<form method="post" action="/' + grailsApplication.config.projectName + '/' + controllerName + '/list?scrid="  style="padding: 5px; page-break-before: avoid; page-break-after: avoid; margin: 0px" >' +
//                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
//                '<button type="submit" id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
///*
//                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
//*/
//                '<a tabindex=\"-1\" ng-href=\"#' + controllerName + '/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a></form>'
        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#organization/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#organization/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
                [field: 'name', displayName: 'Organization Name', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
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
