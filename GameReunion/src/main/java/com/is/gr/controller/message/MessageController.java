package com.is.gr.controller.message;

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

import com.is.gr.service.MessageService;
import com.is.gr.vo.Message;

/**
 * @author iskwon
 */
@Controller
@RequestMapping(value = "/message/**")
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageServiceImpl;
	
	/**
	 * 쪽지 목록 화면으로 이동
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList(
			@RequestParam(value = "isBack", required = false) Boolean isBack,
			Model model) {
		
		logger.info("-> [isBack = {}]", isBack);
		
		model.addAttribute("isBack", isBack == null ? false : isBack);
		
		logger.info("<- []");
		return "message/message_list";
	}
	
	/**
	 * 쪽지 목록 리턴
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
			String memberId = (String) session.getAttribute("MEMBER_ID");
			String messageKind = (String) requestJson.get("messageKind");
			String search = (String) requestJson.get("search");
			long offset = (long) requestJson.get("offset");
			long limit = (long) requestJson.get("limit");
			
			JSONArray rows = messageServiceImpl.getMessageListToJsonArray(memberId, search, offset, limit, messageKind);
			jsonResult.put("rows", rows);
			int total = messageServiceImpl.getMessageListCount(memberId, search, messageKind);
			jsonResult.put("total", total);
			
			logger.info("<- [total = {}], [rows = {}]", total, rows.size());
		} catch (ParseException e) {
			logger.error("~~ [An error occurred]", e);
		}
		return jsonResult.toString();
	}
	
	/**
	 * 쪽지 작성 화면 이동
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String getWrite() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "message/message_write";
	}
	
	/**
	 * 쪽지 작성
	 */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "contents", required = true) String contents,
			@RequestParam(value = "toMemberIds", required = true) String toMemberIds,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		String fromMemberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [fromMemberId = {}], [toMemberId = {}], [title = {}], [contents = {}]", new Object[] { fromMemberId, toMemberIds, title, contents });
		
		Message message = new Message(fromMemberId, title, contents);
		
		if (!messageServiceImpl.insertMessage(message, toMemberIds)) {
			model.addAttribute("kind", "write");
			logger.info("<- [page = message_fail]");
			return "message/message_fail";
		}
		
		logger.info("<- []");
		return "redirect:/message/list";
	}
	
	/**
	 * 쪽지 보낼 때 받는 사람 ID가 실제 존재하는 ID인지 확인
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/check/tomemberids", method = RequestMethod.POST)
	@ResponseBody
	public String postCheckToMemberIds(
			@RequestParam(value = "toMemberIds", required = true) String toMemberIds) {
		
		logger.info("-> [toMemberIds = {}]", toMemberIds);
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", messageServiceImpl.checkToMemberIds(toMemberIds));
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 쪽지 상세 화면 이동
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getDetail(
			@RequestParam(value = "seq", required = true) Integer seq,
			@RequestParam(value = "messageKind", required = true) String messageKind,
			Model model) {
		
		logger.info("-> [seq = {}], [messageKind = {}]", seq, messageKind);
		
		Message message = messageServiceImpl.getMessage(new Message(seq, messageKind));
		model.addAttribute("message", message);
		
		logger.info("<- [message = {}]", message.toString());
		return "message/message_detail";
	}
	
	/**
	 * 받은 쪽지가 있는지 확인
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/check/received", method = RequestMethod.POST)
	@ResponseBody
	public String postCheckReceived(
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String toMemberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [toMemberId = {}]", toMemberId);
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", messageServiceImpl.checkReceived(toMemberId));
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
}
