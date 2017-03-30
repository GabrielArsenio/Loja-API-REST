angular.module('loja').controller('ProdutosController', function($scope, $http) {
	$http.get('api/produtos')
		.then((res) => {
			$scope.produtos = res.data;
		}).catch((error) => {
			console.log(error);
		});
});