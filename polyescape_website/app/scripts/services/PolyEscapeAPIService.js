'use strict';

/**
 * @ngdoc service
 * @name ChangelogLoaderService
 * @description Service used to retrieve ROCKFlows updates from changelog.json
 */

angular.module('polyEscapeApp')
  .service('PolyEscapeAPIService', ['$http', '$q', function ($http, $q) {

    // this.serverHost = "http://localhost:8080";
    this.serverHost = "https://ns3265327.ip-5-39-78.eu:8443";

    this.getAvailablePlugins = function () {
      var deferred = $q.defer();
      $http({
        method: 'GET',
        url: this.serverHost + '/plugins/list'
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to retrieve available plugins");
      });
      return deferred.promise;
    };

    this.getPluginStatus = function (id) {
      var deferred = $q.defer();
      $http({
        method: 'GET',
        url: this.serverHost + '/runners/'+id+'/inputStatus'
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to retrieve plugin status");
      });
      return deferred.promise;
    };

    this.instantiateRunner = function (jsonSteps) {
      var deferred = $q.defer();
      $http({
        method: 'PUT',
        url: this.serverHost + '/runners/instantiate',
        data: jsonSteps,
        headers: {
        "Content-Type": "application/json"
      }
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to instantiate runner");
      });
      return deferred.promise;
    };

    this.getPluginDescription = function (id) {
      var deferred = $q.defer();
      $http({
        method: 'GET',
        url: this.serverHost + '/runners/'+id+'/description'
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to retrieve available plugins");
      });
      return deferred.promise;
    };

    this.hasNextPlugin = function (id) {
      var deferred = $q.defer();
      $http({
        method: 'GET',
        url: this.serverHost + '/runners/'+id+'/status'
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to retrieve available plugins");
      });
      return deferred.promise;
    };


    this.answerResponse = function (id, json) {
      var deferred = $q.defer();
      $http({
        method: 'POST',
        url: this.serverHost + '/runners/'+id+'/answer',
        data:json,
        headers: {
          "Content-Type": "application/json"
        }
      }).then(function successCallback(data) {
        deferred.resolve(data);
      }, function errorCallback(data) {
        deferred.reject("Failed to answer to step");
      });
      return deferred.promise;
    };

  }]);
