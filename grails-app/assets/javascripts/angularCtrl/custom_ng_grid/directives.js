/**
 * Created by Akhil on 21-06-2014.
 */



angular.module('myapp.directives', ['ngGrid', 'ui.utils'])

    .directive('myGrid', function ($http, $location) {
        csvOpts = {
            columnOverrides: {
//            obj: function(o) { return o.a + '|' +  o.b; }
            }
        };
        hgtOpts = {minHeight: 10};
        pdfOpts = {};
        var gridOptions = {
            data: 'myData',
            enableCellEditOnFocus: false,
            enablePaging: true,
            primaryKey: 'id',
            enableRowReordering: false, // drag and drop Row
            enableColumnReordering: true, // drag and drop Columns
            showFooter: true,
            showFilter: false,
            enablePinning: true,
            enableColumnResize: true,
            showColumnMenu: true,
            groupsCollapsedByDefault: false,
            showGroupPanel: false,
            maintainColumnRatios: true,
            enableSorting: true,
            headerRowHeight: 25,
            sortInfo: {fields: ['name'], directions: ['asc']},
            enableCellSelection: false,
            showSelectionCheckbox: false,
            plugins: [new ngGridCsvExportPlugin(csvOpts)],
//            selectedItems: $scope.mySelections,
            multiSelect: true,
            columnDefs: 'myColumns'
        };


        return {
            restrict: 'E',
            template: '<div style="height: 30px;"> <a> <span class="ti-search" ng-click="showSearchFN()">Search</span> </a>' +
            '</div> <div class="gridStyle" ng-grid="gridOptions"></div>',
//            $templateCache:'menuTemplate.html',
            scope: {},
            controller: ('myGridController', function ($scope, $element, $attrs) {
                debugger;
                var attrs = $attrs;
                $scope.columnsDefinition = [];
                $scope.mySelections = [];
                $scope.myData = [];
                $scope.myColumns = [];
                $scope.gridOptions = {};
                $scope.showSearch = false;
                $scope.gridSearch = [];
                $scope.menuData = [];
                $scope.getShowSearch = function () {
                    return $scope.showSearch;
                };

                $scope.totalServerItems = 0;
                $scope.pagingOptions = {
                    pageSizes: [20,30,50],
                    pageSize: 20,
                    currentPage: 1
                };


                $scope.setPagingData = function (data, page, pageSize, total) {
                    // var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
                    $scope.myData = data;
                    $scope.totalServerItems = total;
                    // debugger;
                    setTimeout(function () {
                        if (!$scope.$$phase && !$scope.$root.$$phase) {
                            $scope.$apply();
                            //  $scope.safeApply() ;
                        }
                    }, 100);
                };

                function replaceAll(string, find, replace) {
                    return string.replace(new RegExp(find, 'g'), replace);
                }


                $scope.save = function (columns) {

                    var columnsField = [];

                    _.each(columns, function (num) {
                        debugger;
                        columnsField.push({
                            displayName: num.displayName,//1
                            field: num.field,//2
                            visible: num.visible,//3
                            width: num.width,//4
                            minWidth: num.minWidth,//5
                            sortable: num.sortable,//6
                            resizable: num.resizable,//7
                            groupable: num.groupable,//8
                            pinned: num.pinned,//9
                            editableCellTemplate: num.editableCellTemplate,//10
                            enableCellEdit: num.enableCellEdit,//11
                            cellEditableCondition: num.cellEditableCondition,//12
                            cellClass: num.cellClass,//13
                            headerClass: num.headerClass,//14
                            headerCellTemplate: replaceAll(num.headerCellTemplate, '&', "amp"),//15
                            cellFilter: num.cellFilter,//16
                            aggLabelFilter: num.aggLabelFilter,//17
                            maxWidth: num.maxWidth, //18
                            cellTemplate: replaceAll(num.cellTemplate, '&', "amp"), //19
                            columnsIndex: num.index  //20
                        })
                    });
                    debugger;
                    $http({
                        method: "post",
                        url: attrs.ajaxMenuDef,
                        data: "columnsField=" + angular.toJson(columnsField),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    })
//                    $http.post(attrs.ajaxMenuDef + '?columnsField=' + angular.toJson(columnsField), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                        .success(function (data) {
                            alert(data);
                        });
                };
                $scope.showMSG = function (evnt, column) {
                    debugger;
                    var searchObj = _.find($scope.gridSearch, {dataNM: column.field});
                    if (!searchObj && column.searchText != '') {
                        $scope.gridSearch.push({
                            dataNM: column.field,
                            searchText: column.searchText
                        });
                    } else {
                        if (column.searchText == '') {
                            delete $scope.gridSearch[_.findIndex($scope.gridSearch, {dataNM: column.field})];
                            $scope.gridSearch = _.compact($scope.gridSearch);
                        } else {
                            searchObj.searchText = column.searchText;
                        }
                    }
                    // console.log(JSON.stringify($scope.gridSearch));
                    $scope.getPagedDataAsync();
                };
                $scope.showSearchFN = function () {
                    // alert('show-search');
                    debugger;
                    console.log(JSON.stringify($scope.getShowSearch));
                    $scope.showSearch = !$scope.showSearch;
                    $scope.getShowSearch = [];
                };
                $scope.getPagedDataAsync = function () {
                    var pageSize = $scope.pagingOptions.pageSize,
                        page = $scope.pagingOptions.currentPage;
                    //
                    if (arguments.length == 2) {
                        pageSize = arguments[0];
                        page = arguments[1];
                    }
                    //
                    debugger;
                    var search = $scope.gridSearch && $scope.gridSearch.length > 0 ? $scope.gridSearch : [];
                    var params = {
                        pageSize: pageSize,
                        page: page,
                        search: search
                    };
                    setTimeout(function () {
                        var data;

                        debugger;
                        $http.get(attrs.ajaxDataUrl + "?pageSize=" + pageSize + "&page=" + page + "&search=" + JSON.stringify(search))
                            .success(function (responce) {
                                $scope.setPagingData(responce.data, page, pageSize, responce.total);
                            })
                            .error(function (data) {
                                console.log(data);
                            });

                    }, 100);
                };

                $scope.getPagedDataAsync();

                $scope.$watch('pagingOptions', function (newVal, oldVal) {
                    if (newVal !== oldVal
                        || newVal.pageSize !== oldVal.pageSize
                        || newVal.currentPage !== oldVal.currentPage) {
                        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
                    }
                }, true);


                function configureGrid() {
                    debugger;
                    var data1;
                    $http.get(attrs.ajaxGridDef)
                        .success(function (data) {
                            $scope.myColumns = data;
                            debugger;
                        });

//                    var xmlhttp = new XMLHttpRequest();
//                    xmlhttp.open("GET", attrs.ajaxMenuDef, false);
//                    xmlhttp.send();
//
//                    var data1 = xmlhttp.response;


                    debugger;

//                      gridOptions['menuTemplate'] = $scope.menuData;
                    gridOptions['enablePaging'] = true;
                    gridOptions['showFooter'] = true;
                    gridOptions['totalServerItems'] = 'totalServerItems';
                    gridOptions['pagingOptions'] = $scope.pagingOptions;
                    //   gridOptions['filterOptions'] = $scope.filterOptions;
                    gridOptions['selectedItems'] = $scope.mySelections;
                    gridOptions['afterSelectionChange'] = afterSelectionChange();

//                    gridOptions['columnDefs'] = getColumns();

                    $scope.gridOptions = gridOptions;

                    //  loadData();
//                    loadMenuData();
                }

                function loadMenuData() {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.open("GET", attrs.ajaxMenuDef, false);
                    xmlhttp.send();

                    $scope.menuData = xmlhttp.response;
                }


                $scope.deleteData = function (id) {
                    if (confirm("Are you Sure?")) {
                        debugger;
                        $http.delete(attrs.ajaxDeleteId + "?id=" + id, false)
                            .success(function (data) {
                                debugger;
                                if (data) {
                                    //alert("data successfully deleted")
                                    $scope.getPagedDataAsync();
                                    configureGrid();
                                    $scope.save();
                                } else {
                                    alert("data could not be deleted");
                                    $scope.getPagedDataAsync();
                                    configureGrid();
                                    $scope.save();
                                }                                
                            });
                    }
                };

                configureGrid();

                function afterSelectionChange() {
                    $scope.selectedIDs = [];
                    angular.forEach($scope.mySelections, function (item) {
                        $scope.selectedIDs.push(item)
                    });
                }

                $scope.checkAll = function (bool) {
                    debugger;
                    var l = $scope.myData.length;
                    for (var i = 0; i < l; i++) {
                        $scope.myData[i].sendMailBool = bool;
                    }

                };
                $scope.showPopup = function (id) {
                    $scope.$emit('showPopup', id);
                };

                $scope.sendMail = function () {
                    $scope.$emit('showPopup6', $scope.myData);
                };

            }),
            link: function (scope, element) {
                // debugger;
                //
                // scope.gridOptions ={};
                //  $scope.gridOptions = { };
                /*  var   gridOptions = {
                 columnDefs : [ {field: 'name', displayName:'Name'}, {field: 'age', displayName:'Age'}] ,
                 enableCellEditOnFocus :true ,
                 enablePaging: true,
                 showFooter: true ,
                 data: 'myData'
                 };

                 angular.forEach(gridOptions, function (value, key) {
                 scope.gridOptions[key]   = value;
                 });
                 */
                //    = gridOptions;
            }
        }

    });