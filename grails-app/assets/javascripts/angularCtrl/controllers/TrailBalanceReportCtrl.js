/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.TrailBalanceReportCtrl", [])
    .controller("TrailBalanceReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "trailBalanceTemplate.html"
        }

        $scope.findReport = function () {
            $scope.groupObject.array = ["Expenses", "Income", 'Assets', 'Liabilities'];
            $scope.array = encodeURIComponent(["Expenses", "Income", "Assets", "Liabilities"]);
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateTrailBalanceReport",
                data: httpService.toParams($scope.groupObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.parentData = data;
                $scope.childData = data.child;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.findGroupSummaryReport = function (obj) {
            debugger
            $scope.templatePath = "groupSummaryTemplate.html"
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupSummaryReport",
                data: httpService.toParams(obj),
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
            debugger;
            $scope.groupObject.id = child.id
            if (child.isGroup) {
                $scope.isGroup = child.isGroup;
                $scope.findGroupSummaryReport($scope.groupObject)
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
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupVoucherReport",
                data: httpService.toParams(child),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.reportParent = data;
                $scope.reportChild = data.child;
            }, function (error) {
                alert("data not found");
            });
        }
    }]);