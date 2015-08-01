/**
 * Created by Akhil on 21-06-2014.
 */

var plugin = function (options) {

    var me = this;

    me.init = function (scope, grid, services) {
        debugger;
        me.scope = scope;
        me.grid = grid;
        me.services = services;

    };
    me.checkPlugin = function () {
        debugger;
    }
};

angular.module('myapp.controller', [])

    .controller('testCnt', function ($scope) {
        /* $scope.myData = [{name: "Moroni", age: 50},
         {name: "Tiancum", age: 43},
         {name: "Jacob", age: 27},
         {name: "Nephi", age: 29},
         {name: "Enos", age: 34}] ;
         var plg = new plugin();
         debugger;
         $scope.gridOptions = { data:'myData',
         enableCellEditOnFocus :true ,
         enablePaging: true,
         showFooter: true,enablePinning:true,
         plugins: [ plg ]
         }

         $scope.check = function (){
         plg.checkPlugin();
         }*/
    });


