package com.decent.quiz.controllers;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.decent.quiz.Constants;
import com.decent.quiz.models.Result;
import com.decent.quiz.models.UserInfo;
import com.decent.quiz.services.MainService;

@Controller
@SessionAttributes({ "SessionUser", "UUID", "Username" })
public class MainController {

	private static final Logger LOG = Logger.getLogger(MainController.class.getName());

	private @Autowired MainService MS;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	public void setUserSession(ModelMap model, UserInfo user) {
		model.addAttribute(Constants.SESSION_USER, user);
		model.addAttribute(Constants.USER_UUID, user.getUuid());
		model.addAttribute(Constants.USERNAME, user.getUsername());
	}

	@RequestMapping(value = { "/", "/index.htm" })
	public String index() {
		LOG.info("INDEX");
		return "index";
	}

	@GetMapping(value = "/login")
	public String loginScreen(ModelMap model, SessionStatus sessionStatus) {
		MS.removeUser((UserInfo) session().getAttribute(Constants.SESSION_USER));
		sessionStatus.setComplete();
		session().invalidate();
		return "login";
	}

	@GetMapping(value = "/register")
	public String registerScreen() {
		return "register";
	}

	@GetMapping(value = "/join-game-screen")
	public String gameJoinScreen(ModelMap model) {
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		LOG.info("MS.isUserLogin(user): " + MS.isUserLogin(user));
		if (MS.isUserLogin(user)) {
			return "game-screen";
		} else
			return "redirect:login";
	}

	@GetMapping(value = "/game-end")
	public String gameExitScreen(ModelMap model) {
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		LOG.info("MS.isUserLogin(user): " + MS.isUserLogin(user));
		if (MS.isUserLogin(user)) {
			MS.removeUser((UserInfo) session().getAttribute(Constants.SESSION_USER));
			return "game-end";
		} else
			return "redirect:login";
	}

	@GetMapping(value = "/game-lobby")
	public String gameLobbyScreen(ModelMap model) {
		LOG.info("GamelobbyScreen");
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		if (MS.isUserLogin(user)) {
			return "game-lobby";
		} else
			return "redirect:login";
	}

	@GetMapping(value = "/game-question")
	public String gameQuestionScreen(ModelMap model) {
		LOG.info("GameQuestionScreen");
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		if (MS.isUserLogin(user)) {
			if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			}
			return "game-question";
		} else
			return "redirect:login";
	}

	@GetMapping(value = "/game-result")
	public String gameResultScreen(ModelMap model) {
		LOG.info("GameResultScreen");
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		if (MS.isUserLogin(user)) {
			MS.removeUser(user);
			return "game-result";
		} else
			return "redirect:login";
	}

	@PostMapping("login")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> login(ModelMap model, @RequestBody UserInfo user) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (MS.isRegistered(user, response)) {
			session().setAttribute(Constants.SESSION_USER, user);
			setUserSession(model, user);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("register")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> register(ModelMap model, @RequestBody UserInfo user) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (MS.register(user, response)) {
			setUserSession(model, user);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("save-result")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> saveResult(ModelMap model, @RequestBody Result result) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		UserInfo user = (UserInfo) session().getAttribute(Constants.SESSION_USER);
		if (MS.saveResult(user, result, response)) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@PostMapping("get-answers")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> getResultAnswers(ModelMap model, @RequestBody Result result) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		MS.getResultAnswers(result, response);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "get-winner/{lobbyID}")
	public ResponseEntity<HashMap<String, Object>> gameWinner(ModelMap model, @PathVariable String lobbyID) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		MS.getResultWinner(lobbyID, response);
		return ResponseEntity.ok(response);
	}


}
