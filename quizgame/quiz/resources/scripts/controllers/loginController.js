/**
 * 
 */

angularApp.controller('loginController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	sessionStorage.clear();
	localStorage.clear();
	$scope.user = new User();

	$scope.login = () => {
		AppService.post('login', angular.toJson($scope.user), (response) => {
			$window.location.href = URL_JOIN_GAME_SCREEN;
		}, (error) => {
			console.log('error: ', error);
		})
	}
}]);