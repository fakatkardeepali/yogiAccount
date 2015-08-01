/**
 * Created by akshay on 20/2/15.
 */

angular.module("app.AccountLedgerService", [])
    .service("AccountLedgerService", ["httpInfo", "httpService", "$routeParams", function (httpInfo, httpService, $routeParams) {
        var serviceObject = {};

        this.setInterestArray = function (arr) {
            serviceObject.interestArray = arr;
        };

        this.getInterestArray = function () {
            return serviceObject.interestArray;
        };
        this.getInterestArraySize = function () {
            return serviceObject.interestArray.length;
        };

        this.setMaintainBillArray = function (arr) {
            serviceObject.maintainBill = arr;
        };

        this.getMaintainBillArray = function () {
            return serviceObject.maintainBill;
        };

        this.setOpeningAmount = function (amount, status) {
            serviceObject.openingAmount = amount;
            serviceObject.amountStatus = status;
        };

        this.getOpeningAmount = function () {
            return serviceObject.openingAmount;
        };
        this.getAmountStatus = function () {
            return serviceObject.amountStatus;
        };

        this.setTotalBillArray = function (totalAmount) {
            serviceObject.totalAmount = totalAmount;
        };

        this.getTotalBillArray = function () {
            return serviceObject.totalAmount;
        };
        this.setOnAccountAmount = function (amount, status) {
            serviceObject.onAccountAmount = amount;
            serviceObject.onAccountAmountStatus = status;
        };
        this.getOnAccountAmount = function () {
            return serviceObject.onAccountAmount;

        };
        this.getOnAccountAmountStatus = function () {
            return serviceObject.onAccountAmountStatus;
        };
    }]);