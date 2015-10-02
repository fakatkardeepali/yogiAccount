/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.ProfitAndLossAcReportCtrl", [])
    .controller("ProfitAndLossAcReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "profitAndLossAcTemplate.html"
        }

        $scope.findReport = function (obj) {
            $scope.groupObject.array = ["Expenses", "Income"];
            $scope.array = encodeURIComponent(["Expenses", "Income"]);
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

        $scope.getInfoGroupAndLedger = function (child) {

            $scope.obj = {};
            $scope.obj.id = child.id;
            $scope.obj.form = $scope.groupObject ? $scope.groupObject.fromDate : "";
            $scope.obj.to = $scope.groupObject ? $scope.groupObject.toDate : "";
            $scope.templatePath = child.template;
            $scope.groupObject = $scope.obj;
            $scope.findLedgerReport($scope.obj)
        }

        $scope.findLedgerReport = function (child) {
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupVoucherReportByLedgerId",
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

        $scope.getGroupAndLedger = function (child) {
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
    }]);