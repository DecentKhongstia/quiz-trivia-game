/**
 * 
 */

function AppService($http, $window) {

	var app = this;
	
	/* HTTP */
	app.get = (url, callback) => {
		$http.get(url).then((response) => {
			callback(response.data);
		}, (error) => {
		})
	};
	
	app.post = (url, data, callBack, errCallBack) => {
		$http.post(url, data).then((response) => {
			console.log("response.data.code : ", response.data.code);
			if(HttpSuccessCode.indexOf(response.data.code) >= 0)
				callBack(response)
			else
				errCallBack(response);
		}, (error) => {
			errCallBack(error);
		});
	};
	
	app.clearGameSession = () => {
		sessionStorage.clear();
		localStorage.clear();
	}
	
	app.joinLobby = () => {
		let flag = confirm("Press OK to PLAY\nElse Cancel");
		if(flag)
			$window.location.href = URL_LOBBY_SCREEN;
	}
	app.leaveLobby = () => {
		let flag = confirm("Press OK to Leave.\nElse Cancel");
		if(flag)
			$window.location.href = URL_GAME_SCREEN;
	}

	app.exitGame = () => {
		let flag = confirm("Press OK to Exit.\nElse Cancel");
		if(flag)
			$window.location.href = URL_LOGIN_SCREEN;
	}
	
	app.getCurrentTime = () => {
		var time = new Date();
		var year = time.getFullYear();
		var month = time.getMonth()+1;
		var date1 = time.getDate();
		var hour = time.getHours();
		var minutes = time.getMinutes();
		var seconds = time.getSeconds();
		var t = {};
		t.hour = hour;
		t.min = minutes;
		t.sec = seconds;
		t.time = `${hour}:${minutes}:${seconds}`;
		return t;
	}
	
	app.getWaitingTime = (start, end) => {
		console.log("start: ", start," end: ",end);
		var startTime=moment(start, "HH:mm:ss");
		var endTime=moment(end, "HH:mm:ss");
		var duration = moment.duration(endTime.diff(startTime));
		var hours = parseInt(duration.asHours());
		var minutes = parseInt(duration.asMinutes())-hours*60;
        var result = endTime.diff(startTime, 'seconds');
        return parseInt(result);
	}
}

angularApp.service('AppService', AppService);