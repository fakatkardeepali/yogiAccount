/**
 * Created by akshay on 4/2/15.
 */

angular.module("app.tag", [])
    .directive('enterAsTab', function () {
        return function (scope, element, attrs) {

            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    event.preventDefault();

                    var elementToFocus = element.find('div');
                    var allInput = element.find('div').find('input');
                    var allInputLength = element.find('div').find("input").length;
                    if (element.next('div').find('input')[0]) {
                        elementToFocus = element.next('div').find('input')[0];
                    } else if (element.next('div').find('select')[0]) {
                        elementToFocus = element.next('div').find('select')[0];
                    } else if (element.next('div').find('button')[0]) {
                        elementToFocus = element.next('div').find('button')[0];
                    } else if (element.next('div').find('textarea')[0]) {
                        elementToFocus = element.next('div').find('textarea')[0];
                    }
                    if (angular.isDefined(elementToFocus)) {
                        elementToFocus.focus();
                    }
                }
            });
        };
    })
    .directive('focus', function () {
        return {
            restrict: 'A',
            link: function ($scope, elem, attrs) {

                elem.bind('keydown', function (e) {

                    var code = e.keyCode || e.which;
                    if (code === 13) {
                        e.preventDefault();
                        var inputOnly = elem.find('input');
                        var counter = 0;
                        inputOnly[++counter].focus();
                    }
                });
            }
        }
    })
    .directive('focusOn', function () {
        return {
            restrict: 'AE',
            link: function ($scope, elem, attrs) {

                elem.bind('keydown', function (e) {

                    var code = e.keyCode || e.which;
                    if (code === 13) {
                        e.preventDefault();
                        document.getElementById(attrs.focusOn).focus();
                    }
                });
            }
        }
    })
    .directive('focusReq', function ($http, $parse, logger) {
        return {
            restrict: 'AE',
            link: function (scope, element, attrs, ngModel) {

                element.bind('keydown', function (e) {
                    debugger;
                    var code = e.keyCode || e.which;
                    if (code === 13) {
                        e.preventDefault();
                        if (document.getElementById(attrs.id).value) {
                            //
                            document.getElementById(attrs.focusReq).focus();
                        } else {
                            document.getElementById(attrs.id).focus();
                        }
                    }
                });

//                var old = attr.value ? attr.value.toLowerCase() : ""
//                element.on  ('blur', function (event) {
//                    scope.$apply(function () {
//                        
////                        alert(ngModel);
//                        var v = attr.classname
//                        if (!attr.uniqueid) {
//                            if (old != (ngModel.$viewValue)) {
//                                $http.get("../login/checkUnique?class=" + attr.classname + "&name=" + ngModel.$viewValue + "&query=" + attr.ngQuery + "&id=" + attr.uniqueid)
//                                    .success(function (data) {
//                                        
//                                        if (data) {
//                                            logger.logError(attr.name + " already exist")
//                                            scope.uniqueDisable = true;
//                                        } else {
//                                            scope.alertMsg = ""
//                                            scope.uniqueDisable = false;
//                                        }
//
//                                    });
//                            } else {
//                                scope.uniqueDisable = false;
//                            }
//                        } else {
//                            if ((attr.uniqueid != ngModel.$viewValue) && attr.uniqueid) {
//                                $http.get(erpName + "/main/checkUnique?class=" + attr.classname + "&name=" + ngModel.$viewValue + "&query=" + attr.ngQuery + "&id=" + attr.uniqueid)
//                                    .success(function (data) {
//                                        
//                                        if (data) {
//                                            logger.logError(attr.name + " already exist")
//                                            scope.uniqueDisable = true;
//                                        } else {
//                                            scope.alertMsg = ""
//                                            scope.uniqueDisable = false;
//                                        }
//
//                                    });
//                            }
//                            else {
//                                scope.uniqueDisable = false;
//                            }
//                        }
//                    });
//                });
            }
        };
    });


function isNumberKey(val) {

    if (val) {

        // var nop = val.match(/[\d\.]+/);
        val = val.replace(/^0+/, '')
        var nop = val.match(/^[\-]?[\d]{0,9}[\.]{0,1}[\d]{0,2}/);
        //var nop = val.match(/^-?[0-9]*\.?[0-9]+$/)
        if (nop[0]) {

            return nop + "";
            //return parseInt(nop + "", 10);
        }
        else {
            return 0;
        }
    } else {
        return 0;

    }
};
