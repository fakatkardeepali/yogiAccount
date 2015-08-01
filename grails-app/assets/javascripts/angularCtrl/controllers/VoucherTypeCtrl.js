/**
 * Created by pc-3 on 11/2/15.
 */
angular.module("app.VoucherTypeCtrl", [])
    .controller("VoucherTypeCtrl", ["$scope", "httpService", "httpInfo", "$location", "$routeParams", "logger", function ($scope, httpService, httpInfo, $location, $routeParams, logger) {
        init();

        function init() {
            $scope.vType = {};
            $scope.childList = [];
            $scope.autoList = [{id: "1", name: "Auto"}, {id: "2", name: "Mannual"}]
            $scope.vType.methodOfVoucherNumbering = "1";
            $scope.vType.startNumber = 1;
            $scope.vType.isTypeUpdate = true;
            $scope.vType.prefixWithZero = 0;
            showAdanceConfig($scope.vType.methodOfVoucherNumbering);
            var params = {method: httpInfo.get, url: "../voucherType/findVoucherList", data: {}};
            httpService.callURL(params).then(function (data) {
                $scope.voucherTypeList = data;
            }, function (error) {
                alert("voucherTypeList not found");
            });

            if ($routeParams.id) {

                var params = {
                    method: httpInfo.get,
                    url: "../voucherType/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    $scope.vType = data;
                    $scope.childList = data.child;
                    if (!$scope.vType.typeOfVoucher) {
                        $scope.typeOfVoucherDisable = true;
                        $scope.vType.isTypeUpdate = false;
                    }
                    delete $scope.vType["child"];
                }, function (errors) {
                    alert("Data not found");
                });
            }

            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        $scope.addChild = function () {
            $scope.childList.push({
                applicableFrom: "",
                startNumber: 1,
                prefix: "",
                postfix: "",
                applicableTo: ""
            });
        }

        $scope.showAdanceConfig = function (status) {
            showAdanceConfig(status);
        }

        function showAdanceConfig(status) {

            $scope.vType.isPreventDuplicates = false
            $scope.vType.useAdavanceConfiguration = false
            $scope.vType.useEffectiveDatesForVouchers = false
            $scope.childList = [];
            if (status === "1") {
                $scope.isPreventDuplicates = false
                $scope.useAdavanceConfiguration = true
                $scope.useEffectiveDatesForVouchers = true
            } else {
                $scope.isPreventDuplicates = true
                $scope.useAdavanceConfiguration = false
                $scope.useEffectiveDatesForVouchers = false
            }
        }


        $scope.isNumber = function (val) {
            return isNumberKey(val);
        }

        $scope.setProperty = function (id) {
            debugger;
            $scope.data = _.find($scope.voucherTypeList, {id: id});
            if ($scope.data) {
                $scope.vType.property = $scope.data.property;
            }
        }

        $scope.deleteChild = function (index) {
            $scope.childList.splice(index, 1);
        }

        function childValidation() {
            for (var i = 0; i < $scope.childList.length; i++) {
                if (!$scope.childList[i].applicableFrom) {
                    logger.logError("please fill value of strat date in " + (i + 1) + "th row");
                    return false
                } else if (!$scope.childList[i].startNumber) {
                    logger.logError("please fill value of strat number in " + (i + 1) + "th row");
                    return false
                } else if (!$scope.childList[i].applicableTo) {
                    logger.logError("please fill value of end date in " + (i + 1) + "th row");
                    return false
                }
            }
            return true;
        }

        function validation() {
            if (!$scope.vType.name) {
                $("#name").focus();
                logger.logError("please fill Name");
                return false;
            } else if ((!$scope.vType.typeOfVoucher) && ($scope.vType.isTypeUpdate)) {
                $("#typeOfVoucher").focus();
                logger.logError("please select voucher type");
                return false;
            } else if (!$scope.vType.methodOfVoucherNumbering) {
                $("#methodOfVoucherNumbering").focus();
                logger.logError("please select method of voucher numbering");
                return false;
            } else if (!$scope.vType.startNumber) {
                $("#startNumber").focus();
                logger.logError("please fill start number");
                return false;
            } else if (!childValidation()) {
                return false;
            }
            return true;
        }

        $scope.save = function () {
            if (validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../voucherType/save",
                    data: httpService.toParams($scope.vType) + "&child=" + angular.toJson($scope.childList),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully added");
                        $location.url('voucherType/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.update = function () {
            if (validation()) {
                var params = {
                    method: httpInfo.update,
                    url: "../voucherType/update",
                    data: httpService.toParams($scope.vType) + "&child=" + angular.toJson($scope.childList),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully update");
                        $location.url('voucherType/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../voucherType/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully deleted");
                    $location.url('voucherType/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };
    }]);