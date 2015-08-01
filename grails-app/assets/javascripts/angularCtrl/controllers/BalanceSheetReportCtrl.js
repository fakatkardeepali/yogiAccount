/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.BalanceSheetReportCtrl", [])
    .controller("BalanceSheetReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "balanceSheetTemplate.html"

        }

        $scope.findReport = function (obj) {
            $scope.groupObject.array = ["Liabilities", "Assets"];
            $scope.array = encodeURIComponent(["Liabilities", "Assets"]);
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateBalanceSheetReport",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.parentData = data;
                $scope.purchaseChild = data.purchaseChild;
                $scope.saleChild = data.saleChild;
                $scope.profitLossChild = data.profitLossChild;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.getGroupAndLedger = function (child) {
            debugger;
            $scope.groupObject.id = child.id
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupSummaryReport",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.templatePath = "groupSummaryTemplate.html"
                $scope.parentData = data;
                $scope.childData = data.child;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.getInfoGroupAndLedger = function (child) {
            debugger;
            $scope.groupObject.id = child.id
            if (child.isGroup) {
                $scope.isGroup = child.isGroup;
                $scope.getGroupAndLedger($scope.groupObject)
                //$scope.templatePath = child.template;
            } else {
                //$location.path('voucher/create/'+child.id)
                $scope.obj = {};
                $scope.obj.id = child.id;
                $scope.obj.form = $scope.groupObject ? $scope.groupObject.fromDate : ""
                $scope.obj.to = $scope.groupObject ? $scope.groupObject.toDate : ""
                $scope.templatePath = child.template;
                $scope.findLedgerReport($scope.obj)
            }
        }

        $scope.findLedgerReport = function (child) {
            debugger;
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupVoucherReport",
                data: httpService.toParams(child),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.reportParent = data;
                $scope.reportChild = data.child;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.getProfitAndLossReport = function () {
            debugger;
            $scope.templatePath = "profitAndLossAcTemplate.html"
            $scope.groupObject.array = ["Expenses", "Income"];
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateProfitAndLossReport",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.parentData = data;
                $scope.purchaseChild = data.purchaseChild;
                $scope.saleChild = data.saleChild;
            }, function (error) {
                alert("data not found");
            });
        }
    }]);