/**
 * Created by akshay on 7/2/15.
 */

angular.module("app.LedgerOutstandingReportCtrl", [])
    .controller("LedgerOutstandingReportCtrl", ["$scope", "$rootScope", "httpInfo", "httpService", "$routeParams", "$location", "logger", "AccountLedgerService", function ($scope, $rootScope, httpInfo, httpService, $routeParams, $location, logger, ALService) {
        init();

        function init() {
            $scope.obj = {};
            $scope.templatePath = "ledgerOutstanding.html"
            getLedgerList(["Sundry Debtors", "Sundry Creditors"]);
        }

        function getLedgerList(array) {
            //this list find all ledger which is under or sub under the sundry creditors and sundry debtors
            var params1 = {
                method: httpInfo.get,
                url: "../voucher/findledgerList",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params1).then(function (data) {
                $scope.ledgerList = data;
            }, function (error) {
                alert("data not found");
            });
            return true;
        }

        $scope.findReport = function (obj1) {
            debugger;
            if (!$scope.obj.id) {
                logger.logError("please select Name");
                return false;
            }
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateLedgerOutstandingReport",
                data: httpService.toParams(obj1),
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
