/**
 * 
 */

angularApp.controller('lobbyController',["$scope", "$timeout", "$window", "AppService", function($scope, $timeout, $window, AppService) {

	$scope.user = new User();
	$scope.Questions = [];
	$scope.Q = [{'hello':1}];
	$scope.Users = [];
	$scope.start = null;
	$scope.remainningtime = null;

	var topicGetLobbyID = '/user/'+UUID+'/queue/lobbyID';
	var topicJoinLobby = null;
	var stompClient = null;
	var lobbyID = localStorage.getItem("lobbyID");
	var timeToStart = null;
	var interact = null;
	var isJoinedLobby = null;
	var countdownStarted = null;
	
	$scope.topic = '/user/'+lobbyID+'/queue/lobby-details'
	
	$scope.connect = () => {
		var socket = new SockJS('chat2');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            localStorage.setItem("countdownStarted", 0);
            $scope.setConnected(true);
            stompClient.subscribe(topicGetLobbyID, function(messageOutput) {
				lobbyID = messageOutput.body;
                localStorage.setItem('lobbyID', lobbyID);
                if(lobbyID != null)
                	$scope.joinLobby();
			});
            
        });
        
	}
	
	$scope.joinLobby = () => {
		try{
			$scope.user.username = USERNAME;
			if(lobbyID == null )
				lobbyID = localStorage.getItem('lobbyID');
			if(isJoinedLobby == null && lobbyID != null){
				localStorage.setItem("isJoinedLobby",true);
				$scope.user.lobbyID = lobbyID;
				topicJoinLobby = '/user/'+lobbyID+'/queue/lobby-details';
				stompClient.subscribe(topicJoinLobby, function(messageOutput) {
					var room = angular.fromJson(messageOutput.body);
					var end = AppService.getCurrentTime().time;
					$timeout(() => {
						room.users.forEach((o, x) => {
							if(!$scope.Users.includes(o.username))
								$scope.Users.push(o.username);
						})
						$scope.start = room.time;
						if($scope.start != null){
							timeToStart = AppService.getWaitingTime($scope.start, end)
							$scope.startCountdown();
						}
					}, 0)
				});
				stompClient.send(JOINLOBBY, {}, angular.toJson($scope.user));
			}
		}catch (e) {
			console.log(e);
		}
	};
	
	$scope.leaveLobby = () => {
		AppService.leaveLobby();
	}

	$scope.setConnected= (flag) => {
		if(flag){
			stompClient.send(GETLOBBYID, {}, JSON.stringify({'from':UUID, 'text':'', 'to': ''}));
		}
	};
	
	$scope.startCountdown = () => {
		countdownStarted = localStorage.getItem("countdownStarted");
		if(countdownStarted == 0){
			localStorage.setItem("countdownStarted", 1);
			if(timeToStart != null && timeToStart < LOBBY_WAITINGTIME){
				let t = timeToStart;
				$scope.remainningtime = LOBBY_WAITINGTIME - t;
				var startCountdown = setInterval(() => {
					if($scope.remainningtime <= 0){
						clearInterval(startCountdown);
						$window.location.href = URL_QUESTION_SCREEN;
					}
					if($scope.remainningtime == 0){
						console.log(`Game will start in now`);
					} else{
						/*console.log(`Game will start in ${$scope.remainningtime} seconds..`);*/
					}
					$timeout(() => {
						$scope.remainningtime--;
					},0);
					
				}, 1000);
			} else
				$window.location.href = URL_QUESTION_SCREEN;
		}
	}
}]);