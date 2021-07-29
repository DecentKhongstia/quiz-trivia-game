<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Quiz- Login</title>
	<%@include file="common/headerfiles.jsp"%>
	<script src="resources/scripts/controllers/loginController.js"></script>
	<script src="<c:url value="/resources/scripts/utils/sha256.min.js"/>"></script>
</head>
<body ng-app='angularApp' ng-controller="loginController">
	<div class="container center-align-content full-scale">
		<div class="card">
			<div class="card-header">
				<h1>Login</h1>
			</div>
			<div class="card-body">
				<form id="registerForm">
					<div class="form-group row">
						<label for="username" class="col">Username</label>
						<div class="col">
							<input type="text" class="form-control" id="username"
								ng-model="user.username" pattern-alpha-numeric>
						</div>
					</div>
					<div class="form-group row">
						<label for="password" class="col">Password</label>
						<div class="col">
							<input type="password" class="form-control" id="password"
								ng-model="user.password">
						</div>
					</div>
					<div class="form-group row" ng-if="successMsg != null || errorMsg != null">
						<label class="col" style="color:red" ng-if="errorMsg != null">{{errorMsg}}</label>
						<label class="col" style="color:green" ng-if="serverMsg != null">{{serverMsg}}</label>
					</div>
					<div class="form-group left-align-content">
						<button type="button" class="btn btn-outline-primary"
							ng-click="login();">Login</button>
					</div>
					<div class="form-group left-align-content">
						<a href="register">Register here</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>