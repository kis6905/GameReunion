package com.is.gr.controller.game;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.gr.service.FindBoardService;
import com.is.gr.service.GameServerService;
import com.is.gr.vo.FindBoard;
import com.is.gr.vo.Game;
import com.is.gr.vo.GameServer;

@Controller
@RequestMapping(value = "/game/find/**")
public class FindBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(FindBoardController.class);
	
	@Autowired
	private FindBoardService findBoardServiceImpl;
	@Autowired
	private GameServerService gameServerServiceImpl;
	
	/**
	 * 친구 찾기 게시판 화면 이동
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		
		logger.info("-> [gameCode = {}]", gameCode);
		
		List<GameServer> gameServerList = gameServerServiceImpl.getGameServerList(gameCode);
		model.addAttribute("gameServerList", gameServerList);
		
		logger.info("<- [gameServerListSize = {}]", gameServerList.size());
		return "game/find_board_list";
	}
	
	/**
	 * 친구 찾기 게시판 목록 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postList(
			@RequestBody String body,
			HttpServletRequest request) {
		
		logger.info("-> [body = {}]", body);
		
		JSONObject jsonResult = new JSONObject();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject requestJson = (JSONObject) jsonParser.parse(body);
			
			HttpSession session = request.getSession(false);
			String gameCode = ((Game) session.getAttribute("game")).getGameCode();
			String serverName = (String) requestJson.get("serverName");
			String search = (String) requestJson.get("search");
			long offset = (long) requestJson.get("offset");
			long limit = (long) requestJson.get("limit");
			
			JSONArray rows = findBoardServiceImpl.getFindBoardListToJsonArray(gameCode, serverName, search, offset, limit);
			jsonResult.put("rows", rows);
			int total = findBoardServiceImpl.getFindBoardListCount(gameCode, serverName, search);
			jsonResult.put("total", total);
			
			logger.info("<- [total = {}], [rows = {}]", total, rows.size());
		} catch (ParseException e) {
			logger.error("~~ [An error occurred]", e);
		}
		return jsonResult.toString();
	}
	
	/**
	 * 친구 찾기 게시판 작성
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	@ResponseBody
	public String postWrite(
			@RequestParam(value = "serverName", required = true) String serverName,
			@RequestParam(value = "contents", required = true) String contents,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		
		logger.info("-> [serverName = {}], [contents = {}], [memberId = {}], [gameCode = {}]", new Object[] { serverName, contents, memberId, gameCode });
		
		JSONObject jsonResult = new JSONObject();
		boolean result = false;
		
		if (findBoardServiceImpl.insertFindBoard(new FindBoard(gameCode, serverName, memberId, contents)))
			result = true;
		
		jsonResult.put("result", result);
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 친구 찾기 게시판 수정
	 * 지금은 수정 기능 없음. 추후 넣게되면 쓰이게 됨
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String postUpdate(
			@RequestParam(value = "no", required = true) Integer no,
			@RequestParam(value = "serverName", required = true) String serverName,
			@RequestParam(value = "contents", required = true) String contents,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		
		logger.info("-> [no = {}], [serverName = {}], [contents = {}], [memberId = {}], [gameCode = {}]", new Object[] { no, serverName, contents, memberId, gameCode });
		
		JSONObject jsonResult = new JSONObject();
		boolean result = false;
		
		if (findBoardServiceImpl.updateFindBoard(new FindBoard(no, gameCode, serverName, memberId, contents)))
			result = true;
		
		jsonResult.put("result", result);
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 친구 찾기 게시판 삭제
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String postDelete(
			@RequestParam(value = "no", required = true) Integer no,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [no = {}], [memberId = {}]", no, memberId);
		
		JSONObject jsonResult = new JSONObject();
		boolean result = false;
		
		if (findBoardServiceImpl.deleteFindBoard(new FindBoard(no, memberId)))
			result = true;
		
		jsonResult.put("result", result);
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
