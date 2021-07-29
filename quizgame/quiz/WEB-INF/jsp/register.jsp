<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quiz - Register</title>
		<%@include file="common/headerfiles.jsp"%>
		<script src="resources/scripts/controllers/registerController.js"></script>
	</head>
	<body ng-app='angularApp' ng-controller="registerController">
		<div class="container center-align-content">
			<div class="card">
				<div class="card-header">
					<h1>Register</h1>
				</div>
				<div class="card-body">
					<form id="registerForm">
						<div class="form-group row">
							<label for="username" class="col">Username</label>
							<div class="col">
								<input type="text" class="form-control" id="username" ng-model="user.username">
							</div>
						</div>
						<div class="form-group row">
							<label for="password" class="col">Password</label>
							<div class="col">
								<input type="password" class="form-control" id="password" ng-model="user.password">
							</div>
						</div>
						<div class="form-group left-align-content">
							<button type="button" class="btn btn-outline-primary" ng-click="register();">Register</button>
						</div>
						<div class="form-group left-align-content">
							<a href="login">Login here</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>