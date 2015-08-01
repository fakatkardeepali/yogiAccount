/**
 * Created by akshay on 10/2/15.
 */

angular.module("app.StatutorySettingCtrl", [])
    .controller("StatutorySettingCtrl", ["$scope", "$rootScope", "$modalInstance", "items", "httpInfo", "httpService", "$location", function ($scope, $rootScope, $modalInstance, items, httpInfo, httpService, $location) {

        init();

        function init() {
            $scope.statutoryObject = {};
            var params = {
                method: httpInfo.get,
                url: "../company/findStatutorySetting",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            $scope.mainFrame = true;
            $scope.vatChild = false;
            $scope.serviceTaxChild = false;
            $scope.tcsChild = false;
            $scope.tdsChild = false;
            $scope.stateList = [];
            $scope.typeOfDealerList = [];
            $scope.typeOfOrganizationList = [];
            $scope.typeOfCompanyList = [];


            httpService.callURL(params).then(function (data) {
                $scope.statutoryObject = data;

            }, function (errors) {
                alert("Data not found");
            });

            var params1 = {
                method: httpInfo.get,
                url: "../company/findAllOptions",
                data: {},
                headers: {'Content-Type': httpInfo.urlEncoded}
            };
            httpService.callURL(params1).then(function (data) {

                $scope.stateList = data.allState;
                $scope.typeOfDealerList = data.tod;
                $scope.typeOfOrganizationList = data.too;
                $scope.typeOfCompanyList = data.loc;
            }, function (errors) {
                alert("Data not found");
            });

        }

        $scope.save = function () {

            //if (validationfn()) {
            var params = {
                method: httpInfo.update,
                url: "../company/updateStatutory",
                data: httpService.toParams($scope.statutoryObject),
                headers: {'Content-Type': httpInfo.urlEncoded}
            };

            httpService.callURL(params).then(function (data) {

                if (data) {
                    alert("data successfully added");
                    //$location.url('company/list');
                }
            }, function (errors) {
                alert("something is wrong");
            });
            $modalInstance.close();
            //}
        };

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.vatOpen = function (bool) {

            if (bool) {
                $scope.mainFrame = false;
                $scope.vatChild = true;
            } else if (bool === 0) {
                // validation of vatChild frame
                //alert(bool);
                $scope.mainFrame = true;
                $scope.vatChild = false;
            } else {
                $scope.mainFrame = true;
                $scope.vatChild = false;
            }
        };

        $scope.vatChange = function (bool) {
            if (!bool) {
                $scope.statutoryObject.vatAlterDetails = bool;
            }
        };

        $scope.serviceTaxOpen = function (bool) {

            if (bool) {
                $scope.mainFrame = false;
                $scope.serviceTaxChild = true;
            } else if (bool === 0) {
                // validation of serviceChild frame
                //alert(bool);
                $scope.mainFrame = true;
                $scope.serviceTaxChild = false;
            } else {
                $scope.mainFrame = true;
                $scope.serviceTaxChild = false;
            }
        };

        $scope.serviceTaxChange = function (bool) {
            if (!bool) {
                $scope.statutoryObject.serviceTaxAlterDetails = bool;
            }
        };

        $scope.tdsOpen = function (bool) {

            if (bool) {
                $scope.mainFrame = false;
                $scope.tdsChild = true;
            } else if (bool === 0) {
                // validation of vatChild frame
                //alert(bool);
                $scope.mainFrame = true;
                $scope.tdsChild = false;
            } else {
                $scope.mainFrame = true;
                $scope.tdsChild = false;
            }
        };

        $scope.tdsChange = function (bool) {
            if (!bool) {
                $scope.statutoryObject.tdsAlterDetails = bool;
            }
        };
        $scope.tcsOpen = function (bool) {

            if (bool) {
                $scope.mainFrame = false;
                $scope.tcsChild = true;
            } else if (bool === 0) {
                // validation of vatChild frame
                //alert(bool);
                $scope.mainFrame = true;
                $scope.tcsChild = false;
            } else {
                $scope.mainFrame = true;
                $scope.tcsChild = false;
            }
        };

        $scope.tcsChange = function (bool) {
            if (!bool) {
                $scope.statutoryObject.tcsAlterDetails = bool;
            }
        };


    }]);
