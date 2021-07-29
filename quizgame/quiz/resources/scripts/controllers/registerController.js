/**
 * 
 */

angularApp.controller('registerController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	$scope.user = new User();
	$scope.successMsg = "";
	$scope.errorMsg = "";
	
	$scope.register = () => {
		AppService.post('register', angular.toJson($scope.user), (response) => {
			console.log('response: ', response);
			$scope.successMsg = response.data.msg;
			$window.location.href = "join-game-screen";
		}, (error) => {
			console.log('error: ', error);
			$scope.errorMsg = error.data.msg;
		})
	}
}]);
