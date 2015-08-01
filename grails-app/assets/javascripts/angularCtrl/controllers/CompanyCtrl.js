/**
 * Created by pc-3 on 4/2/15.
 */
angular.module("app.CompanyCtrl", ['callUrlService'])

    .controller("CompanyCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.companyObject = {};
            //$scope.companyObject.id = null;
            var params = {method: httpInfo.get, url: "../company/countryList", data: {}};
            var params1 = {method: httpInfo.get, url: "../user/findOrganizationList", data: {}};

            //debugger;
            //if(!$rootScope.orgShow && ($rootScope.company)){
            //    $scope.companyObject.organization = $rootScope.company.organization;
            //}

            httpService.callURL(params).then(function (data) {
                $scope.countryList = data;
            }, function (error) {
                alert("data not found");
            });

            httpService.callURL(params1).then(function (data) {
                $scope.orgList = data;
            }, function (error) {
                alert("data not found");
            });

            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../company/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    $scope.companyObject = data;
                    debugger;
                    $scope.getState($scope.companyObject.country);
                }, function (errors) {
                    alert("Data not found");
                });
            }

            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        function validationfn() {
            if (!$scope.companyObject.name) {
                $("#name").focus();
                logger.logError("please fill Company Name");
                return false;
            }
            return true;
        }


        $scope.save = function () {
            debugger;
            if (validationfn()) {
                var params = {
                    method: httpInfo.save,
                    url: "../company/save",
                    data: httpService.toParams($scope.companyObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    debugger;
                    if (data) {
                        alert("data successfully added");
                        $location.url('company/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.update = function () {
            debugger;
            if (validationfn()) {
                var params = {
                    method: httpInfo.update,
                    url: "../company/update",
                    data: httpService.toParams($scope.companyObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    debugger;
                    if (data) {
                        alert("data successfully update");
                        $location.url('company/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.delete = function () {
            var params = {method: httpInfo.delete,
                url: "../company/delete/" + $routeParams.id};

            httpService.callURL(params).then(function (data) {
                debugger;
                if (data) {
                    alert("data successfully deleted");
                    $location.url('company/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };

        $scope.getState = function (id) {
            debugger;
            var params = {
                method: httpInfo.get,
                url: "../company/findStateByCountry",
                data: httpService.toParams({id: id}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.stateList = data;
            });
        };

        $scope.checkValidateDate = function (date) {
            var l = date.length;
            var d = date.substring(l - 1, l);
            var m = date.substring(l - 4, l - 3);
            if (parseInt(d) == 1) {
                if (parseInt(m) == 4) {

                } else {
                    logger.logError("Please Select Only First of April \n Thanking you");
                    $scope.companyObject.financialFrom = new Date().getFullYear() + '-04-01';
                    $scope.companyObject.booksBeginigFrom = new Date().getFullYear() + '-04-01';
                }
            } else {
                logger.logError("Please Select Only First of April \n Thanking you");
                $scope.companyObject.financialFrom = new Date().getFullYear() + '-04-01';
                $scope.companyObject.booksBeginigFrom = new Date().getFullYear() + '-04-01';
            }
        }
    }]);
