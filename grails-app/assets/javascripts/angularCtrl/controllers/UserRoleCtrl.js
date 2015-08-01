/**
 * Created by akshay on 9/2/15.
 */

angular.module("app.UserRoleCtrl", [])
    .controller("UserRoleCtrl", ["$scope", "httpService", "httpInfo", "$location", "$routeParams", "logger", function ($scope, httpService, httpInfo, $location, $routeParams, logger) {
        init();
        function init() {
            $scope.userRoleObject = {};
            $scope.userList = [];
            $scope.roleList = [];
            var params = {method: httpInfo.get, url: "../userRole/findAllUserByCompany", data: {}};
            httpService.callURL(params).then(function (data) {
                $scope.userList = data;
            });
            var params1 = {method: httpInfo.get, url: "../userRole/findAllRoleByCompany", data: {}};
            httpService.callURL(params1).then(function (data) {
                $scope.roleList = data;
            });

            if ($routeParams.id) {
                var params2 = {
                    method: httpInfo.get,
                    url: "../userRole/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                httpService.callURL(params2).then(function (data) {
                    $scope.userRoleObject = data;
                });
            }
            $scope.buttonShow = httpService.showHide($routeParams.id);
        }

        $scope.validation = function () {
            if (!$scope.user) {
                $("#user").focus();
                logger.logError("please select Username");
                return false;
            }
            if (!$scope.role) {
                $("#role").focus();
                logger.logError("please select Password");
                return false;
            }
            return true;
        };


        $scope.save = function () {

            var params = {
                method: httpInfo.save,
                url: "../userRole/save",
                data: httpService.toParams($scope.userRoleObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully added");
                    $location.url('userRole/list');
                }
            }, function (errors) {
                alert("something is wrong");
            });
        };

        $scope.update = function () {

            var params = {
                method: httpInfo.update,
                url: "../userRole/update",
                data: httpService.toParams($scope.userRoleObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully update");
                    $location.url('userRole/list');
                }
            }, function (errors) {
                alert("something is wrong");
            });
        };

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../userRole/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully deleted");
                    $location.url('userRole/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };
    }]);