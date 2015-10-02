/**
 * Created by pc-3 on 13/2/15.
 */
angular.module("app.VoucherCtrl", [])
    .controller("VoucherCtrl", ["$scope", "httpService", "httpInfo", "$routeParams", "$location", "logger", "$interval", "$window", function ($scope, httpService, httpInfo, $routeParams, $location, logger, $interval) {
        //alert("VoucherCtrl");
        init();

        function init() {
            $scope.vObject = {};
            $scope.tryChild=[
                {
                    "className": "Voucher",
                    "status": "",
                    "ledger": 43,
                    "debit": "1200",
                    "narration": ""
                },
                {
                    "className": "AccountLedger",
                    "status": "",
                    "ledger": 43,
                    "debit": "1200",
                    "narration": ""
                }
            ];
            $scope.billChildJSON = [];
            $scope.childJSON = [];
            $scope.billShow = false;
            $scope.vObject.amount = 0;
            $scope.amountStatus = ["Cr", "Dr"];
            $scope.isVoucherTypeDisable = false;
            //$scope.bill
            var params = {method: httpInfo.get, url: "../voucherType/findVoucherList", data: {}};
            var params2 = {method: httpInfo.get, url: "../voucher/findBillRefList", data: {}};

            httpService.callURL(params).then(function (data) {
                $scope.vTypeList = data;
                $scope.vObject.voucherType = data[0].id;
                $scope.findVoucherStatusByProperty($scope.vObject.voucherType)
                $scope.edit();
                debugger;
            }, function (error) {
                alert("data not found");
            });

            httpService.callURL(params2).then(function (data) {
                $scope.typeRefList = data;
                debugger;
            }, function (error) {
                alert("data not found");
            });
            $scope.buttonShow = httpService.showHide($routeParams.id)
        }

        $scope.edit = function () {
            if ($routeParams.id) {
                $scope.isVoucherTypeDisable = true;
                var params = {
                    method: httpInfo.get,
                    url: "../voucher/edit",
                    data: httpService.toParams({id: $routeParams.id}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    $scope.vObject = data;
                    $scope.findBalance($scope.vObject.partyName);
                    $scope.findVoucherStatusByProperty($scope.vObject.voucherType)
                    $scope.setChildren(data.saleChild, data.billChild);
                    //$interval($scope.childJSON = data.saleChild,2000);                   

                }, function (errors) {
                    alert("Data not found");
                });
            }
        }

        $scope.setChildren = function (bill, sale) {
            debugger;
            $scope.childJSON = bill;
            $scope.billChildJSON = sale;
            $scope.getSaleCalculation();
            $scope.checkSaleValid();
            $scope.getBillCalculation();
            if (sale.length > 0) {
                delete $scope.vObject["billChild"];
            }
            delete $scope.vObject["saleChild"];
        }

        function getLedgerList(array) {
            //this list find all ledger which is under or sub under the sundry creditors and sundry debtors
            var params1 = {
                method: httpInfo.get,
                url: "../voucher/findledgerList",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params1).then(function (data) {
                $scope.ledgerList = data;
            }, function (error) {
                alert("data not found");
            });
            return true;
        }

        function getAccountLedgerList(array) {
            //this list find all ledger which is under or sub under the Duties and  Taxes,Sale Account and Purchase Account
            var params3 = {
                method: httpInfo.get,
                url: "../voucher/findledgerList",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params3).then(function (data) {
                $scope.AccountLedger = data;
                //$scope.setChildren($scope.vObject.saleChild,$scope.vObject.billChild);
            }, function (error) {
                alert("data not found");
            });
            return true;
        }

        function getLedgerListNotInProperty(array) {
            //this list find all ledger except cash accounts and bank account
            var params = {
                method: httpInfo.get,
                url: "../voucher/findledgerListNotInProperty",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.ledgerList = data;
            }, function (error) {
                alert("data not found");
            });
            return true;
        }

        function getParticulatsListNotInProperty(array) {
            //this list find all ledger except cash accounts and bank account
            var params = {
                method: httpInfo.get,
                url: "../voucher/findledgerListNotInProperty",
                data: httpService.toParams({arr: array}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {
                $scope.AccountLedger = data;
            }, function (error) {
                alert("data not found");
            });
            return true;
        }


        $scope.isNumber = function (val) {
            return isNumberKey(val);
        }

        $scope.findBalance = function (id) {
            var params = {
                method: httpInfo.get,
                url: "../voucher/findBalanceByLedger",
                data: httpService.toParams({id: id}),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params).then(function (data) {
                $scope.balanceInfo = data;
                debugger;
            }, function (error) {
                alert("data not found");
            });
            var data = _.find($scope.ledgerList, {id: id})
            if (data) {
                $scope.maintainBillByBill = data.maintainBill
            } else {
                $scope.maintainBillByBill = false;
            }
        }

        $scope.findBillNoList = function (id, partyName) {
            debugger;
            var v = _.find($scope.typeRefList, {id: id})
            if (v.name === "Agst Ref.") {
                $scope.billShow = true;
                var params = {
                    method: httpInfo.get,
                    url: "../voucher/findAllBillByBillRef",
                    data: httpService.toParams({id: id, partyName: partyName}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                httpService.callURL(params).then(function (data) {
                    $scope.billNoList = data;
                    debugger;
                }, function (error) {
                    alert("data not found");
                });
            } else {
                $scope.billNoList = [];
                $scope.billShow = false;
            }

        }

        $scope.findAmountAgstBill = function (id, index) {
            debugger;
            var v = _.find($scope.billNoList, {billNo: id})
            if (v) {
                $scope.billChildJSON[index].amount = v.remainAmount
            }
        };

        $scope.addBillChild = function () {
            var creditDays=0;
            if (!$scope.vObject.partyName) {
                $("#partyName").focus();
                logger.logError("please select Party Ledger");
                return false;
            } else if (!$scope.vObject.date) {
                $("#date").focus();
                logger.logError("please select date");
                return false;
            } else if (!$scope.vObject.amount) {
                $("#amount").focus();
                logger.logError("please first fill Amount");
                return false;
            }
            creditDays = $scope.vObject.partyName?_.find($scope.ledgerList,{id:$scope.vObject.partyName}).creditDays:0;
            $scope.billChildJSON.push({
                typeRef: "",
                billNo: "",
                crDays: creditDays,
                amount: 0,
                billDate: $scope.vObject.date,
                amountStatus: $scope.voucherStatus.parentStatus
            });
        };

        $scope.deleteBillChild = function (index) {
            $scope.billChildJSON.splice(index, 1);
        };

        $scope.addChild = function () {
            $scope.childJSON.push({status: "", ledger: "", debit: 0, narration: ""});
        };

        $scope.deleteChild = function (index) {
            $scope.childJSON.splice(index, 1);
        };

        $scope.findVoucherStatusByProperty = function (id) {
            debugger;
            $scope.data = _.find($scope.vTypeList, {id: id});
            if ($scope.data) {
                if ($scope.data.property === "Contra") {
                    getLedgerList(["Bank Accounts", "Cash-in-Hand"]);
                    getAccountLedgerList(["Bank Accounts", "Cash-in-Hand"]);
                    if($scope.ledgerList.length<=0)alert("No Cash/Bank Created");
                } else if ($scope.data.property === "Journal") {
                    getLedgerListNotInProperty(["Bank Accounts", "Cash-in-Hand", "Bank OD A/c"]);
                    //getLedgerListNotInProperty(["Direct Expenses","Indirect Expenses", "Purchase Accounts","Sales Accounts","Direct Income","Indirect Income","Capital Account"]);
                    getParticulatsListNotInProperty(["Bank Accounts", "Cash-in-Hand", "Bank OD A/c"]);
                    //getParticulatsListNotInProperty(["Direct Expenses","Indirect Expenses", "Purchase Accounts","Sales Accounts","Direct Income","Indirect Income","Capital Account"]);
                } else if (($scope.data.property === "Payment")) {
                    getLedgerList(["Sundry Debtors", "Sundry Creditors","Capital Account","Indirect Expenses","Direct Expenses","Duties & Taxes","Provisions"]);
                    getAccountLedgerList(["Bank Accounts", "Cash-in-Hand","Direct Expenses","Indirect Expenses","Duties & Taxes","Provisions"]);
                } else if(($scope.data.property === "Receipt")){
                    //getLedgerList(["Sundry Debtors", "Sundry Creditors","Capital Account","Indirect Income","Direct Income"]);
                    getLedgerList(["Sundry Debtors", "Sundry Creditors","Capital Account","Indirect Income","Direct Income"]);
                    //getAccountLedgerList(["Bank Accounts", "Cash-in-Hand","Direct Income","Indirect Income","Capital Account"]);
                    getAccountLedgerList(["Bank Accounts", "Cash-in-Hand","Direct Income","Indirect Income","Capital Account"]);
                }else {
                    getLedgerList(["Sundry Debtors", "Sundry Creditors"]);
                    getAccountLedgerList(["Sales Accounts", "Purchase Accounts", "Duties & Taxes"]);
                }
                var params = {
                    method: httpInfo.get,
                    url: "../voucher/findVoucherStatusByProperty",
                    data: httpService.toParams({data: $scope.data.property}),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                httpService.callURL(params).then(function (data) {
                    $scope.voucherStatus = data;
                    $scope.billTotal = 0;
                    $scope.billStatus = $scope.voucherStatus.parentStatus
                }, function (error) {
                    alert("data not found");
                });

                if ($scope.data.methodOfVoucherNumbering == "2") {
                    $scope.readonlyVoucherNo = false
                } else {
                    $scope.readonlyVoucherNo = true
                }
                $scope.voucherLabel = $scope.data.name;
                $scope.useCommonNarration = $scope.data.useCommonNarration;
                $scope.narrationForEachEntry = $scope.data.narrationForEachEntry;
            }
        };

        function validation() {
            if (!$scope.vObject.voucherType) {
                $("#voucherType").focus();
                logger.logError("please Select Voucher Type");
                return false;
            } else if ((!$scope.readonlyVoucherNo) && (!$scope.vObject.voucherNo)) {
                debugger;
                $("#voucherNo").focus();
                logger.logError("please fill Voucher Number");
                return false;
            } else if (!$scope.vObject.date) {
                $("#date").focus();
                logger.logError("please fill Voucher Date");
                return false;
            } else if (!$scope.vObject.partyName) {
                $("#partyName").focus();
                logger.logError("please select ledger");
                return false;
            } else if (!$scope.vObject.amount) {
                $("#amount").focus();
                logger.logError("please fill Amount");
                return false;
            } else if ($scope.vObject.amount != $scope.saleTotal) {
                $("#amount").focus();
                logger.logError("please check your particulars total not equals to ledger amount");
                return false;
            } else if ($scope.maintainBillByBill) {
                if ($scope.vObject.amount != $scope.billTotal) {
                    $("#amount").focus();
                    logger.logError("please check bill total not equals to ledger amount");
                    return false;
                } else if (validateBillChild()) {
                }
            } else if (validateSaleChild()) {
            }
            return true;
        }

        function validateBillChild() {
            debugger;
            for (var i = 0; i < $scope.billChildJSON.length; i++) {
                if (!$scope.billChildJSON[i].typeRef) {
                    logger.logError("please select ref. type of " + (i + 1) + "th row");
                    return false
                } else {
                    var v = _.find($scope.typeRefList, {id: $scope.billChildJSON[i].typeRef});
                    if (v.name === "Agst Ref.") {
                        if (!$scope.billChildJSON[i].billNo) {
                            logger.logError("please select bill no of " + (i + 1) + "th row");
                            return false
                        }
                    } else if (v.name === "On Account") {
                    } else {
                        logger.logError("please fill bill no of " + (i + 1) + "th row");
                        return false
                    }
                }
            }
            return true;
        }

        function validateSaleChild() {
            debugger;
            for (var i = 0; i < $scope.childJSON.length; i++) {
                if (!$scope.childJSON[i].ledger) {
                    logger.logError("please select particulars of " + (i + 1) + "th row");
                    return false
                }
            }
            return true;
        }

        $scope.save = function () {
            if (validation()) {
                var params = {
                    method: httpInfo.save,
                    url: "../voucher/save",
                    data: httpService.toParams($scope.vObject) + "&child=" + angular.toJson($scope.childJSON) + "&billChild=" + angular.toJson($scope.billChildJSON)+"&tryChild="+angular.toJson($scope.tryChild),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };

                httpService.callURL(params).then(function (data) {
                    if (data) {
                        alert("data successfully added");
                        $location.url('voucher/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        }

        $scope.update = function () {
            debugger;
            if (validation()) {
                var params = {
                    method: httpInfo.update,
                    url: "../voucher/update",
                    data: httpService.toParams($scope.vObject) + "&child=" + angular.toJson($scope.childJSON) + "&billChild=" + angular.toJson($scope.billChildJSON),
                    headers: {'Content-Type': httpInfo.urlEncoded}
                };
                debugger;
                httpService.callURL(params).then(function (data) {

                    if (data) {
                        alert("data successfully update");
                        $location.url('voucher/list');
                    }
                }, function (errors) {
                    alert("something is wrong");
                });
            }
        };


        $scope.delete = function () {
            var params = {
                method: httpInfo.delete,
                url: "../voucher/delete/" + $routeParams.id
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully deleted");
                    $location.url('group/list');
                }
            }, function (errors) {
                alert("data could not be deleted");
            });
        };

        $scope.getBillCalculation = function () {
            debugger;
            if ($scope.maintainBillByBill) {
                var crAmount = 0;
                var drAmount = 0;
                angular.forEach($scope.billChildJSON, function (obj) {
                    $scope.billTotal = parseFloat($scope.billTotal) + parseFloat(obj.amount);
                    if (obj.amountStatus == "Cr") {
                        crAmount = parseFloat(crAmount) + parseFloat(obj.amount)
                    } else {
                        drAmount = parseFloat(drAmount) + parseFloat(obj.amount)
                    }
                });
                if (crAmount > drAmount) {
                    $scope.billTotal = crAmount - drAmount;
                    $scope.billStatus = "Cr";
                } else {
                    $scope.billTotal = drAmount - crAmount;
                    $scope.billStatus = "Dr";
                }
                $scope.checkBillValid();
            }
        };

        $scope.checkBillValid = function () {
            if ($scope.maintainBillByBill) {
                if ($scope.vObject.amount == $scope.billTotal) {
                    $scope.icon = "ti-check";
                } else {
                    $scope.icon = "ti-close";
                }
            }
        };

        $scope.checkSaleValid = function () {
            debugger;
            if ($scope.vObject.amount == $scope.saleTotal) {
                $scope.saleIcon = "ti-check";
            } else {
                $scope.saleIcon = "ti-close";
            }
        };

        $scope.getSaleCalculation = function () {
            $scope.saleTotal = 0;
            angular.forEach($scope.childJSON, function (obj) {
                $scope.saleTotal = parseFloat($scope.saleTotal) + parseFloat(obj.debit)
            });
        };

        $scope.calculateAmount = function(ledgerId,totalAmount,index){
               var ledgerObj = _.find($scope.AccountLedger,{id:ledgerId});
               if(ledgerObj.percentageOfCalculation>0){
                   $scope.childJSON[index].debit=(totalAmount*(ledgerObj.percentageOfCalculation/100));
               }
        };
        //print functionallity
        $scope.printInvoice = function () {
            debugger;
            var originalContents, popupWin, printContents;
            return printContents = document.getElementById("voucher").innerHTML, originalContents = document.body.innerHTML, popupWin = window.open(), popupWin.document.open(), popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="styles/main.css" /></head><body onload="window.print()">' + printContents + "</html>"), popupWin.document.close()

        }
    }]);