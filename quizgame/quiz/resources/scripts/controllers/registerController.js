/**
 * 
 */

angularApp.controller('registerController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	$scope.user = new User();

	$scope.register = () => {
		AppService.post('register', angular.toJson($scope.user), (response) => {
			console.log('response: ', response);
			$window.location.href = "join-game-screen";
		}, (error) => {
			console.log('error: ', error);
		})
	}
}]);