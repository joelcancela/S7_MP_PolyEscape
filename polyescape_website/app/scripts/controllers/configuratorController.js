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
        keyboard: false
      };

      $rootScope.storyModal = {
        templateUrl: 'views/modals/storyModal.html',
        keyboard: false
      };

      $rootScope.addStepModal = {
        templateUrl: 'views/modals/addStepModal.html',
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
          $rootScope.stepTypes = result.data;
        }, function (reason) {
          alert('Failed to connect to server: ' + reason);
        });
      };

      $scope.addStep = function () {
        $rootScope.addStepModalInstance = $uibModal.open($rootScope.addStepModal);//creates modal instance
        $rootScope.addStepModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameSteps.push(res);
          }
        });
      };

      $scope.setTimeLimit = function () {
        $rootScope.timelimitModalInstance = $uibModal.open($rootScope.timelimitModal);//creates modal instance
        $rootScope.timelimitModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameTimeLimit = res;
          }
        });
      };

      $scope.setStory = function () {
        $rootScope.storyModalInstance = $uibModal.open($rootScope.storyModal);//creates modal instance
        $rootScope.storyModalInstance.result.then(function (res) {//result of the modal
          if (res) {//if the user confirms the force toggle
            $rootScope.escapeGameStory = res;
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
        return jsonStep;
      };

      $scope.configStep = function (index) {
        //TODO configure a single step
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

      $scope.downloadConfig = function () {
        return encodeURIComponent(JSON.stringify($rootScope.escapeGameSteps));
      };


      $scope.loadConfig = function () {
        document.getElementById('file').click();
      };

      $scope.loadConfigOnSubmit = function ($fileContent) {
        $rootScope.escapeGameSteps = JSON.parse($fileContent);
      };

      init();

    }]);
