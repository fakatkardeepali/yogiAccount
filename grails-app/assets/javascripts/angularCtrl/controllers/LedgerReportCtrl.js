/**
 * Created by akshay on 19/2/15.
 */

angular.module("app.LedgerReportCtrl", [])
    .controller("LedgerReportCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {

        init();

        function init() {
            $scope.ledgerObject = {};

            var params = {
                method: httpInfo.get,
                url: "../voucher/findledgerListNotInProperty",
                data: httpService.toParams({arr: []}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.ledgerList = data;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.findReport = function (obj) {
            debugger;
            if (!$scope.ledgerObject.id) {
                logger.logError("please select Name");
                return false;
            }
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateLedgerReport",
                data: httpService.toParams(obj),
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
