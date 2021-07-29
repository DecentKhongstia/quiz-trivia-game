/**
 * 
 */

angularApp.controller('gameController',["$scope", "$window", "AppService", function($scope, $window, AppService) {
	$scope.joinLobby = () => {
		$window.location.href = 'game-lobby';
    }
}]);