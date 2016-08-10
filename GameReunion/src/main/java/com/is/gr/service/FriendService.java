package com.is.gr.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.is.gr.vo.Friend;

@Service
public interface FriendService {

	public List<Friend> getFriendList(String fromMemberId);
	public JSONArray getFriendListToJSONArray(String fromMemberId);
	public int requestFriend(Friend friend);
	public List<Friend> getReceiveList(String memberId);
	public JSONArray getReceiveListToJSONArray(String memberId);
	public boolean changeFriendStatus(Friend friend);
	public boolean deleteFriend(Friend friend);
	
}
