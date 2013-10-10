'use strict';

angular.module('myApp.controllers', []).
  controller('MyCtrl1', function MyCtrl1($scope) {
    var a = []
    for (var i=0; i<50; i++) {
        a.push({name: "App" + i});
    }
    $scope.apps = a;

  })
  .controller('MyCtrl2', [function() {

  }]);