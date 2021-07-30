/**
 * 
 */

angularApp.controller('endController',["$scope", "$timeout", "$window", "AppService", function($scope, $timeout, $window, AppService) {
	var lobbyID = localStorage.getItem("lobbyID");
	var url = `get-winner/${lobbyID}`
		
	$scope.user = new User();
	$scope.winner = "";
	$scope.max = null;
	$scope.tally = null;
	
	AppService.get(url, response => {
		$scope.max  = 0;
		$scope.Results = response.data;
		let winners = [];
		$scope.Results.winner.sort((a, b) => {
			return a.tally - b.tally;
	    });
		$scope.Results.winner.reverse();
		var position = 1;
		$scope.Results.winner.forEach((r, x) => {
			if(r.tally >= $scope.max ){
				$scope.max = r.tally;
				winners.push(r.username);
			}
			if(r.tally >= $scope.tally){
				r.position=position;
				$scope.tally = r.tally;
			}else{
				$scope.tally = r.tally;
				r.position=++position;
			}	
		});
		console.log($scope.users)
		console.log($scope.Results.winner)
		if(winners != null && winners.length > 0)
			$scope.winner = winners.join(', ');	
	})
}]);
