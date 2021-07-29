<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="common/headerfiles.jsp"%>
<script src="resources/scripts/controllers/questionController.js"></script>
</head>
<body ng-app='angularApp' ng-controller="questionController"
	ng-init="connect()">
	<div class="container center-align-content margin-top">
		<form id="registerForm">
			<div class="form-group">
				<h6>Next questions will appear in {{timer}} seconds</h6>
			</div>
			<div class="form-group row"
				ng-repeat="Q in Questions | filter:{ display: false}">
				<label for="to" class="col-sm-12">{{Q.description}}</label>
				<div class="col-sm-12 px-5">
					<div class="row" ng-repeat="A in Q.answers">
						<label class="col" for="response_{{Q.code}}">{{($index+1)+': '+A.description}}</label>
						<div class="col">
							<input type="radio" id="response_{{Q.code}}"
								name="response_{{Q.code}}" ng-model="A.response" value="Y">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	const UUID = '${UUID}';
	const USERNAME = '${Username}';
</script>
</html>
