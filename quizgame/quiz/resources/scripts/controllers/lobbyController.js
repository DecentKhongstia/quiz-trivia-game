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
	
	$scope.topic = '/user/'+lobbyID+'/queue/lobby-details'
	
	$scope.connect = () => {
		var socket = new SockJS('chat2');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            $scope.setConnected(true);
            stompClient.subscribe(topicGetLobbyID, function(messageOutput) {
				lobbyID = messageOutput.body;
                localStorage.setItem('lobbyID', lobbyID);
                if(lobbyID != null)
                	$scope.joinLobby();
			});
            
        });
        
	}
	
	$scope.setConnected= (flag) => {
		if(flag){
			stompClient.send(GETLOBBYID, {}, JSON.stringify({'from':UUID, 'text':'', 'to': ''}));
		}
	};
	
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

	$scope.startCountdown = () => {
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
	
	/*$scope.displayResult = () => {
		
	};
	
	$scope.displayQuestions = () => {
		console.log("displayQuestions");
		let no = null;
		$timeout(() => {
			if($scope.Questions != null){
				no = $scope.Questions.length;
				$scope.Questions.forEach((o, x) => {
					o.display = false
				})
				$scope.Questions[0].display = true;
				$scope.nextQuestions(no);
			}
		}, 0);
	}
	
	$scope.nextQuestions = (n) => {
		let t = 10;
		let i = 1;
		var startCountdown = setInterval( () => {
			
			if(t == 0 && i<n){
				$timeout( () => {
					console.log("inside: i="+i);
					$scope.Questions[i-1].display = false;
					$scope.Questions[i].display = true;
					i++;
					if(i == n)
						clearInterval(startCountdown);
				}, 0)
				console.log("Next question will appear now: ", $scope.Questions);
			}else
				console.log(`Next question will appear in ${t} seconds..`);
			
			if(t <= 0){
				if($scope.Questions[(n-1)].display == true)
					clearInterval(startCountdown);
				else
					t = 11;
			}
			t--;
		}, 1000)
	}*/
	
}]);