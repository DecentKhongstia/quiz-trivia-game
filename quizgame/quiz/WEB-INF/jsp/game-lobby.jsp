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
					<form id="lobbyForm">
						<div class="form-group row" ng-if="start == null">
							The Game will start after some people have join.
						</div>
						<div class="form-group row" ng-if="start != null">
							The Game will start after {{remainningtime}} seconds.
						</div>
						<div class="form-group row" ng-repeat="U in Users">
							<label class="col">{{($index+1)+':  '+U+'  '}} - have joined the game</label>
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
