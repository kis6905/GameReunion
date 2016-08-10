package com.is.gr.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.gr.commons.Constants;
import com.is.gr.dao.FriendMapper;
import com.is.gr.vo.Friend;

@Service(value = "friendServiceImpl")
public class FriendServiceImpl implements FriendService {
	
	private static final String STATUS_REFUSE = "N";
	
	private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);
	
	@Autowired
	private FriendMapper friendMapper;
	
	/**
	 * 친구 목록 리턴
	 * 
	 * @param fromMemberId
	 * @return
	 */
	@Override
	public List<Friend> getFriendList(String fromMemberId) {
		List<Friend> friendList = new ArrayList<Friend>();
		try {
			friendList = friendMapper.getFriendList(fromMemberId);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return friendList;
	}
	
	/**
	 * 친구 목록을 JSONArray로 리턴
	 * 
	 * @param fromMemberId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONArray getFriendListToJSONArray(String fromMemberId) {
		JSONArray jsonArray = new JSONArray();
		
		List<Friend> friendList = getFriendList(fromMemberId);		
		for (Friend friend : friendList)
			jsonArray.add(friend.toJSONObject());
			
		return jsonArray;
	}
	
	/**
	 * 친구 요청 후 그에 대한 결과 리턴
	 * 
	 * @param friend
	 * @return 성공, 실패(원인에 따른) 코드 리턴
	 */
	@Override
	public int requestFriend(Friend friend) {
		int result = Constants.COMMON_SERVER_ERROR;
		try {
			Friend checkFriend = friendMapper.getOneFriend(friend);
			if (checkFriend == null) {
				if (friendMapper.insertFriend(friend) > 0)
					result = Constants.FRIEND_REQUEST_SUCCESS;
			}
			else {
				if (checkFriend.getStatus().equals(STATUS_REFUSE))
					result = Constants.FRIEND_REQUEST_FAIL_REFUSE;
				else
					result = Constants.FRIEND_REQUEST_FAIL_ALREADY;
			}
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
			result = Constants.COMMON_SERVER_ERROR;
		}
		return result;
	}
	
	/**
	 * 요청받은 목록 리턴
	 * 
	 * @param memberId
	 * @return
	 */
	@Override
	public List<Friend> getReceiveList(String memberId) {
		List<Friend> receiveList = new ArrayList<Friend>();
		try {
			receiveList = friendMapper.getReceiveList(memberId);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return receiveList;
	}
	
	/**
	 * 요청받은 목록을 JSONArray로 리턴
	 * 
	 * @param fromMemberId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONArray getReceiveListToJSONArray(String memberId) {
		JSONArray jsonArray = new JSONArray();
		
		List<Friend> receiveList = getReceiveList(memberId);	
		for (Friend friend : receiveList)
			jsonArray.add(friend.toJSONObject());
			
		return jsonArray;
	}
	
	/**
	 * 친구 수락 여부에 따른 상태 업데이트(수락, 거절)
	 * 수락하는 경우 from, to를 바꿔 인서트 or 업데이트도 해준다.
	 * 
	 * @param friend
	 * @return
	 */
	@Override
	public boolean changeFriendStatus(Friend friend) {
		try {
			int result = 0;
			
			// 먼저 상대방이 요청한 row의 status 업데이트
			result += friendMapper.updateFriendStatus(friend);
			
			// fromMemberId와 toMemberId를 바꿔 업데이트
			Friend reverseFriend = new Friend();
			reverseFriend.setFromMemberId(friend.getToMemberId());
			reverseFriend.setToMemberId(friend.getFromMemberId());
			reverseFriend.setStatus(friend.getStatus());
			result += friendMapper.updateFriendStatus(reverseFriend);
			
			return result > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 친구 삭제
	 * fromMemberId, toMemberId 삭제 후 바꿔서도 삭제한다.
	 * 
	 * @param friend
	 * @return
	 */
	@Override
	public boolean deleteFriend(Friend friend) {
		try {
			return friendMapper.deleteFriend(friend) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
}