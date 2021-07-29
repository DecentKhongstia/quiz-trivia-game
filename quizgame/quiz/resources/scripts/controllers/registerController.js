/**
 * 
 */

angularApp.controller('registerController',["$scope", "$window", "AppService", function($scope, $window, AppService) {

	$scope.user = new User();
	$scope.successMsg = "";
	$scope.errorMsg = "";
	
	$scope.register = () => {
		let flag = $scope.validate();
		if(flag){
			AppService.post('register', angular.toJson($scope.user), (response) => {
				console.log('response: ', response);
				$scope.successMsg = response.data.msg;
				$window.location.href = URL_GAME_SCREEN;
			}, (error) => {
				console.log('error: ', error);
				$scope.errorMsg = error.data.msg;
			})
		}
	}
	
	$scope.validate = () => {
		let status = true;
		let form = $scope.registrationForm;
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
				status = false;
				form.password.$error.required = true;
				form.password.$error.msg = "Password length cannot be less than 6";
			}
		}
		if(status){
			$scope.user.password = SHA256($scope.user.password);
		}
		
		return status;
	}
}]);
