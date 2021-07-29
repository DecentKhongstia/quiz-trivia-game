/**
 * 
 */

angularApp.controller('loginController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	AppService.clearGameSession();

	$scope.user = new User();
	$scope.successMsg = "";
	$scope.errorMsg = "";
	
	$scope.login = () => {
		AppService.post('login', angular.toJson($scope.user), (response) => {
			$window.location.href = URL_GAME_SCREEN;
			$scope.successMsg = response.data.msg;
		}, (error) => {
			console.log('error: ', error);
			$scope.errorMsg = error.data.msg;
		})
	}
}]);