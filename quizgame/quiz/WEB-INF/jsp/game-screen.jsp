<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<%@include file="common/headerfiles.jsp"%>
		<script src="resources/scripts/controllers/gameController.js"></script>
	</head>
	<body ng-app='angularApp' ng-controller="gameController"
		ng-init="connect()">
		<div class="container center-align-content full-scale">
			<form id="registerForm">
				<div class="form-group row">
					<button type="button" class="btn btn-outline-primary"
						ng-click="joinLobby()">Join</button>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		const UUID = '${UUID}';
		const USERNAME = '${Username}';
	</script>
</html>
