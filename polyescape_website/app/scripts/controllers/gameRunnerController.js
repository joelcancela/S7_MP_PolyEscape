'use strict';

/**
 * @ngdoc function
 * @name polyEscapeApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the polyEscapeApp
 */
angular.module('polyEscapeApp')
  .controller('GameRunnerCtrl', ['$rootScope', '$scope', '$window', '$uibModal', 'PolyEscapeAPIService',
    function ($rootScope, $scope, $window, $uibModal, PolyEscapeAPIService) {
      function init() {
        if ($rootScope.configIsReady) {
          console.log("JSON Sent");
          console.log($rootScope.escapeGameSteps);
          var promise = PolyEscapeAPIService.instantiateRunner($rootScope.escapeGameSteps);
          promise.then(function (result) {
            console.log(result);
            console.log("Game On");
            //Display history
            //Ask for next plugin
            promise = PolyEscapeAPIService.getNextPlugin();
            promise.then(function (result) {
              console.log(result);
            }, function (reason) {
              alert('Failed to get next plugin ' + reason);
            });
          }, function (reason) {
            alert('Failed to instantiate game runner ' + reason);
          });

        }
      }

      init();
    }]);
