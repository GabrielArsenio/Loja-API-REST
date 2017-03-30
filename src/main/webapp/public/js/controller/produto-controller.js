angular.module('loja').controller('ProdutoController', function($scope, $http) {

	$scope.submeter = () => {

		if (!$scope.formulario.$valid) {
			return;
		}

		$http.post('api/produtos', $scope.produto)
			.then((res) => {
				$scope.produto = {};
				$scope.menssagem = 'Registro salvo com sucesso!'
			})
			.catch((error) => {
				$scope.menssagem = 'Erro ao salvar!';
			});
	};
});