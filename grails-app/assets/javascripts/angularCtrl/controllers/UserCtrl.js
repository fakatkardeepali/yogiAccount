/**
 * Created by pc-3 on 9/2/15.
 */
angular.module("app.UserCtrl", [])
    .controller("UserCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, logger) {

        init();

        function init() {
            $scope.userObject = {};
            $scope.userObject.useMultipleCompany = true;

            var params = {
                method: httpInfo.get,
                url: "../user/findOrganizationList",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.orgList = data;
            }, function (errors) {
                alert("Data not found");
            });
            

            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../user/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    $scope.userObject = data;
                    $scope.getCompany($scope.userObject.organization);
                }, function (errors) {
                    alert("Data not found");
                });
            }
            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        function validationfn() {
            if (!$scope.userObject.username) {
                $("#username").focus();
                logger.logError("please fill Username");
                return false;
            } else if (!$scope.userObject.password) {
                $("#password").focus();
                logger.logError("please fill password");
                return false;
            }
            return true;
        }

        $scope.getCompany = function (id) {

            $scope.companyList = [];

            var params = {
                method: httpInfo.get,
                url: "../user/findAllCompanybyOrganizationId",
                data: httpService.toParams({id: id}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                $scope.companyList = data;
            }, function (errors) {
                alert("company not found");
            });
        }

        $scope.save = function () {

            if (validationfn()) {
                var params = {
                    method: httpInfo.save,
                    url: "../user/save",
                    data: httpService.toParams($scope.userObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    alert("data succesfully added");
                    $location.url('user/list');
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.update = function () {

            if (validationfn()) {
                var params = {
                    method: httpInfo.update,
                    url: "../user/update",
                    data: httpService.toParams($scope.userObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    alert("data succesfully update");
                    $location.url('user/list');

                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../user/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data succesfully deleted");
                    $location.url('user/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        }
    }]);