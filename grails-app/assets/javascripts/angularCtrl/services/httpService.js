/**
 * Created by akshay on 4/2/15.
 * jygy
 */


angular.module("callUrlService", [])
    .service("httpService", ['$http', '$q', '$log', function ($http, $q, $log) {

        this.callURL = function (params) {
            var deferred = $q.defer();
            $http(params)
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (msg, code) {
                    deferred.reject(msg);
                    $log.error(msg, code);
                });
            return deferred.promise;
        };

        this.toParams = function (params) {
            var p = [];
            for (var key in params) {

                p.push(key + '=' + encodeURIComponent(params[key]));
            }
            return p.join('&')
        };

        this.showHide = function (id) {

            if (id) {
                return true;
            } else {
                return false;
            }
        };

        this.getDate = function () {
            return new Date();
        }
    }]);