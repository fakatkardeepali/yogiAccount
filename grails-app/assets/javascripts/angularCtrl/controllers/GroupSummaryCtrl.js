/**
 * Created by akshay on 20/2/15.
 */
angular.module("app.GroupSummaryCtrl", [])
    .controller("GroupSummaryCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {

        init();
        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "groupSummaryTemplate.html"
            $scope.isGroup = true;
            var params = {
                method: httpInfo.get,
                url: "../voucher/findAccountGroup",
                data: httpService.toParams({arr: []}),
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
                $scope.findReport($scope.groupObject)
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

        //print functionallity
        $scope.printInvoice = function () {
            debugger;
            var originalContents, popupWin, printContents;
            return printContents = document.getElementById("summary").innerHTML, originalContents = document.body.innerHTML, popupWin = window.open(), popupWin.document.open(), popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="../css/main.css" /></head><body onload="window.print()">' + printContents + "</html>"), popupWin.document.close()

        }
    }]);
