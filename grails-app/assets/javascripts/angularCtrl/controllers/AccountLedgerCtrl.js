/**
 * Created by akshay on 7/2/15.
 */

angular.module("app.AccountLedgerCtrl", [])
    .controller("AccountLedgerCtrl", ["$scope", "$rootScope", "httpInfo", "httpService", "$routeParams", "$location", "logger", "AccountLedgerService", function ($scope, $rootScope, httpInfo, httpService, $routeParams, $location, logger, ALService) {

        init();

        $scope.changeGroupList = function (bool) {
            if (bool) {
                var params = {method: httpInfo.get, url: "../accountGroup/findPrimaryGroup", data: {}};

                httpService.callURL(params).then(function (data) {
                    $scope.nonPrimaryList = data;
                });
            } else {
                var params = {method: httpInfo.get, url: "../accountGroup/findNonPrimaryGroup", data: {}};

                httpService.callURL(params).then(function (data) {
                    $scope.nonPrimaryList = data;
                });
            }
        }

        function init() {
            $scope.ledgerObject = {};
            $scope.ledgerObject.openingBalance = 0;
            $scope.typeOfDutyList = [];
            $scope.dutyHeadList = [];
            $scope.typeOfDutyListForServiceTaxCheckBox = [];
            $scope.methodOfCalculationList = [];
            $scope.roundMethodList = [];
            $scope.vatTaxClassList = [];
            $scope.classificationList = [];
            $scope.categoryNameList = [];
            $scope.ledgerObject.percentageOfCalculation = 0;
            $scope.ledgerObject.creditDays = 0;
            $scope.ledgerObject.totalBillArray = 0;
            ALService.setTotalBillArray($scope.ledgerObject.totalBillArray);
            $scope.ledgerObject.showMaintainBill = false;
            $scope.interestPara = [];
            ALService.setInterestArray($scope.interestPara);
            $scope.billChildJSON = [];
            ALService.setMaintainBillArray($scope.billChildJSON);
            $scope.ratePerList = ["30-day Month", "365-day Year", "Calender Month", "Calender Year"];
            $scope.statusList = ["Dr", "Cr"];
            $scope.ledgerObject.status = "Dr";
            ALService.setOnAccountAmount(0, "Dr");

            // tds list
            $scope.nopList = [];
            $scope.dtList = [];
            var params9 = {method: httpInfo.get, url: "../company/findAllState", data: {}};
            httpService.callURL(params9).then(function (data) {
                $scope.stateList = data;
            });

            var params = {method: httpInfo.get, url: "../accountGroup/findNonPrimaryGroup", data: {}};

            httpService.callURL(params).then(function (data) {
                $scope.nonPrimaryList = data;
            });

            var params1 = {method: httpInfo.get, url: "../company/findStatutorySetting", data: {}};

            httpService.callURL(params1).then(function (data) {
                $scope.statutorySetting = data;
                // service Tax Condition
                if ($scope.statutorySetting.enableServiceTax) {
                    var params2 = {method: httpInfo.get, data: {}, url: "../accountLedger/findAllTaxType"};
                    httpService.callURL(params2).then(function (data) {
                        $scope.typeOfDutyListForServiceTaxCheckBox = data;
                        var serviceId = _.find($scope.typeOfDutyListForServiceTaxCheckBox, {name: "ServiceTax"}).id;
                        if (serviceId) {
                            var params3 = {
                                method: httpInfo.get,
                                url: "../accountLedger/findAllTaxChildByParentId",
                                data: httpService.toParams({flagId: serviceId}),
                                headers: {'Content-Type': httpInfo.urlEncoded}
                            };
                            httpService.callURL(params3).then(function (data) {
                                $scope.categoryNameList = _.filter(data, {remark: "SC"});
                                $scope.classificationList = _.filter(data, {remark: "Classification"});
                            });
                        }
                    });
                }
                // TDS Condition
                if ($scope.statutorySetting.tdsEnableTaxDeductedAtSource) {
                    var params4 = {method: httpInfo.get, data: {}, url: "../accountLedger/findAllTaxType"};
                    httpService.callURL(params4).then(function (data) {
                        $scope.typeOfDutyListForServiceTaxCheckBox = data;
                        var serviceId = _.find($scope.typeOfDutyListForServiceTaxCheckBox, {name: "TDS"}).id;
                        if (serviceId) {
                            var params5 = {
                                method: httpInfo.get,
                                url: "../accountLedger/findAllTaxChildByParentId",
                                data: httpService.toParams({flagId: serviceId}),
                                headers: {'Content-Type': httpInfo.urlEncoded}
                            };
                            httpService.callURL(params5).then(function (data) {
                                $scope.nopList = _.filter(data, {remark: "NOP"});
                                $scope.dtList = _.filter(data, {remark: "DT"});
                            });
                        }
                    });
                }
            });

            var params6 = {
                method: httpInfo.get,
                url: "../company/findAccountFeatureSetting",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params6).then(function (data) {
                $scope.accountSetting = data;
                debugger;
                $scope.ledgerObject.showMaintainBill = $scope.accountSetting.forNontradingAccountAlso ? true : false;
                $scope.ledgerObject.showActiveIntersetCalculation = $scope.accountSetting.activeIntersetCalculation;
            }, function (errors) {
                alert("Data not found");
            });

            var params7 = {
                method: httpInfo.get,
                url: "../company/findStatutorySetting",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params7).then(function (data) {
                $scope.statutorySetting = data;
                debugger;
            }, function (errors) {
                alert("Data not found");
            });

            // for editing part ==========================================================
            if ($routeParams.id) {
                var params8 = {
                    method: httpInfo.get,
                    url: "../accountLedger/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params8).then(function (data) {
                    $scope.ledgerObject = data;
                    debugger;
                    $scope.changeGroupList($scope.ledgerObject.usePrimaryGroup);
                    $scope.ledgerObject.showMaintainBill = $scope.accountSetting.forNontradingAccountAlso ? true : false;
                    $scope.ledgerObject.showActiveIntersetCalculation = $scope.accountSetting.activeIntersetCalculation ? true : false;
                    $scope.interestPara = $scope.ledgerObject.childs;
                    ALService.setInterestArray($scope.interestPara);
                    $scope.changeUnder($scope.ledgerObject.underGroup)
                    $scope.billChildJSON = _.filter($scope.ledgerObject.billData, {billRef: "New Ref."});
                    ALService.setMaintainBillArray($scope.billChildJSON);
                    debugger;
                    if ($scope.ledgerObject.billData.length > 0) {
                        $scope.ledgerObject.onAccountAmount = _.find($scope.ledgerObject.billData, {billRef: "On Account"}).amount;
                        $scope.ledgerObject.onAccountAmountStatus = _.find($scope.ledgerObject.billData, {billRef: "On Account"}).amountStatus;
                        ALService.setOnAccountAmount($scope.ledgerObject.onAccountAmount, $scope.ledgerObject.onAccountAmountStatus);
                    }
                }, function (errors) {
                    alert("Data not found");
                });
            }
            $scope.buttonShow = httpService.showHide($routeParams.id)
        }        

        $scope.changeUnder = function (underObj) {
            $scope.ledgerObject.showReconciliationDate = false;
            $scope.ledgerObject.showTypeOfDuty = false;
            $scope.ledgerObject.showIsServiceTax = false;
            $scope.isNOPtrue_DTfalse = false;
            $scope.isClassification = false;
            var data = _.find($scope.nonPrimaryList, {id: underObj});
            if ("Bank Accounts" === data.property || "Bank OD A/c" === data.property) {
                $scope.ledgerObject.showReconciliationDate = true;
                //$scope.ledgerObject.reconciliationDate = new Date(2015, 4, 1);
                debugger;
            } else if ("Duties & Taxes" === data.property) {
                var params = {method: httpInfo.get, data: {}, url: "../accountLedger/findAllTaxType"};
                httpService.callURL(params).then(function (data) {
                    $scope.typeOfDutyList = data;
                    if ($routeParams.id && $scope.typeOfDutyList.length) {
                        $scope.getAllChilds($scope.ledgerObject.typeOfDuty)
                    }
                });
                $scope.ledgerObject.showTypeOfDuty = true;

            } else if ("Sundry Creditors" === data.property || "Sundry Debtors" === data.property) {
                $scope.ledgerObject.showMaintainBill = true;
                $scope.isClassification = true;
                $scope.isNOPtrue_DTfalse = true;
                $scope.ledgerObject.showIsServiceTax = $scope.statutorySetting.enableServiceTax;
                $scope.ledgerObject.showIsTdsDeductee = $scope.statutorySetting.tdsEnableTaxDeductedAtSource;
                $scope.ledgerObject.showIsVat = $scope.statutorySetting.enableValueAddedTax;
                $scope.ledgerObject.showIsTcsApplicable = $scope.statutorySetting.tcsEnableTaxCollectedAtSource;
                // $scope.ledgerObject.showIsVat =  $scope.statutorySetting.tcsEnableTaxCollectedAtSource;
            } else if ("Purchase Accounts" === data.property || "Direct Expenses" === data.property) {
                $scope.ledgerObject.showIsServiceTax = $scope.statutorySetting.enableServiceTax;
                $scope.ledgerObject.showIsVat = $scope.statutorySetting.enableValueAddedTax;
                $scope.ledgerObject.showIsTdsDeductee = $scope.statutorySetting.tdsEnableTaxDeductedAtSource;
            } else if ("Fixed Assets" === data.property || "Indirect Expenses" === data.property) {
                $scope.ledgerObject.showIsServiceTax = $scope.statutorySetting.enableServiceTax;
                $scope.ledgerObject.showIsTdsDeductee = $scope.statutorySetting.tdsEnableTaxDeductedAtSource;
            } else if ("Sales Accounts" === data.property || "Direct Income" === data.property) {
                $scope.ledgerObject.showIsServiceTax = $scope.statutorySetting.enableServiceTax;
                $scope.ledgerObject.showIsVat = $scope.statutorySetting.enableValueAddedTax;
            } else if ("Indirect Income" === data.property) {
                $scope.ledgerObject.showIsServiceTax = $scope.statutorySetting.enableServiceTax;
            } else if ("Branch/Divisions" === data.property || "Capital Account" === data.property || "Current Assets" === data.property ||
                "Deposits (Assets)" === data.property || "Loan and Advance (Assets)" === data.property || "Loans (liability)" === data.property ||
                "Misc. Expenses (Assets)" === data.property || "Reserves & Surplus" === data.property || "Secured Loans" == data.property || "Unsecured Loans" == data.property) {
                $scope.ledgerObject.showIsTdsDeductee = $scope.statutorySetting.tdsEnableTaxDeductedAtSource;
            } else {
                $scope.ledgerObject.showMaintainBill = $scope.accountSetting.forNontradingAccountAlso;
                $scope.ledgerObject.showIsServiceTax = false;
                $scope.ledgerObject.showIsTdsDeductee = false;
                $scope.ledgerObject.showIsVat = false;
                $scope.ledgerObject.showIsTcsApplicable = false;
            }
        };

        $scope.getAllChilds = function (flagId) {
            var params = {
                method: httpInfo.get,
                url: "../accountLedger/findAllTaxChildByParentId",
                data: httpService.toParams({flagId: flagId}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            $scope.dutyHeadList = [];
            $scope.methodOfCalculationList = [];
            var name = '';
            debugger;
            name = _.find($scope.typeOfDutyList, {id: flagId}).name;
            httpService.callURL(params).then(function (data) {
                $scope.ledgerObject.showPercentageOfCalculation = false;
                if (name === 'Excise') {
                    $scope.dutyHeadList = data;
                    $scope.ledgerObject.showPercentageOfCalculation = true;
                } else if (name === 'Others') {
                    $scope.methodOfCalculationList = data;
                    $scope.ledgerObject.showPercentageOfCalculation = true;
                } else if (name === "VAT" || name === "CST") {
                    $scope.methodOfCalculationList = _.filter(data, {remark: null});
                    $scope.dutyHeadList = _.filter(data, {remark: "VTC"});
                    $scope.ledgerObject.showPercentageOfCalculation = true;
                } else if (name === "ServiceTax") {
                    $scope.dutyHeadList = _.filter(data, {remark: "SC"});
                    //$scope.ledgerObject.showPercentageOfCalculation=false;
                } else if (name === "TDS") {
                    $scope.dutyHeadList = _.filter(data, {remark: "NOP"});
                    //$scope.ledgerObject.showPercentageOfCalculation=false;
                } else if (name === "TCS") {
                    $scope.dutyHeadList = _.filter(data, {remark: "LTT"});
                    //$scope.ledgerObject.showPercentageOfCalculation=false;
                }
                if ($routeParams.id && $scope.ledgerObject.dutyHead) {
                    $scope.getDutyHeadChild($scope.ledgerObject.dutyHead)
                }
            })
        };

        $scope.isNumber = function (n) {
            return isNumberKey(n);
        };

        $scope.getDutyHeadChild = function (dhId) {
            var params = {
                method: httpInfo.get,
                url: "../accountLedger/findAllTaxChildByParentId",
                data: httpService.toParams({flagId: dhId}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params).then(function (data) {
                debugger;
                $scope.methodOfCalculationList = data;
            });
            var params1 = {
                method: httpInfo.get,
                url: "../accountLedger/findRoundMethod",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params1).then(function (data) {
                debugger;
                $scope.roundMethodList = data;
            })
        };

        $scope.checkValue = function (bool) {
            if (!bool) {
                $scope.ledgerObject.creditDays = 0;
            }
        };



// validation
        $scope.validation = function () {

            if (!$scope.ledgerObject.name) {
                $("#name").focus();
                logger.logError("please fill name");
                return false;
            }
            if (!$scope.ledgerObject.underGroup) {
                $("#underGroup").focus();
                logger.logError("Please select account group");
                return false;
            }
            debugger;
            if ($scope.ledgerObject.showReconciliationDate && !$scope.ledgerObject.reconciliationDate) {
                $("#reconciliationDate").focus();
                logger.logError("Please Fill Date");
                return false;
            }
            if ($scope.ledgerObject.showTypeOfDuty) {
                if (!$scope.ledgerObject.typeOfDuty) {
                    $("#typeOfDuty").focus();
                    logger.logError("Please select Type Of Duty");
                    return false;
                }
                if ($scope.dutyHeadList.length && !$scope.ledgerObject.dutyHead) {
                    $("#dutyHead").focus();
                    logger.logError("Please select Duty Head");
                    return false;
                }
                if ($scope.ledgerObject.showPercentageOfCalculation && $scope.ledgerObject.percentageOfCalculation && !$scope.ledgerObject.methodOfCalculation) {
                    $("#methodOfCalculation").focus();
                    logger.logError("Please select method of Calculation");
                    return false;
                }

            }

            if ($scope.ledgerObject.activeInterestCalculation && !$scope.interestPara.length) {
                logger.logError("Please Fill Interest Calculation");
                return false;
            } else if ($scope.ledgerObject.activeInterestCalculation && $scope.interestPara.length) {
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

        $scope.save = function () {
            $scope.interestPara = ALService.getInterestArray();
            $scope.billChildJSON = ALService.getMaintainBillArray();
            $scope.ledgerObject.totalBillArray = ALService.getTotalBillArray();
            $scope.ledgerObject.onAccountAmount = ALService.getOnAccountAmount();
            $scope.ledgerObject.onAccountAmountStatus = ALService.getOnAccountAmountStatus();
            if ($scope.validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../accountLedger/save",
                    data: httpService.toParams($scope.ledgerObject) + "&interestPara=" + angular.toJson($scope.interestPara) + "&billJson=" + angular.toJson($scope.billChildJSON),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                httpService.callURL(params).then(function (data) {
                    if (data) {
                        alert("data Successfully added");
                        $location.url('ledger/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.update = function () {
            $scope.interestPara = ALService.getInterestArray();
            if ($scope.validation()) {
                var params = {
                    method: httpInfo.update,
                    url: "../accountLedger/update",
                    data: httpService.toParams($scope.ledgerObject) + "&interestPara=" + angular.toJson($scope.interestPara) + "&billJson=" + angular.toJson($scope.billChildJSON),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully update");
                        $location.url('ledger/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };

        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../accountLedger/delete/",
                data: httpService.toParams({id: $routeParams.id}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data == 1) {
                    alert("data successfully deleted");
                    $location.url('ledger/list');
                }else{
                    alert("data could not be deleted");
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };

        $scope.fillOpeningAmount = function (amount, tr) {
            ALService.setOpeningAmount(amount, tr);
        };



    }]);
