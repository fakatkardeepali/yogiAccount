/**
 * Created by akshay on 20/2/15.
 */
angular.module("app.SalesRegisterCtrl", [])
    .controller("SalesRegisterCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {

        init();

        function init() {
            $scope.groupObject = {};
            $scope.groupObject.property = "Sales";
            $scope.templatePath = "saleRegisterReportTemplate.html";
            $scope.isGroupVoucher = false;
            var params = {
                method: httpInfo.get,
                url: "../voucher/findVoucherType",
                data: httpService.toParams({property: "Sale"}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.voucherTypeList = data;
            }, function (error) {
                alert("data not found");
            });
        }

        $scope.findReport = function (obj) {
            if (!$scope.groupObject.id) {
                logger.logError("please select Voucher Type");
                return false;
            }

            var params = {
                method: httpInfo.get,
                url: "../voucher/generateSalesRegisterReport",
                data: httpService.toParams(obj),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.reportParent = data;
                $scope.reportChild = data.child;
                $scope.isGroupVoucher = true;
            }, function (error) {
                alert("data not found");
                $scope.isGroupVoucher = false;
            });
        };

        //print functionallity
        $scope.printInvoice = function () {
            debugger;
            var originalContents, popupWin, printContents;
            return printContents = document.getElementById("summary").innerHTML, originalContents = document.body.innerHTML, popupWin = window.open(), popupWin.document.open(), popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="../css/main.css" /></head><body onload="window.print()">' + printContents + "</html>"), popupWin.document.close()

        };
    }]);
