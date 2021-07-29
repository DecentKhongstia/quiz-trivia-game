<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<%@include file="common/headerfiles.jsp"%>
		<script src="resources/scripts/controllers/endController.js"></script>
	</head>
	<body ng-app='angularApp' ng-controller="endController"
		ng-init="connect()">
		<div class="container center-align-content full-scale">
			<div class="card">
				<div class="card-body">
					<form id="lobbyForm">
						<div class="form-group center-align-content mb-5" ng-if="winner != null">
							The winner is {{winner}} with a score of {{max}} each.
						</div>
						<div class="form-group center-align-content">
							<a href="game-screen"><button type="button" class="btn btn-outline-primary">New Game</button></a>
						</div>
					</form>
				</div>			
			</div>
		</div>
	</body>
	<script type="text/javascript">
		const UUID = '${UUID}';
		const USERNAME = '${Username}';
	</script>
</html>
