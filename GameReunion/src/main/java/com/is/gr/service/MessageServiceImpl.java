package com.is.gr.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.gr.commons.Constants;
import com.is.gr.commons.Utility;
import com.is.gr.dao.MemberMapper;
import com.is.gr.dao.MessageMapper;
import com.is.gr.vo.Message;

@Service(value = "messageServiceImpl")
public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 쪽지 리스트 리턴
	 */
	@Override
	public List<Message> getMessageList(String memberId, String search, long offset, long limit, String messageKind) {
		if (search == null || search.isEmpty())
			search = null;
		else
			search = Utility.convertSpecialCharToEntity(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("search", search);
		map.put("start", offset);
		map.put("end", offset + limit);
		
		List<Message> messageList = new ArrayList<Message>();
		try {
			switch (messageKind) {
			case Constants.MESSAGE_KIND_RECEIVED:
				return messageMapper.getReceivedMessageList(map);
			case Constants.MESSAGE_KIND_SENT:
				return messageMapper.getSentMessageList(map);
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return messageList;
	}

	/**
	 * 쪽지 개수 리턴
	 */
	@Override
	public int getMessageListCount(String memberId, String search, String messageKind) {
		if (search == null || search.isEmpty())
			search = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("search", search);
		
		try {
			switch (messageKind) {
			case Constants.MESSAGE_KIND_RECEIVED:
				return messageMapper.getReceivedMessageListCount(map);
			case Constants.MESSAGE_KIND_SENT:
				return messageMapper.getSentMessageListCount(map);
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return 0;
	}

	/**
	 * 쪽지 리스트를 JSONArray로 리턴
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getMessageListToJsonArray(String memberId, String search, long offset, long limit, String messageKind) {
		List<Message> messageList = getMessageList(memberId, search, offset, limit, messageKind);
		JSONArray jsonArray = new JSONArray();
		
		for (Message message : messageList) {
			message.setMessageKind(messageKind);
			jsonArray.add(message.toJSONObject());
		}
		
		return jsonArray;
	}

	/**
	 * 쪽지 상세 리턴
	 */
	@Override
	public Message getMessage(Message message) {
		Message messageDetail = new Message();
		
		switch (message.getMessageKind()) {
		case Constants.MESSAGE_KIND_RECEIVED:
			messageDetail = messageMapper.getReceivedMessage(message);
			break;
		case Constants.MESSAGE_KIND_SENT:
			messageDetail = messageMapper.getSentMessage(message);
			break;
		default:
			break;
		}
		
		if (messageDetail != null) {
			// 상세 화면에선 <div> 에 뿌려주기 때문에 엔터 key 코드를 <br/> 태그로 바꿔준다.
			messageDetail.setContents(Utility.convertEnterCodeToHtml(messageDetail.getContents()));
			messageDetail.setMessageKind(message.getMessageKind());
			updateMessageConfirmStatus(messageDetail); // 확인 상태 업데이트
		}
		
		return messageDetail;
	}

	/**
	 * 쪽지 보내기
	 * 받는 사람이 여러명일수 있으므로 배열로 받아 처리한다.
	 */
	@Override
	public synchronized boolean insertMessage(Message message, String toMemberIds) {
		if (toMemberIds == null || toMemberIds.isEmpty())
			return false;
		
		// 상세 화면에선 <div> 에 뿌려주기 때문에 엔터 key 코드를 <br/> 태그로 바꿔준다.
		message.setContents(Utility.convertEnterCodeToHtml(message.getContents()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("toMemberIdList", Arrays.asList(toMemberIds.split(";")));
		
		if (messageMapper.insertReceivedMessage(map) > 0 && messageMapper.insertSentMessage(map) > 0)
			return true;
		
		return false;
	}

	@Override
	public boolean deleteMessage(Message message) {
		return false;
	}
	
	/**
	 * 쪽지 확인했을 때 확인 상태 업데이트
	 */
	@Override
	public boolean updateMessageConfirmStatus(Message message) {
		message.setConfirmStatus(Constants.MESSAGE_CONFIRMSTATUS_Y);
		switch (message.getMessageKind()) {
		case Constants.MESSAGE_KIND_RECEIVED:
			return messageMapper.updateReceivedMessageConfirmStatus(message) > 0;
		case Constants.MESSAGE_KIND_SENT:
			return messageMapper.updateSentMessageConfirmStatus(message) > 0;
		default:
			break;
		}
		return false;
	}
	
	/**
	 * 쪽지 보낼 때 받는 사람 ID가 실제 존재하는 ID인지 확인
	 */
	@Override
	public boolean checkToMemberIds(String toMemberIds) {
		List<Object> toMemberIdList = new ArrayList<Object>();
		Map<String, String> toMemberIdMap = null;
		for (String toMemberId : toMemberIds.split(";")) {
			toMemberIdMap = new HashMap<String, String>();
			toMemberIdMap.put("toMemberId", toMemberId);
			toMemberIdList.add(toMemberIdMap);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("toMemberIdList", toMemberIdList);
		int realMemberCount = memberMapper.getCountOfMemberIdList(map);
		
		if (realMemberCount >= toMemberIdList.size())
			return true;
		
		return false;
	}
	
	/**
	 * 받은 쪽지가 있는지 확인
	 */
	@Override
	public boolean checkReceived(String toMemberId) {
		Message message = new Message();
		message.setToMemberId(toMemberId);
		message.setConfirmStatus(Constants.MESSAGE_CONFIRMSTATUS_N);
		
		return messageMapper.getNoConfirmReceivedMessage(message) > 0;
	}

}
