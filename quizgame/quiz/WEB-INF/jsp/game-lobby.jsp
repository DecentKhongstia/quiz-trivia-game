<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="common/headerfiles.jsp"%>
<script src="resources/scripts/controllers/lobbyController.js"></script>
</head>
<body ng-app='angularApp' ng-controller="lobbyController"
	ng-init="connect()">
	<div class="container center-align-content mt-5">
		<div class="card">
			<div class="card-body">
				<div class="card-title" ng-if="start == null">
					<h5>Please wait for others to join in.</h5>
				</div>
				<div class="card-title" ng-if="start != null">
					The Game will begin after {{remainningtime}} seconds.
				</div>
				<div class="form-group row" ng-repeat="U in Users">
					<label class="col">{{($index+1)+': '+U+' '}} - have joined
						the game</label>
				</div>
				<div class="form-group center-align-content">
					<button type="button" class="btn btn-outline-primary"
						ng-click="leaveLobby()">Leave</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	const UUID = '${UUID}';
	const USERNAME = '${Username}';
</script>
</html>
