/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.GroupOutstandingReportCtrl", [])
    .controller("GroupOutstandingReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("GroupOutstandingReportCtrl");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "groupOutstanding.html"
            $scope.isGroup = true;

            var array = ["Sundry Debtors", "Sundry Creditors"];
            var params = {
                method: httpInfo.get,
                url: "../voucher/findAccountGroupByName",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.groupList = data;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.findReport = function (obj) {
            debugger;
            if (!$scope.groupObject.id) {
                logger.logError("please select Group");
                return false;
            }
            $scope.groupObject.reportStatus = "Group";
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateGroupOutstandingReport",
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

        $scope.getInfoLedger = function (child) {
            debugger;
            $scope.groupObject.id = child.id
            if (child.isGroup) {
                $scope.isGroup = child.isGroup;
                $scope.findReport($scope.groupObject)
                //$scope.templatePath = child.template;
            } else {
                debugger;
                $scope.obj = child;
                $scope.obj.reportStatus = "Ledger";
                $scope.obj.fromDate = $scope.groupObject.fromDate;
                $scope.obj.toDate = $scope.groupObject.toDate;
                $scope.templatePath = child.template;
                $scope.findLedgerReport($scope.obj);
            }
        }

        $scope.findLedgerReport = function (child) {
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateLedgerOutstandingReport",
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

        //print functionallity
        $scope.printInvoice = function () {
            debugger;
            var originalContents, popupWin, printContents;
            return printContents = document.getElementById("summary").innerHTML, originalContents = document.body.innerHTML, popupWin = window.open(), popupWin.document.open(), popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="../css/main.css" /></head><body onload="window.print()">' + printContents + "</html>"), popupWin.document.close()

        }
    }]);