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
		<div class="container center-align-content full-scale-width-80">
			<div class="card">
				<div class="card-header">
					<h1 class="text-center">Welcome To Our Multiplayer Trivia Game</h1>
					<h6 class="text-right">Logged in as ${Username}</h6>
				</div>
				<div class="card-body">
					<div class="card-title"><p class="text-justify">Please read the instructions before proceeding further</p></div>
					<div class="form-group">
						<ul>
							<li>The Game will start after a total of 10 players enter the room or 1 min after 2 players minimum are in a room.</li>
							<li>Each player will be given a set of 3 Multiple Choice Questions.</li>
							<li>One Multiple Choice Question will be displayed at a time and after 10 seconds a new question will appear.</li>
							<li>A player has to select one of the options.</li>
							<li>Each player is awarded 10 points for the correct options.</li>
							<li>There are no negative points. Feel free to select any of your guts choice.</li>
							<li>After all questions are revealed. The correct answer will be displayed and the winner will be announced.</li>
							<li>Do not refresh the browser otherwise you will be exited from the game.</li>
						</ul>
					</div>
					<div class="form-group row">
						<div class="col-sm-6 center-align-content">
							<button type="button" class="btn btn-outline-primary"
								ng-click="joinLobby()">Join</button>
						</div>
						<div class="col-sm-6 center-align-content">
							<button type="button" class="btn btn-outline-danger"
								ng-click="exitGame()">Exit</button>
						</div>
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
				