package com.decent.quiz.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.decent.quiz.models.Answer;
import com.decent.quiz.models.GameResponse;
import com.decent.quiz.models.MCAs;
import com.decent.quiz.models.MCQs;
import com.decent.quiz.models.Result;
import com.decent.quiz.models.UserInfo;
import com.decent.quiz.models.Winner;

@Repository
public class MainDao {

	private static final Logger LOG = Logger.getLogger(MainDao.class.getName());

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean boilerPlate(HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "";
			Object[] args = new Object[] {};
			status = jdbcTemplate.update(sql, args) > 0;
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("msg", "Internal server error. Please sorry for the inconvenience.");
		}
		return status;
	}

	public boolean isExist(UserInfo user, HashMap<String, Object> response) {
		LOG.info("isExist");
		boolean status = false;
		try {
			String sql = "SELECT username FROM quiz.userinfos WHERE username = ?";
			status = jdbcTemplate.queryForRowSet(sql, user.getUsername()).first();
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("msg", "Internal server error. Please sorry for the inconvenience.");
			e.printStackTrace();
		}
		return status;
	}

	public boolean isRegistered(UserInfo user, HashMap<String, Object> response) {
		LOG.info("isRegistered");
		boolean status = false;
		try {
			String uuid = "";
			String sql = "SELECT usercode as uuid FROM quiz.userinfos WHERE username = ? AND userpassword = ?";
			Object[] args = new Object[] { user.getUsername(), user.getPassword() };
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, args);
			status = rowSet.first();
			uuid = rowSet.getString("uuid");
			user.setUuid(uuid);
			user.setPassword("");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("msg", "Internal server error. Please sorry for the inconvenience.");
			e.printStackTrace();
		}
		return status;
	}

	public boolean registerUser(UserInfo user, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "INSERT INTO quiz.userinfos(" + "            usercode, username, userpassword, entrydate) "
					+ "    VALUES (?, ?, ?, now())";
			Object[] args = new Object[] { UUID.randomUUID().toString(), user.getUsername(), user.getPassword() };
			status = jdbcTemplate.update(sql, args) > 0;
			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Registered successfully");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Internal server error. Please sorry for the inconvenience.");
			e.printStackTrace();
		}
		return status;
	}

	public MCQs getMCQs(Integer code) {
		MCQs qs = new MCQs();
		try {
			List<MCQs> questions = new ArrayList<MCQs>();
			List<MCAs> answers = new ArrayList<MCAs>();

			String sql = "";

			Object[] args = new Object[] { code };

			if (code != null) {
				sql = "SELECT code, description FROM quiz.questions WHERE code = ?";
				questions = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(MCQs.class));
			} else {
				sql = "SELECT code, description FROM quiz.questions";
				questions = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MCQs.class));
			}
			if (questions != null && !questions.isEmpty())
				qs = questions.get(0);

			if (qs != null && qs.getCode() != null) {
				sql = "SELECT code, qcode, description, flag as options FROM quiz.answers WHERE qcode = ?";
				args = new Object[] { qs.getCode() };
				answers = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(MCAs.class));
				if (answers != null && !answers.isEmpty())
					qs.setAnswers(answers);
			}
			return qs;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qs;
	}

	public MCQs getMCQs(Set<Integer> codes) {
		LOG.info("getMCQs");

		MCQs qs = new MCQs();
		try {

			MCAs as = new MCAs();
			List<MCQs> questions = new ArrayList<MCQs>();
			List<MCAs> answers = new ArrayList<MCAs>();

			String sql = "";

			Object[] args;
			List<Object> params = new ArrayList<Object>();

			if (codes != null && !codes.isEmpty()) {
				sql = "SELECT code, description FROM quiz.questions WHERE code NOT IN (";
				for (Integer i : codes) {
					sql = sql.concat("?,");
					params.add(i);
				}
				sql = sql.substring(0, sql.length() - 1).concat(")");
				questions = jdbcTemplate.query(sql, params.toArray(), BeanPropertyRowMapper.newInstance(MCQs.class));
			} else {
				sql = "SELECT code, description FROM quiz.questions";
				questions = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MCQs.class));
			}
			if (questions != null && !questions.isEmpty())
				qs = questions.get(0);

			if (qs != null && qs.getCode() != null) {
				sql = "SELECT code, qcode, description, flag as options FROM quiz.answers WHERE qcode = ?";
				args = new Object[] { qs.getCode() };
				answers = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(MCAs.class));
				if (answers != null && !answers.isEmpty())
					qs.setAnswers(answers);
			}
			return qs;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qs;
	}

	public List<MCQs> listMCQs() {
		LOG.info("getMCQs");
		try {
			List<MCQs> questions = new ArrayList<MCQs>();

			String sql = "";
			sql = "SELECT code, description FROM quiz.questions";
			questions = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MCQs.class));
			if (questions != null && !questions.isEmpty()) {
				sql = "SELECT code, qcode, description, flag FROM quiz.answers WHERE qcode = ?";
				for (MCQs q : questions) {
					Object[] params = new Object[] { q.getCode() };
					List<MCAs> answers = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(MCAs.class));
					if (answers != null && !answers.isEmpty())
						q.setAnswers(answers);
				}
			}
			questions.forEach(System.out::println);
			return questions;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<MCQs>();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean saveResult(UserInfo user, Result result, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "SELECT COALESCE(MAX(code), 0)+1 FROM quiz.gameanswers";
			Integer qcode = jdbcTemplate.queryForObject(sql, Integer.class);

			sql = "INSERT INTO quiz.gameanswers(  " + "            code, username, tally, lobbyid, flag, entrydate)  "
					+ "    VALUES (?, " + "		?, ?, ?, ?, now()) ";
			Object[] args = new Object[] { qcode, user.getUsername(), result.getTally(), result.getLobbyID(), "Y" };
			status = jdbcTemplate.update(sql, args) > 0;
			if (!status)
				throw new Exception();

			sql = "INSERT INTO quiz.gameanswersresponse(  " + "            id, code, qcode, response, entrydate)  "
					+ "VALUES ((SELECT COALESCE(MAX(id), 0)+1 FROM quiz.gameanswersresponse), ?, ?, ?, now())  ";
			List<Object[]> params = new ArrayList<Object[]>();
			for (Answer ans : result.getAnswers()) {
				params.add(new Object[] { qcode, ans.getCode(), ans.getResponse() });
			}
			status = jdbcTemplate.batchUpdate(sql, params).length == params.size();

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Saved successful.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("msg", "Internal server error. Please sorry for the inconvenience.");
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getResultAnswers(Result result, HashMap<String, Object> response) {
		try {
			JSONObject json = new JSONObject();
			Map<String, Object> results = new HashMap<String, Object>();
			List<GameResponse> responses = new ArrayList<GameResponse>();
			String sql = "SELECT DISTINCT(Q.code), Q.description AS question, A.description AS answer, AR.response   "
					+ "FROM quiz.gameanswersresponse AR   " + "INNER JOIN quiz.gameanswers GA ON GA.code = AR.code   "
					+ "INNER JOIN quiz.questions Q ON Q.code = AR.qcode    "
					+ "INNER JOIN quiz.answers A ON A.qcode = Q.code AND A.flag = 'Y'   "
					+ "WHERE GA.username = ? AND GA.lobbyid = ?";
			Object[] args = new Object[] { result.getUsername(), result.getLobbyID() };
			responses = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(GameResponse.class));
			json.put("answers", responses);

			sql = "SELECT username, tally   " + "FROM quiz.gameanswers GA    "
					+ "WHERE GA.username = ? AND GA.lobbyid = ?";
			results = jdbcTemplate.queryForMap(sql, args);
			json.put("result", results);
			response.put("code", HttpStatus.OK.value());
			response.put("data", json);
			return json;
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("data", new JSONObject());
			e.printStackTrace();
		}
		return new JSONObject();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getResultWinner(String lobbyID, HashMap<String, Object> response) {
		try {
			JSONObject json = new JSONObject();
			Map<String, Object> results = new HashMap<String, Object>();
			List<Winner> responses = new ArrayList();
			String sql = "SELECT username, tally   " + "FROM quiz.gameanswers GA   " + "WHERE (lobbyid, tally) = (  "
					+ "	SELECT lobbyid, MAX(tally)  " + "	FROM quiz.gameanswers GA   " + "	WHERE GA.lobbyid = ?  "
					+ "	GROUP BY lobbyid  " + ")";

			sql = "SELECT username, tally   " + "FROM quiz.gameanswers GA   " + "WHERE GA.lobbyid = ?  ";

			Object[] args = new Object[] { lobbyID };
			responses = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(Winner.class));
			json.put("winner", responses);
			response.put("code", HttpStatus.OK.value());
			response.put("data", json);
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("data", new JSONObject());
			e.printStackTrace();
		}
	}
}
