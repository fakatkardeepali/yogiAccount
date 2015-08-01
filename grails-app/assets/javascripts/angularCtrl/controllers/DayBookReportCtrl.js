/**
 * Created by akshay on 5/2/15.
 */
'use strict';
angular.module("app.DayBookReportCtrl", [])
    .controller("DayBookReportCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {
        //alert("hello");
        init();

        function init() {
            $scope.groupObject = {};
            $scope.templatePath = "dayBook.html"
        }

        $scope.findReport = function (obj) {
            var params = {
                method: httpInfo.get,
                url: "../voucher/generateDayBook",
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
    }]);