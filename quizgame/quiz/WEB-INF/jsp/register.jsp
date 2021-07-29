<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quiz - Register</title>
		<%@include file="common/headerfiles.jsp"%>
		<script src="resources/scripts/controllers/registerController.js"></script>
		<script src="<c:url value="/resources/scripts/utils/sha256.min.js"/>"></script>
	</head>
	<body ng-app='angularApp' ng-controller="registerController">
		<div class="container center-align-content full-scale">
			<div class="card">
				<div class="card-header">
					<h1>Register</h1>
				</div>
				<div class="card-body">
					<form id="registrationForm" name="registrationForm">
						<div class="form-group row">
							<label for="username" class="col">Username</label>
							<div class="col">
								<input type="text" class="form-control" id="username" name="username" ng-model="user.username" required>
							</div>
							<div class="col-sm-12 error" 
								ng-if="registrationForm.username.$touched && registrationForm.username.$error.msg != null">
								{{registrationForm.username.$error.msg}}
							</div>
						</div>
						<div class="form-group row">
							<label for="password" class="col">Password</label>
							<div class="col">
								<input type="password" class="form-control" id="password" name="password" ng-model="user.password" required>
							</div>
							<span class="col-sm-12 error" 
								ng-if="registrationForm.password.$touched && registrationForm.password.$error.msg != null">
								{{registrationForm.password.$error.msg}}
							</span>
						</div>
						<div class="form-group row" ng-if="successMsg != null || errorMsg != null">
							<label class="col" style="color:red" ng-if="errorMsg != null">{{errorMsg}}</label>
							<label class="col" style="color:green" ng-if="serverMsg != null">{{serverMsg}}</label>
						</div>
						<div class="form-group left-align-content">
							<button type="button" class="btn btn-outline-primary" ng-click="register();">Register</button>
						</div>
						<div class="form-group left-align-content mt-3 pl-1">
							<a href="login">Login here</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>