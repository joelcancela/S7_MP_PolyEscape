'use strict';

/**
 * @ngdoc function
 * @name polyEscapeApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the polyEscapeApp
 */
angular.module('polyEscapeApp')
  .controller('ConfigCtrl', ['$rootScope', '$scope', '$window', '$uibModal', 'PolyEscapeAPIService',
    function ($rootScope, $scope, $window, $uibModal, PolyEscapeAPIService) {

      $rootScope.timelimitModal = {
        templateUrl: 'views/modals/timeLimitModal.html',
        // backdrop: 'static',
        keyboard: false
      };

      $rootScope.storyModal = {
        templateUrl: 'views/modals/storyModal.html',
        // backdrop: 'static',
        keyboard: false
      };

      $rootScope.addStepModal = {
        templateUrl: 'views/modals/addStepModal.html',
        // backdrop: 'static',
        keyboard: false
      };


      $rootScope.escapeGameStory = "Echappez-vous dans le temps imparti en résolvant les éngimes !";
      $rootScope.escapeGameTimeLimit = 60;
      $rootScope.escapeGameSteps = [];
      $rootScope.stepTypes = [{name: "Caesar code", value: "caesarcode", inputStory: true}];//TODO

      $scope.addStep = function () {
        $rootScope.addStepModalInstance = $uibModal.open($rootScope.addStepModal);//creates modalForceToggle instance
        $rootScope.addStepModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameSteps.push(res);
            console.log("Added steps " + res);
          }
        });
      };

      $scope.setTimeLimit = function () {
        $rootScope.timelimitModalInstance = $uibModal.open($rootScope.timelimitModal);//creates modalForceToggle instance
        $rootScope.timelimitModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameTimeLimit = res;
            console.log("New time limit " + res);
          }
        });
      };

      $scope.setStory = function () {
        $rootScope.storyModalInstance = $uibModal.open($rootScope.storyModal);//creates modalForceToggle instance
        $rootScope.storyModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameStory = res;
            console.log("New story " + res);
          }
        });
      };

      $scope.playEscapeGame = function () {
        if ($rootScope.escapeGameSteps.length === 0) {
          $window.alert("Vous n'avez pas d'étapes pour votre escape game !");
        } else {
          $rootScope.configIsReady = true;
          $window.location.hash = "#/play";
        }
      };

    }]);
