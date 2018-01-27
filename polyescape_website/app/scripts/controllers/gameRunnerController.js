'use strict';

/**
 * @ngdoc function
 * @name polyEscapeApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the polyEscapeApp
 */
angular.module('polyEscapeApp')
  .controller('GameRunnerCtrl', ['$rootScope', '$scope', '$window', '$uibModal', 'PolyEscapeAPIService', '$interval', '$interpolate',
    function ($rootScope, $scope, $window, $uibModal, PolyEscapeAPIService, $interval, $interpolate) {

      $rootScope.playerID = null;
      $rootScope.stepTimer = $rootScope.escapeGameTimeLimit * 60;
      $rootScope.currentPluginDescription = "";
      $rootScope.currentPluginIsInput = false;
      $rootScope.currentPluginNumerOfInput = 1;
      $rootScope.correctAnswerGiven = undefined;
      $scope.currentAnswer = {"answer": ""};
      var intervalPromise;
      $rootScope.startModal = {
        templateUrl: 'views/modals/escapeGameStartModal.html',
        backdrop: 'static',
        size: 'lg',
        windowClass: 'start-modal-popup',
        keyboard: false
      };
      $rootScope.loseModal = {
        templateUrl: 'views/modals/loseModal.html',
        backdrop: 'static',
        keyboard: false
      };
      $rootScope.winModal = {
        templateUrl: 'views/modals/winModal.html',
        backdrop: 'static',
        keyboard: false
      };

      $scope.submitAnswer = function () {
        var promise = PolyEscapeAPIService.answerResponse($rootScope.playerID, {"attempt": $scope.currentAnswer.answer});
        promise.then(function (result) {
          $rootScope.correctAnswerGiven = result.data.status;
        }, function (reason) {
          alert('Failed to send request to answer ' + reason);
        });
      };

      $scope.$on('timer-stopped', function (event, data) {
        var minutes = (($rootScope.escapeGameTimeLimit * 60) - (data.hours * 3600 + data.minutes * 60 + data.seconds)) / 60;
        var hours = minutes / 60;
        var realMinutes = minutes % 60;
        var seconds = (($rootScope.escapeGameTimeLimit * 60) - (data.hours * 3600 + data.minutes * 60 + data.seconds)) % 60;
        $rootScope.escapeGameTimeElapsed = Math.floor(hours) + "h: " + Math.floor(realMinutes) + "m: " + Math.floor(seconds)+"s";
      });

      $scope.getToNextStep = function () {
        if ($rootScope.correctAnswerGiven) {
          var promise = PolyEscapeAPIService.hasNextPlugin($rootScope.playerID);
          promise.then(function (result) {
            if (result.data.status === 'ok') {
              $rootScope.correctAnswerGiven = undefined;
              $scope.currentAnswer = {"answer": ""};
              getNextPlugin();
            } else if (result.data.status === 'finish') {
              $rootScope.$broadcast('timer-stop');
              $rootScope.winModalInstance = $uibModal.open($rootScope.winModal);//creates modal instance
              $rootScope.winModalInstance.result.then(function (res) {//result of the modal
                if (res) {//if the user confirms the force toggle
                  $window.location.hash = "#/";
                }
              });
            }
          }, function (reason) {
            alert('Failed to send request to answer ' + reason);
          });
        }
      };

      $scope.timeElapsedCallback = function () {
        $rootScope.loseModalInstance = $uibModal.open($rootScope.loseModal);//creates modal instance
        $rootScope.loseModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $window.location.hash = "#/";
          }
        });
      };

      function getNextPlugin() {
        var promise = PolyEscapeAPIService.getPluginDescription($rootScope.playerID);
        promise.then(function (result) {
          if (Object.keys(result.data.responseFormat).length === 0) {
            $rootScope.currentPluginIsInput = true;
          } else {
            $rootScope.currentPluginIsInput = false;
          }
          $rootScope.currentPluginDescription = $interpolate(result.data.attributes.description)($rootScope);
          if ($rootScope.currentPluginIsInput) {
            intervalPromise = $interval(triggerIntervalInputService, 3000);
          }
        }, function (reason) {
          alert('Failed to get next plugin ' + reason);
        });
      }

      function triggerIntervalInputService() {
        var promise = PolyEscapeAPIService.getPluginStatus($rootScope.playerID);
        promise.then(function (result) {
          if (result.data.status) {
            $interval.cancel(intervalPromise);
            $rootScope.correctAnswerGiven = true;
            $scope.getToNextStep();
          }
        }, function (reason) {
          alert('Failed to get next plugin ' + reason);
        });
      }

      function init() {
        if ($rootScope.configIsReady) {
          //Start modal
          $rootScope.startModalInstance = $uibModal.open($rootScope.startModal);//creates modal instance
          $rootScope.startModalInstance.result.then(function (res) {//result of the modal
            if (res) {//if the user confirms the force toggle
              $rootScope.$broadcast('timer-start');
            }
          });

          var promise = PolyEscapeAPIService.instantiateRunner($rootScope.escapeGameSteps);
          promise.then(function (result) {
            $rootScope.playerID = result.data.id;
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
