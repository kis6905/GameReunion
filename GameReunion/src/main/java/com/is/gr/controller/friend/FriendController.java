package com.is.gr.controller.friend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.is.gr.service.FriendService;
import com.is.gr.service.MemberService;
import com.is.gr.vo.Friend;

@Controller
@RequestMapping(value = "/friend/**")
public class FriendController {
	
	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	private FriendService friendServiceImpl;
	@Autowired
	private MemberService memberServiceImpl;
	
	/**
	 * 친구 페이지로 이동
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getList() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "/friend/friend_list";
	}
	
	/**
	 * 친구 목록 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postList(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [memberId = {}]", memberId);
		
		JSONObject jsonResult = new JSONObject();
		JSONArray friendList = friendServiceImpl.getFriendListToJSONArray(memberId);
		jsonResult.put("friendList", friendList);
		
		logger.info("<- [friendListSize = {}]", friendList.size());
		return jsonResult.toString();
	}
	
	/**
	 * 친구 검색 결과 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postSearch(
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
		
		logger.info("-> [search = {}]", search);

		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		JSONObject jsonResult = new JSONObject();
		JSONArray memberList = memberServiceImpl.getSearchMemberListToJSONArray(search, memberId);
		jsonResult.put("memberList", memberList);
		
		logger.info("<- [memberList = {}]", memberList.size());
		return jsonResult.toString();
	}
	
	/**
	 * 친구 요청
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postRequest(
			@RequestParam(value = "toMemberId", required = true) String toMemberId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [toMemberId = {}], [memberId = {}]", toMemberId, memberId);
		
		JSONObject jsonResult = new JSONObject();
		// 실제 화면에서는 원인에 따라 UI를 변경하지 않지만, 로그엔 남겨놓는다.
		jsonResult.put("result", friendServiceImpl.requestFriend(new Friend(memberId, toMemberId)));
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 요청받은 리스트 리턴
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/receive/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postReceiveList(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [memberId = {}]", memberId);
		
		JSONObject jsonResult = new JSONObject();
		JSONArray receiveList = friendServiceImpl.getReceiveListToJSONArray(memberId);
		jsonResult.put("receiveList", receiveList);
		
		logger.info("<- [receiveListSize = {}]", receiveList.size());
		return jsonResult.toString();
	}
	
	/**
	 * 나에게 온 친구 요청 수락 or 거부
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changestatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postChangeStatus(
			@RequestParam(value = "status", required = true) String status,
			@RequestParam(value = "fromMemberId", required = true) String fromMemberId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [status = {}], [fromMemberId = {}], [memberId = {}]", new Object[] { status, fromMemberId, memberId });
		
		JSONObject jsonResult = new JSONObject();
		boolean result = friendServiceImpl.changeFriendStatus(new Friend(fromMemberId, memberId, status.equals("accept") ? "Y" : "N"));
		jsonResult.put("result", result);
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * 친구 삭제
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String postDelete(
			@RequestParam(value = "deleteMemberId", required = true) String deleteMemberId,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		String memberId = (String) session.getAttribute("MEMBER_ID");
		
		logger.info("-> [deleteMemberId = {}], [memberId = {}]", deleteMemberId, memberId);
		
		JSONObject jsonResult = new JSONObject();
		boolean result = friendServiceImpl.deleteFriend(new Friend(deleteMemberId, memberId));
		jsonResult.put("result", result);
		
		logger.info("<- [jsonResult = {}]", jsonResult.toString());
		return jsonResult.toString();
	}
}
