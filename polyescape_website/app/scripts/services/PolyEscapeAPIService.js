'use strict';

/**
 * @ngdoc service
 * @name ChangelogLoaderService
 * @description Service used to retrieve ROCKFlows updates from changelog.json
 */

angular.module('polyEscapeApp')
  .service('PolyEscapeAPIService', ['$http', '$q', function ($http, $q) {

    this.serverHost = "http://localhost:8080";

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

  }]);
