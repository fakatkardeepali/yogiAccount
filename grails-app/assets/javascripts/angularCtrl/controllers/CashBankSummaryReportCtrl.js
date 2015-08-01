/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.CashBankSummaryReportCtrl", [])
    .controller("CashBankSummaryReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "cashBankSummaryTemplate.html"

        }

        $scope.findReport = function (obj) {
            $scope.groupObject.array = ["Cash-in-Hand", "Bank Accounts"];
            $scope.array = encodeURIComponent(["Cash-in-Hand", "Bank Accounts"]);
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateCashBankSummaryReport",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.parentData = data;
                $scope.childData = data.child;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.getInfoGroupAndLedger = function (child) {
            $scope.groupObject.id = child.id;
            $scope.findLedgerReport($scope.groupObject)
        }

        $scope.findLedgerReport = function (child) {
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateCashBankSummaryReportByGroupId",
                data: httpService.toParams(child),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                if (data.isGroup) {
                    $scope.templatePath = "cashBankSummaryTemplate.html"
                } else {
                    $scope.templatePath = "cashBankLedgerSummaryTemplate.html"
                }
                $scope.parentData = data;
                $scope.childData = data.child;
            }, function (error) {
                alert("data not found");
            });
        }
    }]);