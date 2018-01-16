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

      $rootScope.currentPluginDescription = "";
      $rootScope.currentPluginIsInput = false;
      $rootScope.currentPluginNumerOfInput = 1;
      $rootScope.correctAnswerGiven = undefined;
      $scope.currentAnswer = {"answer": "Votre réponse"};

      $scope.submitAnswer = function () {
        console.log($scope.currentAnswer.answer);
        var promise = PolyEscapeAPIService.answerResponse({"attempt": $scope.currentAnswer.answer});
        promise.then(function (result) {
          $rootScope.correctAnswerGiven = result.data.success;
          console.log("Response " + result.data.success);
        }, function (reason) {
          alert('Failed to send request to answer ' + reason);
        });
      };

      $scope.getToNextStep = function () {
        console.log("go to next step");
        if ($rootScope.correctAnswerGiven) {
          var promise = PolyEscapeAPIService.hasNextPlugin();
          promise.then(function (result) {
            if (result.data.status === 'ok') {
              //next item TODO
              console.log("next step ");
              $rootScope.correctAnswerGiven = undefined;
              getNextPlugin();
            } else if (result.data.status === 'finish') {
              //TODO
              //ITS OVER
              //Stop countdown
              //Display time elapsed
              console.log("Finished");
            }
          }, function (reason) {
            alert('Failed to send request to answer ' + reason);
          });
        }
      };

      function getNextPlugin() {
        var promise = PolyEscapeAPIService.getPluginDescription();
        promise.then(function (result) {
          console.log(result.data);
          console.log(result.data.description);
          $rootScope.currentPluginDescription = result.data.description;
        }, function (reason) {
          alert('Failed to get next plugin ' + reason);
        });
      }

      function init() {
        if ($rootScope.configIsReady) {
          var promise = PolyEscapeAPIService.instantiateRunner($rootScope.escapeGameSteps);
          promise.then(function (result) {
            console.log(result);
            console.log("Game On");
            //Display history
            //Ask for next plugin
            getNextPlugin();
          }, function (reason) {
            alert('Failed to instantiate game runner ' + reason);
          });

        } else {
          $window.location.hash = "#/";
        }
      }

      init();
    }]);
