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
		<div class="container center-align-content mt-5 full-scale-width-50" style="display: block;">
			<div class="card">
				<div class="card-body">
					<div class="form-group center-align-content mb-5" ng-if="winner != null">
						<div ng-if="winner.length > 1">The winners are {{winner}}</div>
						<div ng-if="winner.length == 1">The winner is {{winner}}</div>
					</div>
					<div class="form-group row" ng-if="Results.winner != null">
						<label class="col-sm-4 text-right">Slno</label>
						<label class="col">Username</label>
						<label class="col-sm-4">Score</label>
					</div>
					<div class="form-group row" ng-repeat="W in Results.winner| orderBy: {tally}">
						<label class="col-sm-4 text-right">{{$index+1+' - '}}</label>
						<label class="col">{{W.username}}</label>
						<label class="col-sm-4 text-left">{{W.tally}}</label>
					</div>
					<div class="form-group center-align-content">
						<a href="game-screen"><button type="button" class="btn btn-outline-primary">New Game</button></a>
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
