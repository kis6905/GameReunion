package com.is.gr.service;

import java.util.List;

import org.json.simple.JSONArray;

import com.is.gr.vo.Message;

public interface MessageService {
	
	public List<Message> getMessageList(String toMemberId, String search, long offset, long limit, String messageKind);
	public int getMessageListCount(String toMemberId, String search, String messageKind);
	public JSONArray getMessageListToJsonArray(String toMemberId, String search, long offset, long limit, String messageKind);
	public Message getMessage(Message message);
	public boolean insertMessage(Message message, String toMemberIds);
	public boolean deleteMessage(Message message);
	public boolean updateMessageConfirmStatus(Message message);
	public boolean checkToMemberIds(String toMemberIds);
	public boolean checkReceived(String toMemberId);
	
}
