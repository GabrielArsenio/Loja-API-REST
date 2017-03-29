angular.module('loja', ['minhasDiretivas', 'ngAnimate', 'ngRoute'])
	.config(($routeProvider, $locationProvider) => {

		$locationProvider.html5Mode(true);

		$routeProvider
			.when('!/produtos', {
				tamplateUrl: 'https://code.angularjs.org/1.6.3/angular-route.min.js',
				controller: 'ProdutosController'
			});
	});