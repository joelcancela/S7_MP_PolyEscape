'use strict';

/**
 * @ngdoc service
 * @name ChangelogLoaderService
 * @description Service used to retrieve ROCKFlows updates from changelog.json
 */

angular.module('polyEscapeApp')
  .service('PolyEscapeAPIService', ['$http', '$q', function ($http, $q) {

    /**
     * @ngdoc function
     * @name getAllConfig
     * @description retrieve all the variables defined in app/resources/config.json
     * @returns Function, a promise with the data in app/resources/config.json
     */
    this.getChangeLog = function () {
      // var deferred = $q.defer();
      // $http.get('./resources/changelog.json')
      //   .success(function (data) {
      //     deferred.resolve(data);
      //   })
      //   .error(function () {
      //     deferred.reject(GUIConstantsService.ERROR_CHANGELOGJSON);
      //   });
      // return deferred.promise;
    };


  }]);
