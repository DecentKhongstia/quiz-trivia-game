<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<%@include file="common/headerfiles.jsp"%>
		<script src="resources/scripts/controllers/resultController.js"></script>
	</head>
	<body ng-app='angularApp' ng-controller="resultController"
		ng-init="connect()">
		<div class="container center-align-content" style="display: block; margin-top: 20%">
			<div class="card">
				<div class="card-body">
					<form id="lobbyForm">
						<div class="form-group center-align-content mb-5" ng-if="Results.result.username != null">
							You score {{Results.result.tally}} out of {{total}}.
						</div>
						<div class="form-group row">
							<label class="col-sm-1 text-float-right">Sl</label>
							<label class="col-sm-5">Question</label>
							<label class="col-sm-3">Right Answer</label>
							<label class="col-sm-3">You answer it</label>
						</div>
						<div class="form-group row" ng-repeat="R in Answers">
							<label class="col-sm-1">{{$index+1}}</label>
							<label class="col-sm-5">{{R.question}}</label>
							<label class="col-sm-3">{{R.answer}}</label>
							<label class="col-sm-3">{{(R.response == 'Y'?'Correct': 'Wrong')}}</label>
						</div>
						<div class="form-group">
							<a href="game-end"><button type="button" class="btn btn-outline-primary">See Winner</button></a>
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
