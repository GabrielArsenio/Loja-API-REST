angular.module('minhasDiretivas', []).directive('meuPainel', function() {

	var ddo = {
		restrict: 'AE', // Atribute Element
		scope: {
			titulo: '@'
		},
		transclude: true,
		templateUrl: 'public/js/directives/meu-painel.html'
	};

	return ddo; // Directive Definition Object
});