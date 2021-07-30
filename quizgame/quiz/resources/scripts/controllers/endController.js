/**
 * 
 */

angularApp.controller('endController',["$scope", "$timeout", "$window", "AppService", function($scope, $timeout, $window, AppService) {
	var lobbyID = localStorage.getItem("lobbyID");
	var url = `get-winner/${lobbyID}`
		
	$scope.user = new User();
	$scope.winner = "";
	$scope.max = null
	AppService.get(url, response => {
		$scope.max  = 0;
		$scope.Results = response.data;
		let winners = [];
		$scope.Results.winner.forEach((r, x) => {
			if(r.tally >= $scope.max ){
				$scope.max = r.tally;
				winners.push(r.username);
			}
		});
		/*console.log(`$scope.Results.winner`,$scope.Results.winner);
		$scope.Results.winner.sort((a, b) => {
			return a.tally - b.tally;
	    });*/
		if(winners != null && winners.length > 0)
			$scope.winner = winners.join(', ');	
	})
}]);