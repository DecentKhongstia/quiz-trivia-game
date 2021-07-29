/**
 * 
 */

angularApp.controller('gameController',["$scope", "$window", "AppService", function($scope, $window, AppService) {
	AppService.clearGameSession();
	
	$scope.joinLobby = () => {
		AppService.joinLobby();
    }
	
	$scope.exitGame = () => {
		AppService.exitGame();
    }
}]);