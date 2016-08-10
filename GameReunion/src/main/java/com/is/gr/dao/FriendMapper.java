package com.is.gr.dao;

import java.util.List;

import com.is.gr.vo.Friend;

public interface FriendMapper {
	
	List<Friend> getFriendList(String fromMemberId);
	Friend getOneFriend(Friend friend);
	List<Friend> getReceiveList(String memberId);
	
	int insertFriend(Friend friend);
	int updateFriendStatus(Friend friend);
	int deleteFriend(Friend friend);
}
