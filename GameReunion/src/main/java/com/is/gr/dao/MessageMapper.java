package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.Message;

public interface MessageMapper {
	
	List<Message> getReceivedMessageList(Map<String, Object> map);
	Integer getReceivedMessageListCount(Map<String, String> map);
	Message getReceivedMessage(Message message);
	int getNoConfirmReceivedMessage(Message message);
	int insertReceivedMessage(Map<String, Object> map);
	int deleteReceivedMessage(Message message);
	int updateReceivedMessageConfirmStatus(Message message);
	
	List<Message> getSentMessageList(Map<String, Object> map);
	Integer getSentMessageListCount(Map<String, String> map);
	Message getSentMessage(Message message);
	int insertSentMessage(Map<String, Object> map);
	int deleteSentMessage(Message message);
	int updateSentMessageConfirmStatus(Message message);
	
}
