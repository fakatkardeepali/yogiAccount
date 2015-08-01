/**
 * Created by akshay on 20/2/15.
 */
angular.module("app.GroupVouchersCtrl", [])
    .controller("GroupVouchersCtrl", ["$scope", "$http", "$location", "httpInfo", "httpService", "$routeParams", "$rootScope", "logger", function ($scope, $http, $location, httpInfo, httpService, $routeParams, $rootScope, logger) {

        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "ledgerReportTemplate.html"
            $scope.isGroupVoucher = false;
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
                url: "../voucher/generateGroupVoucherReport",
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
        }
    }]);
