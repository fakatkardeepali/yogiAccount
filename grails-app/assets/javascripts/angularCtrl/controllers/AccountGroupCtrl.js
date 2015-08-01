/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.AccountGroupCtrl", [])
    .controller("AccountGroupCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.doesEffect = false;
            $scope.primaryList = [];
            $scope.nonPrimaryList = [];
            var params = {method: httpInfo.get, url: "../accountGroup/findPrimaryGroup", data: {}};
            var params1 = {method: httpInfo.get, url: "../accountGroup/findNonPrimaryGroup", data: {}};

            httpService.callURL(params).then(function (data) {
                $scope.primaryList = data;
            });
            httpService.callURL(params1).then(function (data) {
                $scope.nonPrimaryList = data;
            });

            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../accountGroup/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    $scope.groupObject = data;
                    debugger;
                    $scope.checkChange();
                }, function (errors) {
                    alert("Data not found");
                });
            }

            $scope.buttonShow = httpService.showHide($routeParams.id)
        }



        $scope.checkChange = function () {

            if ($scope.groupObject.isGroupUnderPrimary) {
                if ($scope.groupObject.underGroup) {
                    $scope.data = _.find($scope.primaryList, {id: $scope.groupObject.underGroup});
                    if ($scope.data.name === "Expenses" || $scope.data.name === "Income") {
                        $scope.doesEffect = true;
                    } else {
                        $scope.doesEffect = false;
                    }
                }
            }
        };


        $scope.validation = function () {

            if (!$scope.groupObject.name) {
                $("#name").focus();
                logger.logError("please fill name");
                return false;
            }           
            if (!$scope.groupObject.underGroup) {
                $("#underGroup").focus();
                logger.logError("please select account group");
                return false;
            }
            return true;
        };

        $scope.save = function () {

            if ($scope.validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../accountGroup/save",
                    data: httpService.toParams($scope.groupObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data Successfully added");
                        $location.url('group/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.update = function () {
            $scope.validation();
            var params = {
                method: httpInfo.update,
                url: "../accountGroup/update",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully update");
                    $location.url('group/list');
                }
            }, function (errors) {
                alert("something is wrong");
            });
        };

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../accountGroup/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully deleted");
                    $location.url('group/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };

        $scope.setProperty = function (id) {
            $scope.data = _.find($scope.nonPrimaryList, {id: id});
            if ($scope.data) {
                $scope.groupObject.property = $scope.data.property;
            }
        }

    }]);