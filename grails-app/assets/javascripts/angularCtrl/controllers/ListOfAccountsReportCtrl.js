/**
 * Created by user on 11/5/15.
 */

angular.module("app.ListOfAccountsReportCtrl", [])
    .controller("ListOfAccountsReportCtrl", ["$scope", "$rootScope", "httpInfo", "httpService", "$routeParams", "$location", "logger", "AccountLedgerService", function ($scope, $rootScope, httpInfo, httpService, $routeParams, $location, logger, ALService) {
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "listOfAccounts.html"

            var params = {
                method: httpInfo.get,
                url: "../voucher/generateListOfAccounts",
                data: httpService.toParams($scope.groupObject),
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

        $scope.findReport = function (obj) {
            debugger;
            var params = {
                method: httpInfo.get,
                url: "../voucher/generatePayablesOutstandingReport",
                data: httpService.toParams($scope.groupObject),
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

