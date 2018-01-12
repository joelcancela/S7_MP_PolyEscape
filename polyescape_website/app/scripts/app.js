'use strict';

/**
 * @ngdoc overview
 * @name polyEscapeApp
 * @description
 * # polyEscapeApp
 *
 * Main module of the application.
 */
angular
  .module('polyEscapeApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/play', {
        templateUrl: 'views/gamerunner/game_runner.html',
        controller: 'GameRunnerCtrl',
        controllerAs: 'gamerunner'
      })
      .otherwise({
        redirectTo: '/'
      });

    $locationProvider.hashPrefix('');

  });
