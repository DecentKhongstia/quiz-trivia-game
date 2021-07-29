/**
 * 
 */

angularApp.controller('resultController',["$scope", "$timeout", "$window", "AppService", function($scope, $timeout, $window, AppService) {
	var lobbyID = localStorage.getItem("lobbyID");
	var url = `get-answers`;
		
	$scope.user = new User();
	$scope.Answers = [];
	$scope.max = null
	$scope.result = {
			lobbyID:lobbyID,
			username:USERNAME,
	};
	$scope.total = 0;
	AppService.post(url, angular.toJson($scope.result), response => {
		$scope.max  = 0;
		$scope.Results = response.data.data;
		$scope.Answers = $scope.Results.answers;
		let no = $scope.Answers.length;
		
		$scope.total = (parseInt(no) * POINT_PER_QUESTION);
		let winners = [];
	}, error => {
		
	})
}]);