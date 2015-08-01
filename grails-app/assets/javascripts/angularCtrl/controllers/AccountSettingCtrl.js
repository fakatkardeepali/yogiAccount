/**
 * Created by akshay on 10/2/15.
 */

angular.module("app.AccountSettingCtrl", [])
    .controller("AccountSettingCtrl", ["$scope", "$rootScope", "$modalInstance", "items", "httpInfo", "httpService", function ($scope, $rootScope, $modalInstance, items, httpInfo, httpService) {

        init();

        function init() {
            debugger;
            $scope.settingObject = {};

            $scope.settingObject.maintainBillWiseDetails = true;
            var params = {
                method: httpInfo.get,
                url: "../company/findAccountFeatureSetting",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.settingObject = data;
            }, function (errors) {
                alert("Data not found");
            });

        }


        $scope.update = function () {
            debugger;
            //if (validationfn()) {
            var params = {
                method: httpInfo.update,
                url: "../company/updateAccountFeature",
                data: httpService.toParams($scope.settingObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully added");
                    $location.url('company/list');
                }
            }, function (errors) {
                alert("something is wrong");
            });
            $modalInstance.close();
            //}
        };

        $scope.close = function () {
            debugger;
            $modalInstance.close();
        };

        $scope.defaultValue = function () {
            $scope.settingObject.maintainBillWiseDetails = true;
        }

    }]);
