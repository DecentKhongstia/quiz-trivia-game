/**
 * 
 */

angularApp.controller('loginController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	AppService.clearGameSession();

	$scope.user = new User();
	$scope.successMsg = "";
	$scope.errorMsg = "";
	
	$scope.login = () => {
		let flag = $scope.validate();
		if(flag){
			AppService.post('login', angular.toJson($scope.user), (response) => {
				$window.location.href = URL_GAME_SCREEN;
				$scope.successMsg = response.data.msg;
			}, (error) => {
				console.log('error: ', error);
				$scope.errorMsg = error.data.msg;
			})
		}
	}
	

	$scope.validate = () => {
		let status = true;
		let form = $scope.loginForm;
		if(form != null){
			form.username.$error = {};
			form.password.$error = {};
			form.username.$touched = true;
			form.password.$touched = true;
			if(form.username.$modelValue == null || form.username.$modelValue == ""){
				status = false;
				form.username.$error.required = true;
				form.username.$error.msg = "Username cannot be empty";
			}
			if(form.password.$modelValue == null || form.password.$modelValue == ""){
				status = false;
				form.password.$error.required = true;
				form.password.$error.msg = "Password cannot be empty";
			}else if(form.password.$modelValue != null && form.password.$modelValue.length < 6){
				/*
				status = false;
				form.password.$error.required = true;
				form.password.$error.msg = "Password length cannot be less than 6";
				*/
			}
		}
		if(status){
			$scope.user.password = SHA256($scope.user.password);
		}
		
		return status;
	}
}]);