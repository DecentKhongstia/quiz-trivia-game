/**
 * 
 */

angularApp.controller('questionController',["$scope", "$timeout", "$window", "AppService", function($scope, $timeout, $window, AppService) {

	$scope.user = new User();
	$scope.user.username = USERNAME;
	$scope.Questions = [];
	$scope.result = {
			lobbyID:'',
			username:USERNAME,
			tally: null,
			answers:[]
	};
	
	$scope.Answers = [];
	$scope.timer = QUESTIONS_WAITINGTIME;
	var count = 0;
	var lobbyID = localStorage.getItem("lobbyID");
	var sequenceId = null;
	var topicGetQuestion = null;
	var topicAnswer = null;
	var started = localStorage.getItem("started");
	$scope.user.lobbyID = lobbyID;
	$scope.user.uuid = UUID; 
	
	$scope.connect = () => {
		var socket = new SockJS('chat2');
        stompClient = Stomp.over(socket);
        topicGetQuestion = `/user/${UUID}/queue/lobby-questions`;
        	
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            $scope.setConnected(true);
            stompClient.subscribe(topicGetQuestion, function(messageOutput) {
            	console.log(angular.fromJson(messageOutput.body));
            	let qs = angular.fromJson(messageOutput.body);
            	let exist = false;
            	$timeout(() => {
            		if(qs != null && qs.code != null){
            			$scope.Questions.forEach((o, x) => {
            				if(o.code == qs.code)
            					exist = true;
            			})
            			if(!exist)
            				$scope.Questions.push(angular.fromJson(messageOutput.body));
            		}
            		
            		
            		if(!started){
            			started = true;
            			localStorage.setItem("started", started);
            			$scope.startCountdown();
            		}
            	}, 0)
			});
        });
	}
	
	$scope.setConnected= (flag) => {
		if(flag){
			$scope.getQuestion(null);
		}
	};
	
	$scope.getQuestion = (code) => {
		if(localStorage.getItem("sequenceId") != null){
			sequenceId = localStorage.getItem("sequenceId");
		}
		sequenceId++;
		localStorage.setItem("sequenceId", sequenceId);
		stompClient.send(GETQUESTION, {}, JSON.stringify({'code':code, 'lobbyID': lobbyID, 'sequenceId': sequenceId, 'uuid': UUID}));
	}
	
	var startTimer;
	$scope.startCountdown = () => {
		$timeout(() => {
			let len = $scope.Questions.length;
			let code = $scope.Questions[len-1].code;
			startTimer = setTimeout($scope.startCountdown, 1000);
			if($scope.timer < 0){
				$timeout(() => {
					$scope.Questions[len-1].display = true;
					count++;
					$scope.timer = QUESTIONS_WAITINGTIME;
				}, 300)
				$scope.getQuestion(code);
			}else if($scope.timer >= 0){
				/*console.log(`Next question in ${$scope.timer} seconds`);*/
			}
			if(count >= NO_OF_QUESTIONS){
				clearTimeout(startTimer);
				$scope.displayResult();
			}
			$scope.timer--;
		}, 0)
	}
	
	$scope.displayResult = () => {
		let tally = 0;
		try{
			console.log("Questions: ",($scope.Questions));
			$scope.Questions.forEach( (o, x) => {
				let ans = {};
				o.answers.forEach( (a, x) => {
					ans.code = o.code;
					if(a.code != null && a.code != ''){
						if(a.response != null){
							ans.response = a.options;
							if(a.options == a.response){
								tally += 10;
							}
							return;
						}
					}
				})
				if(ans != null && ans.code != null && ans.code != ""){
					if(ans.response == null || ans.response == "")
						ans.response = "N";
					$scope.result.answers.push(ans);
				}
			});
			$scope.result.tally = tally;
			$scope.result.lobbyID = lobbyID;
			AppService.post('save-result', angular.toJson($scope.result), (response) => {
				 if(response)
					 $window.location.href = URL_RESULT_SCREEN;
			}, error => {
				 $window.location.href = URL_RESULT_SCREEN;
			});
		}catch (e) {
		}
	};
	
	$scope.leaveLobby = () => {
		AppService.leaveLobby();
	}
}]);