/**
 * Created by akshay on 9/2/15.
 */


angular.module("app.RoleRightsCtrl", [])
    .controller("RoleRightsCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", function ($scope, httpService, httpInfo, $routeParams, $location, logger) {

        init();
        function init() {
            $scope.roleObject = {};
            $scope.roleObject.frameList = [];
            $scope.screenModule = [];
            $scope.screenCanList = [];


            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../role/editRoleRightsChild",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    $scope.frameList = data;
                });

                //var params = {
                //    method: httpInfo.get,
                //    url: "../role/findFrames",
                //    data: httpService.toParams({roleId: $routeParams.id}),
                //    headers: {'Content-Type': httpInfo.urlEncoded}
                //};
                //
                //httpService.callURL(params).then(function (data) {
                //    $scope.frameList = data;
                //});

                var params1 = {
                    method: httpInfo.get,
                    url: "../role/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params1).then(function (data) {
                    $scope.roleObject = data;

                }, function (errors) {
                    alert("Data not found");
                });
            } else {
                var params = {
                    method: httpInfo.get,
                    url: "../role/createRoleRightsChild",
                    data: httpService.toParams({roleId: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    debugger;
                    $scope.frameList = data;
                });

                //var params2 = {method: httpInfo.get, url: "../role/findFrames", data: {}};
                //httpService.callURL(params2).then(function (data) {
                //    $scope.frameList = data;
                //});
            }


            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        $scope.validation = function () {
            if (!$scope.roleObject.name) {
                $("#name").focus();
                logger.logError("please fill Authority Name");
                return;
            }
            return true;
        };


        $scope.save = function () {
            if ($scope.validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../role/save",
                    data: "name=" + $scope.roleObject.name + "&frameList=" + angular.toJson($scope.frameList),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully added");
                        $location.url('role/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.update = function () {

            if ($scope.validation()) {
                var params = {
                    method: httpInfo.update,
                    url: "../role/update",
                    data: "name=" + $scope.roleObject.name + "&id=" + $scope.roleObject.id + "&frameList=" + angular.toJson($scope.frameList),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully update");
                        $location.url('role/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../role/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully deleted");
                    $location.url('role/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };


        // Tree list for check and uncheck Coding Style

        $scope.allCheck = function (index, bool, pid, level) {
            debugger;
            if (level == 1) {
                var cl = $scope.frameList[index].module.length;
                for (var i = 0; i < cl; i++) {
                    $scope.frameList[index].module[i].bool = bool;
                    var ml = $scope.frameList[index].module[i].module.length;
                    for (var y = 0; y < ml; y++) {
                        $scope.frameList[index].module[i].module[y].bool = bool;
                    }
                }
            } else if (level == 2) {
                var can2 = $scope.frameList[pid].module;
                var boosh2 = false;
                for (var s in can2) {
                    if (can2[s].bool) {
                        boosh2 = can2[s].bool;
                    }
                }
                var ml1 = $scope.frameList[pid].module[index].module.length;
                for (var y1 = 0; y1 < ml1; y1++) {
                    $scope.frameList[pid].module[index].module[y1].bool = bool;
                }
                $scope.frameList[pid].bool = boosh2;
            }
        };
    }]);