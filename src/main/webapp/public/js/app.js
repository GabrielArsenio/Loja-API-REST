angular.module('loja', ['minhasDiretivas', 'ngRoute'])
	.config(function($routeProvider, $locationProvider) {

		$locationProvider.html5Mode(false);
		$locationProvider.hashPrefix('');

		$routeProvider
			.when('/produtos', {
				templateUrl: 'public/partials/produtos.html', //Até aqui funciona, ele abre meu tamplate na url correta
				controller: 'ProdutosController' // Quando comenta essa linha não mostra mais o erro
			})
			.when('/produtos/new', {
				templateUrl: 'public/partials/produtos-new.html',
				controller: 'ProdutoController'
			})
			.otherwise({
				redirectTo: '/'
			});
	});