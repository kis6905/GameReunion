package com.is.gr.controller.game;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.gr.service.GameService;
import com.is.gr.vo.Game;

@Controller
@RequestMapping(value = "/game/**")
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameServiceImpl;
	
	/**
	 * 게임 리스트 화면 이동
	 * 검색하는 경우면 해당하는 리스트만 리턴
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(
			@RequestParam(value = "search", required = false) String search, 
			Model model) {
		
		logger.info("-> [search = {}]", search);
		
		List<Game> gameList = gameServiceImpl.getSearchGameList(search);
		model.addAttribute("gameList", gameList);
		model.addAttribute("search", search);
		
		logger.info("<- [gameListSize = {}]", gameList.size());
		return "game/game_list";
	}
	
	/**
	 * 게임 상세 화면 이동
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getDetail(
			@RequestParam(value = "gameCode", required = true) String gameCode,
			HttpServletRequest request,
			Model model) {
		
		logger.info("-> [gameCode = {}]", gameCode);
		
		// 게임 정보는 상세에 들어온 후 쓰이는 곳이 많기 때문에 session에 넣어준다.
		Game game = gameServiceImpl.getGameDetail(gameCode);
		HttpSession session = request.getSession(false);
		session.setAttribute("game", game);
		
		String memberId = (String) request.getSession(false).getAttribute("MEMBER_ID");
		boolean isBookmark = gameServiceImpl.isBookmark(memberId, gameCode);
		model.addAttribute("isBookmark", isBookmark);
		
		logger.info("<- [game = {}], [isBookmark = {}]", game.toString(), isBookmark);
		return "game/game_detail";
	}
	
	/**
	 * 북마크 추가 or 삭제
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changebookmark", method = RequestMethod.POST)
	@ResponseBody
	public String getChangeBookmark(
			@RequestParam(value = "flag", required = true) String flag, // "add" or "remove" 
			HttpServletRequest request) {
		
		logger.info("-> [flag = {}]", flag);
		
		JSONObject jsonResult = new JSONObject();
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		boolean result = gameServiceImpl.changeBookmark(memberId, gameCode, flag);
		jsonResult.put("result", result);
		
		logger.info("<- [resultJson = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
