/**
 * Created by akshay on 20/2/15.
 */
angular.module("app.InterestParaCtrl", [])
    .controller("InterestParaCtrl", ["$scope", "$rootScope", "httpInfo", "httpService", "$routeParams", "$location", "logger", "AccountLedgerService", "$modalInstance", function ($scope, $rootScope, httpInfo, httpService, $routeParams, $location, logger, ALService, $modalInstance) {


        $scope.calculateAmount = function () {
            debugger;
            var crAmount = 0, drAmount = 0, total = 0;
            var l = $scope.billChildJSON.length;
            debugger;
            if (l) {
                for (var i = 0; i < l; i++) {
                    if ($scope.billChildJSON[i].amountStatus === "Dr") {
                        drAmount = drAmount + parseFloat($scope.billChildJSON[i].amount);
                    } else if ($scope.billChildJSON[i].amountStatus === "Cr") {
                        crAmount = crAmount + parseFloat($scope.billChildJSON[i].amount);
                    }
                }
                $scope.tableTotal = crAmount > drAmount ? crAmount - drAmount : drAmount - crAmount;
                $scope.status1 = crAmount > drAmount ? "Cr" : "Dr";

                if ($scope.status1 === $scope.status3) {
                    if (parseFloat($scope.tableTotal) < parseFloat($scope.openingAmount)) {
                        $scope.onAccount = parseFloat($scope.openingAmount) - parseFloat($scope.tableTotal);
                        $scope.status2 = $scope.status3;
                    } else {
                        $scope.onAccount = parseFloat($scope.tableTotal) - parseFloat($scope.openingAmount);
                        $scope.status2 = $scope.status3 === "Dr" ? "Cr" : "Dr";
                    }
                } else {
                    if (parseFloat($scope.tableTotal) < parseFloat($scope.openingAmount)) {
                        $scope.onAccount = parseFloat($scope.openingAmount) + parseFloat($scope.tableTotal);
                        $scope.status2 = $scope.status3;
                    } else {
                        $scope.onAccount = parseFloat($scope.tableTotal) + parseFloat($scope.openingAmount);
                        $scope.status2 = $scope.status1 === "Dr" ? "Cr" : "Dr";
                    }
                }
            }
        };

        $scope.addRow = function () {
            $scope.interestPara.push({
                rate: 0,
                rateper: '',
                applicableDateFrom: '',
                applicableDateTo: ''
            })
        };

        $scope.close = function () {
            if ($scope.validation()) {
                ALService.setInterestArray($scope.interestPara);
                $modalInstance.close();
            }
        };

        $scope.validation = function () {
            if (!$scope.interestPara.length) {
                logger.logError("Please Click On Plus Symbol");
                return false;
            } else if ($scope.interestPara.length) {
                var l = $scope.interestPara.length;
                for (var i = 0; i < l; i++) {
                    if (!$scope.interestPara[i].rate) {
                        logger.logError("Please Fill Interest Rate");
                        return false;
                    }
                    if (!$scope.interestPara[i].rateper) {
                        logger.logError("Please Fill Interest Rate/Per");
                        return false;
                    }
                    if (!$scope.interestPara[i].applicableDateFrom) {
                        logger.logError("Please Fill Applicable Start Date");
                        return false;
                    }
                    if (!$scope.interestPara[i].applicableDateTo) {
                        logger.logError("Please Fill Applicable To Date");
                        return false;
                    }
                }
            }
            return true;
        };

        $scope.addBillChild = function () {
            debugger;
            $scope.billChildJSON.push({
                date: "",
                billNo: "",
                crDays: 0,
                billRef: "New Ref.",
                amount: $scope.billChildJSON.length == 0 ? $scope.openingAmount : 0,
                amountStatus: $scope.billChildJSON.length == 0 ? $scope.amountStatus : "Dr"
            });
            $scope.calculateAmount();
        };

        $scope.closeBill = function () {
            debugger;
            if ($scope.validationMaintainBill()) {
                ALService.setMaintainBillArray($scope.billChildJSON);
                ALService.setOnAccountAmount($scope.onAccount, $scope.status2);
                $modalInstance.close();
            }
        };


        $scope.validationMaintainBill = function () {
            if (!$scope.billChildJSON.length) {
                logger.logError("Please Click On Plus Symbol");
                return false;
            } else if ($scope.billChildJSON.length) {
                var l = $scope.billChildJSON.length;
                for (var i = 0; i < l; i++) {
                    if (!$scope.billChildJSON[i].date) {
                        logger.logError("Please Fill Start Date of Credit/Debit Amount");
                        return false;
                    }
                    if (!$scope.billChildJSON[i].billNo) {
                        logger.logError("Please Fill Bill No");
                        return false;
                    }
                    if (!$scope.billChildJSON[i].crDays) {
                        logger.logError("Please Fill Credit Days");
                        return false;
                    }
                    if (!$scope.billChildJSON[i].amount) {
                        logger.logError("Please Fill Amount");
                        return false;
                    }
                }
            }
            return true;
        };

        $scope.isNumber = function (n) {
            return isNumberKey(n);
        };

        init();

        function init() {
            debugger;
            $scope.interestPara = ALService.getInterestArray();
            $scope.billChildJSON = ALService.getMaintainBillArray();
            $scope.amountStatusList = ["Cr", "Dr"];
            $scope.ratePerList = ["30-day Month", "365-day Year", "Calender Month", "Calender Year"];
            $scope.openingAmount = ALService.getOpeningAmount();
            $scope.amountStatus = ALService.getAmountStatus();
            $scope.onAccount = ALService.getOnAccountAmount();
            $scope.status2 = ALService.getOnAccountAmountStatus();
            $scope.tableTotal = ALService.getTotalBillArray();
            $scope.status3 = ALService.getAmountStatus();
            $scope.calculateAmount();
        }


    }]);