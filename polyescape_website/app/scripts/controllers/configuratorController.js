'use strict';

/**
 * @ngdoc function
 * @name polyEscapeApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the polyEscapeApp
 */
angular.module('polyEscapeApp')
  .controller('ConfigCtrl', ['$rootScope', '$scope', '$window', '$uibModal', 'PolyEscapeAPIService', 'TranslatorService',
    function ($rootScope, $scope, $window, $uibModal, PolyEscapeAPIService, TranslatorService) {

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
      $rootScope.escapeGameSteps = [];//Escape game steps configured
      $rootScope.stepTypes = [];//Steps available
      $rootScope.stepArgsValue = [];
      $rootScope.stepCreated = 0;

      function init() {
        var promise = PolyEscapeAPIService.getAvailablePlugins();
        promise.then(function (result) {
          // alert('Connexion ok');
          $rootScope.stepTypes = result.data;
        }, function (reason) {
          alert('Failed to connect to server: ' + reason);
        });
      };

      $scope.addStep = function () {
        $rootScope.addStepModalInstance = $uibModal.open($rootScope.addStepModal);//creates modalForceToggle instance
        $rootScope.addStepModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameSteps.push(res);
            console.log("Added step " + res);
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

      $rootScope.parseJson = function (json) {
        if (json === undefined) {
          return;
        }
        return JSON.parse(json);
      };

      $rootScope.createNewStep = function (stepSelectedType) {
        if (stepSelectedType === undefined) {
          return;
        }
        var jsonType = {name: JSON.parse(stepSelectedType).name};
        var jsonStep = angular.extend({}, jsonType, $rootScope.stepArgsValue[$rootScope.stepCreated]);
        $rootScope.stepCreated++;
        console.dir($rootScope.escapeGameSteps);
        return jsonStep;
      };

      $scope.configStep = function (index) {
        //TODO
      };

      $scope.removeStep = function (index) {
        $rootScope.escapeGameSteps.splice(index, 1);
      };

      $rootScope.translate = function (str) {
        return TranslatorService.translate(str);
      };

      $rootScope.getInputType = function (str) {
        if (str === "java.lang.String") {
          return "text";
        } else if (str === "java.lang.Integer") {
          return "number";
        }
      };

      $scope.downloadConfig = function() {
        return encodeURIComponent(JSON.stringify($rootScope.escapeGameSteps));
      };



      init();

    }]);
