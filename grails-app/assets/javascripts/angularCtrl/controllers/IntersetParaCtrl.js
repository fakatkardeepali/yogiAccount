/**
 * Created by akshay on 10/2/15.
 */

angular.module("app.IntersetParaCtrl", [])
    .controller("IntersetParaCtrl", ["$scope", "$rootScope", "$modalInstance", "items", "httpInfo", "httpService", function ($scope, $rootScope, $modalInstance, items, httpInfo, httpService) {
        debugger;
        $scope.ok = function () {
            debugger;
            $modalInstance.close();
        };
    }]);
