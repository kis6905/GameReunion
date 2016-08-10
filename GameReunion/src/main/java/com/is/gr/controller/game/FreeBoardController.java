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

import com.is.gr.service.FreeBoardService;
import com.is.gr.vo.FreeBoard;
import com.is.gr.vo.FreeBoardComment;
import com.is.gr.vo.Game;

@Controller
@RequestMapping(value = "/game/free/**")
public class FreeBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(FreeBoardController.class);
	
	@Autowired
	private FreeBoardService freeBoardServiceImpl;
	
	/**
	 * 자유게시판 화면 이동
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(
			@RequestParam(value = "isBack", required = false) Boolean isBack,
			Model model) {
		
		logger.info("-> [isBack = {}]", isBack);
		
		model.addAttribute("isBack", isBack == null ? false : isBack);
		
		logger.info("<- []");
		return "game/free_board_list";
	}
	
	/**
	 * 자유게시판 목록 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postList(
			@RequestBody String body,
			HttpServletRequest request) {
		
		logger.info("-> [body = {}]", body);
		
		JSONObject jsonResult = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		
		try {
			JSONObject requestJson = (JSONObject) jsonParser.parse(body);
			
			HttpSession session = request.getSession(false);
			String gameCode = ((Game) session.getAttribute("game")).getGameCode();
			String search = (String) requestJson.get("search");
			long offset = (long) requestJson.get("offset");
			long limit = (long) requestJson.get("limit");
			
			JSONArray rows = freeBoardServiceImpl.getFreeBoardListToJsonArray(gameCode, search, offset, limit);
			jsonResult.put("rows", rows);
			int total = freeBoardServiceImpl.getFreeBoardListCount(gameCode, search);
			jsonResult.put("total", total);
			
			logger.info("<- [total = {}], [rows = {}]", total, rows.size());
		} catch (ParseException e) {
			logger.error("~~ [An error occurred]", e);
		}
		return jsonResult.toString();
	}
	
	/**
	 * 자유게시판 작성 화면 이동
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String getWrite() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "game/free_board_write";
	}
	
	/**
	 * 자유게시판 작성
	 */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "contents", required = true) String contents,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		
		logger.info("-> [gameCode = {}], [title = {}], [contents = {}]", new Object[] { gameCode, title, contents });
		
		FreeBoard freeBoard = new FreeBoard(gameCode, (String) session.getAttribute("MEMBER_ID"), title, contents);
		
		if (!freeBoardServiceImpl.insertFreeBoard(freeBoard)) {
			model.addAttribute("kind", "write");
			logger.info("<- [page = free_board_fail]");
			return "game/free_board_fail";
		}
		
		logger.info("<- []");
		return "redirect:/game/free/list";
	}
	
	/**
	 * 자유게시판 상세 화면 이동
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getDetail(
			@RequestParam(value = "no", required = true) Integer no,
			Model model) {
		
		logger.info("-> [no = {}]", no);
		
		FreeBoard freeBoard = freeBoardServiceImpl.getFreeBoard(new FreeBoard(no, null), "detail");
		model.addAttribute("freeBoard", freeBoard);
		
		List<FreeBoardComment> commentList = freeBoardServiceImpl.getFreeBoardCommentList(no);
		model.addAttribute("commentList" , commentList);
		
		logger.info("<- [freeBoard = {}], [commentList = {}]", freeBoard.toString(), commentList.size());
		return "game/free_board_detail";
	}
	
	/**
	 * 자유게시판 수정 화면 이동
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdate(
			@RequestParam(value = "no", required = true) Integer no,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [no = {}], [memberId = {}]", no, memberId);
		
		FreeBoard freeBoard = freeBoardServiceImpl.getFreeBoard(new FreeBoard(no, memberId), "update");
		model.addAttribute("freeBoard", freeBoard);
		
		logger.info("<- [freeBoard = {}]", freeBoard.toString());
		return "game/free_board_update";
	}
	
	/**
	 * 자유게시판 수정
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String postUpdate(
			@RequestParam(value = "no", required = true) Integer no,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "contents", required = true) String contents,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		String gameCode = ((Game) session.getAttribute("game")).getGameCode();
		
		logger.info("-> [no = {}], [memberId = {}], [gameCode], [title = {}], [contents = {}]", new Object[] { no, memberId, gameCode, title, contents });
		
		if (!freeBoardServiceImpl.updateFreeBoard(new FreeBoard(no, gameCode, memberId, title, contents))) {
			model.addAttribute("kind", "update");
			logger.info("<- [page = free_board_fail]");
			return "game/free_board_fail";
		}
		
		logger.info("<- []");
		return "redirect:/game/free/detail?no=" + no;
	}
	
	/**
	 * 자유게시판 삭제
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String postDelete(
			@RequestParam(value = "no", required = true) Integer no,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [no = {}], [memberId = {}]", no, memberId);
		
		if (!freeBoardServiceImpl.deleteFreeBoard(new FreeBoard(no, memberId))) {
			model.addAttribute("kind", "delete");
			logger.info("<- [page = free_board_fail]");
			return "game/free_board_fail";
		}
		
		logger.info("<- []");
		return "redirect:/game/free/list?isBack=true";
	}
	
	/**
	 * 자유게시판 댓글 목록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/comment/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postCommentList(@RequestParam(value = "boardNo", required = true) Integer boardNo) {
		
		logger.info("-> [boardNo = {}]", boardNo);
		
		JSONObject jsonResult = new JSONObject();
		
		JSONArray commentList = freeBoardServiceImpl.getFreeBoardCommentListToJsonArray(boardNo);
		jsonResult.put("commentList", commentList);
		
		logger.info("<- [commentListSize = {}]", commentList.size());
		return jsonResult.toString();
	}
	
	/**
	 * 자유게시판 댓글 등록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/comment/write")
	@ResponseBody
	public String postCommentWrite(
			@RequestParam(value = "boardNo", required = true) Integer boardNo,
			@RequestParam(value = "commentContents", required = true) String commentContents,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [boardNo = {}], [commentContents = {}], [memberId = {}]", new Object[] { boardNo, commentContents, memberId });
		
		JSONObject jsonResult = new JSONObject();
		boolean result = false;
		
		if (freeBoardServiceImpl.insertFreeBoardComment(new FreeBoardComment(boardNo, memberId, commentContents)))
			result = true;
		
		jsonResult.put("result", result);
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 자유게시판 댓글 삭제
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/comment/delete")
	@ResponseBody
	public String postCommentDelete(
			@RequestParam(value = "commentNo", required = true) Integer commentNo,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [commentNo = {}], [memberId = {}]", commentNo, memberId);
		
		JSONObject jsonResult = new JSONObject();
		boolean result = false;
		
		if (freeBoardServiceImpl.deleteFreeBoardComment(new FreeBoardComment(commentNo, memberId)))
			result = true;
		
		jsonResult.put("result", result);
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
