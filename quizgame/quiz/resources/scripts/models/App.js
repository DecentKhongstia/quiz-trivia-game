/**
 * 
 */

const URL_JOIN_GAME_SCREEN = "join-game-screen";
const URL_LOGIN_SCREEN = "login";
const URL_REGISTER_SCREEN = "register";
const URL_LOBBY_SCREEN = "game-lobby";
const URL_EXIT_GAME_SCREEN = "game-exit";
const URL_QUESTION_SCREEN = "game-question";
const URL_RESULT_SCREEN = "game-result";
const URL_END_SCREEN = "game-end";

const LOBBY_WAITINGTIME = 60;
const QUESTIONS_WAITINGTIME = 10;
const NO_OF_QUESTIONS = 3;
const POINT_PER_QUESTION = 10;

const JOINLOBBY = '/app/join-lobby';
const GETLOBBYID = '/app/get-lobbyID';
const GETQUESTION = '/app/get-question';
const GETANSWER = '/app/get-answer';


const HttpSuccessCode = [ 200, 201, 202, 203 ];

var User = function() {
	var User = {
		username : '',
		password : '',
		uuid: '',
		lobbyID: '',
	};

	return User;
};


