/**
 * Created by akshay on 24/2/15.
 */

angular.module("app.SplitCtrl", [])
    .controller("SplitCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {

        init();

        function init() {
            $scope.splitObject = {};
            $scope.splitObject.isCompanySplit = true;

            var params1 = {
                method: httpInfo.get,
                url: "../split/findCompany",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params1).then(function (data) {
                $scope.splitObject = data;
                $scope.splitObject.from = data.financialFrom;
                $scope.splitObject.isCompanySplit = true;
            });
        }

        $scope.validation = function () {
            debugger;
            if (!$scope.splitObject.to) {
                $("#to").focus();
                logger.logError("please Select First Day of Month");
                return false;
            }
            if (!$scope.checkValidDate()) {
                // logger.logError("please Select First Day of Month");
                return false;
            }
            return true;
        };

        $scope.save = function () {
            if ($scope.validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../split/save",
                    data: httpService.toParams($scope.splitObject),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                httpService.callURL(params).then(function (data) {
                    if (data == 1) {
                        alert("data Successfully added");
                    } else {
                        alert("something is wrong");
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.checkValidDate = function () {
            //console.log(toDate) 2015-02-01 yyyy-MM-dd
            var dateLength = $scope.splitObject.to.length;
            var day = $scope.splitObject.to.substring(dateLength - 1, dateLength);
            var month = $scope.splitObject.to.substring(dateLength - 4, dateLength - 3);
            var currentMonth = $scope.splitObject.from.substring(dateLength - 4, dateLength - 3);
            if (parseInt(day) == 1) {
                return true;
            } else if (month == currentMonth) {
                logger.logError("Sorry, Please Select up to March");
                return false;
            } else {
                logger.logError("please Select First Day of Month");
                return false;
            }
        }
    }]);