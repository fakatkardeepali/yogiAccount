/**
 * Created by pc-3 on 7/2/15.
 */
angular.module("app.OrganizationCtrl", [])
    .controller("OrganizationCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, logger) {

        init();

        function init() {
            $scope.orgObject = {};

            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../organization/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    $scope.orgObject = data;
                }, function (errors) {
                    alert("Data not found");
                });
            }
            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        function validationfn() {
            if (!$scope.orgObject.name) {
                $("#name").focus();
                logger.logError("please fill Name");
                return false;
            }
            return true;
        }

        $scope.save = function () {

            if (validationfn()) {
                var params = {
                    method: httpInfo.save,
                    url: "../organization/save",
                    data: httpService.toParams($scope.orgObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data succesfully added");
                        $location.url('organization/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.update = function () {

            if (validationfn()) {
                var params = {
                    method: httpInfo.update,
                    url: "../organization/update",
                    data: httpService.toParams($scope.orgObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data succesfully update");
                        $location.url('organization/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.delete = function () {
            var params = {method: httpInfo.delete,
                url: "../organization/delete/" + $routeParams.id};

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data succesfully deleted");
                    $location.url('organization/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        }
    }]);