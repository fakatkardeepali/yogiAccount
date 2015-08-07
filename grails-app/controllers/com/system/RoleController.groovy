package com.system

import com.annotation.ParentScreen
import grails.converters.JSON
import grails.transaction.Transactional

@ParentScreen(name = "Utilities", fullName = "Role", sortList = 1, link = "/role/create")
@Transactional(readOnly = true)
class RoleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SystemService systemService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Role.list(params), model: [roleInstanceCount: Role.count()]
    }

    def show(Role roleInstance) {
        respond roleInstance
    }

    def create() {
        respond new Role(params)
    }

    @Transactional
    def save(Role roleInstance) {
        bindData(roleInstance, params, [exclude: ["company", "lastUpdatedBy"]])
        if (roleInstance == null) {
            notFound()
            return
        }

        roleInstance.company = systemService.getCompanyObjectById(session['company'].id as Long);
        roleInstance.lastUpdatedBy = systemService.getUserById(session['activeUser'].user.id as Long);

        def screenChild = JSON.parse(params.frameList);
        screenChild.each { s ->
            if (s.bool) {
                s.module.each { s1 ->
                    if (s1.bool) {
                        roleInstance.addToRoleChild(parentScreen: systemService.getScreenById(s1.parentId as Long),
                                screen: systemService.getScreenById(s1.id as Long),
                                status: s1.bool,
                                company: systemService.getCompanyObjectById(session['company'].id as Long));
                    }
                }
            }
        }

        if (roleInstance.hasErrors()) {
            respond roleInstance.errors
//            return
        } else {

            roleInstance.save flush: true
            render 1
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])
//                redirect roleInstance
//            }
//            '*' { respond roleInstance, [status: CREATED] }
//        }
    }

    def edit(Role roleInstance) {
        def roleObject = systemService.getRoleById(params.id as Long);
        if (roleObject) {
            render roleObject as JSON
        } else {
            render '[]'
        }
    }

    @Transactional
    def update(Role roleInstance) {
        if (roleInstance == null) {
            notFound()
            return
        }

        Role.executeUpdate("delete RoleChild as r where r.role.id=:id", [id: roleInstance.id]);
        roleInstance.save(flush: true);
        roleInstance.properties = params


        def screenChild = JSON.parse(params.frameList);
        screenChild.each { s ->
            if (s.bool) {
                s.module.each { s1 ->
                    if (s1.bool) {
                        roleInstance.addToRoleChild(parentScreen: systemService.getScreenById(s1.parentId as Long),
                                screen: systemService.getScreenById(s1.id as Long),
                                status: s1.bool,
                                company: systemService.getCompanyObjectById(session['company'].id as Long));
                    }
                }
            }
        }

        if (roleInstance.hasErrors()) {
            respond roleInstance.errors
//            return
        } else {

            roleInstance.save flush: true
            render 1
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'Role.label', default: 'Role'), roleInstance.id])
//                redirect roleInstance
//            }
//            '*' { respond roleInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete(Role roleInstance) {

        if (roleInstance == null) {
            notFound()
            return
        }

//        roleInstance.delete flush: true
        if (roleInstance.delete(flush: true)) {
            render 1
        } else {
            render roleInstance.errors;
        }

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Role.label', default: 'Role'), roleInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NOT_FOUND }
//        }
    }

    def createRoleRightsChild() {
        render systemService.getCreateRoleRights();
    }

    def editRoleRightsChild() {
        if (params.id) {
            render systemService.getEditRoleRights(params.id as Long);
        } else {
            render systemService.getCreateRoleRights();
        }

    }

    def findFrames() {

        def result = [];
        def can = [];
        def rights = [];
        int parentCounter = -1;
        int screenCounter = -1;
        def dataUserRole = systemService.getUserRoleByUser(session['activeUser'].user.id as Long);
        def data = systemService.findAllScreenByControllerAndParentScreen()
        if (data) {
            data.sort { it.sortList }.each { d ->
                def haveScreen1 = systemService.findRoleChildByRoleAndParentScreen(params.roleId as Long, d.id as Long)
                def moduleRite = systemService.getScreenById(d.id as Long);
                if (moduleRite) {
                    parentCounter++;
                    screenCounter = -1;
                    can = [];
                    def datascr = systemService.findAllScreenByParentScreenWithSort(d.id as Long)
                    if (datascr) {
                        datascr.each { ds ->
                            def haveScreen2 = systemService.findRoleChildByRoleAndScreen(params.roleId as Long, ds.id as Long)
                            def screenRite = systemService.getScreenById(ds.id as Long);
                            if (screenRite) {
                                def dd = ds.id;
                                screenCounter++;
                                rights = [];
                                rights.push([
                                        name       : "Add",
                                        bool       : haveScreen2?.canAdd ?: false,
                                        moduleid   : '',
                                        parentid   : dd,
                                        level      : 3,
                                        collapsed  : true,
                                        parentIndex: parentCounter,
                                        screenIndex: screenCounter
                                ]);
                                rights.push([
                                        name       : "Update",
                                        bool       : haveScreen2?.canUpdate ?: false,
                                        moduleid   : '',
                                        parentid   : dd,
                                        level      : 3,
                                        collapsed  : true,
                                        parentIndex: parentCounter,
                                        screenIndex: screenCounter
                                ]); rights.push([
                                        name       : "Delete",
                                        bool       : haveScreen2?.canDelete ?: false,
                                        moduleid   : '',
                                        parentid   : dd,
                                        level      : 3,
                                        collapsed  : true,
                                        parentIndex: parentCounter,
                                        screenIndex: screenCounter
                                ]); rights.push([
                                        name       : "View",
                                        bool       : haveScreen2?.canView ?: false,
                                        moduleid   : "",
                                        parentid   : dd,
                                        level      : 3,
                                        collapsed  : true,
                                        parentIndex: parentCounter,
                                        screenIndex: screenCounter
                                ]); rights.push([
                                        name       : "Print",
                                        bool       : haveScreen2?.canPrint ?: false,
                                        moduleid   : '',
                                        parentid   : dd,
                                        level      : 3,
                                        collapsed  : true,
                                        parentIndex: parentCounter,
                                        screenIndex: screenCounter
                                ]);
                                can.push([
                                        bool       : haveScreen2?.canView ?: haveScreen2?.canDelete ?: haveScreen2?.canPrint ?: haveScreen2?.canUpdate ?: haveScreen2?.canAdd ?: false,
                                        name       : ds.domainName,
                                        moduleid   : ds.id,
                                        parentid   : d.id,
                                        module     : rights,
                                        collapsed  : true,
                                        level      : 2,
                                        parentIndex: parentCounter,
                                        screenIndex: ''
                                ])
                            }
                        }
                    }
                    result.push([
                            moduleid   : d.id,
                            name       : d.name,
                            parentid   : '',
                            bool       : haveScreen1 ? true : false,
                            module     : can,
                            collapsed  : true,
                            level      : 1,
                            parentIndex: '',
                            moduleIndex: '',
                            screenIndex: ''
                    ])
                }
            }
        }
        if (result) {
//                print result;
            render result as JSON;
        } else {
            render '[]'
        }
    }

    //===================================================== start ng - grid ===================================================
    def gridData() {

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

        def editDeleteButton = '<a tabindex=\"-1\" ng-href=\"#role/create/{{row.entity.id}}"><span class=\"ti-pencil-alt\"></span></a>' +
                '<button id="{{row.entity.id}}" ng-click="deleteData(row.entity.id)" style="border: none; background: transparent">' +
                '<img src="/' + grailsApplication.config.projectName + '/images/delete1.png"></button> ' +
                '<a tabindex=\"-1\" ng-href=\"#role/print/{{row.entity.id}}"><i class=\"ti-printer\"></i></span></a>'

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
                [field: 'name', displayName: 'Company Name', width: "600", headerCellTemplate: headerT1 + elemnt + headerT2],
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
