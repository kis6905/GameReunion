package com.is.gr.vo;

import org.json.simple.JSONObject;

import com.is.gr.commons.Constants;

public class Message extends CommonVo {
	
	private Integer seq 			= null;
	private Integer no				= null;
	private String fromMemberId		= null;
	private String fromNickname		= null; // 보낸 사람
	private String toMemberId		= null;
	private String toNickname		= null; // 받는 사람
	private String title			= null;
	private String contents			= null;
	private String confirmStatus	= null;
	private String messageKind		= null;
	
	public Message() {
	}
	
	public Message(Integer seq, String messageKind) {
		this.seq = seq;
		this.messageKind = messageKind;
	}
	
	public Message(String fromMemberId, String title, String contents) {
		this.fromMemberId = fromMemberId;
		this.title = title;
		this.contents = contents;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getFromMemberId() {
		return fromMemberId;
	}

	public void setFromMemberId(String fromMemberId) {
		this.fromMemberId = fromMemberId;
	}
	
	public String getFromNickname() {
		return fromNickname;
	}

	public void setFromNickname(String fromNickname) {
		this.fromNickname = fromNickname;
	}

	public String getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(String toMemberId) {
		this.toMemberId = toMemberId;
	}

	public String getToNickname() {
		return toNickname;
	}

	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public void setMessageKind(String messageKind) {
		this.messageKind = messageKind;
	}
	
	public String getMessageKind() {
		return messageKind;
	}
	
	/**
	 * 쪽지 목록에 사용될 JSONObject
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seq", seq);
		jsonObject.put("fromMemberId", fromMemberId);
		jsonObject.put("toMemberId", toMemberId);
		jsonObject.put("nickname", getResultNickname());
		jsonObject.put("title", title);
		jsonObject.put("contents", contents);
		jsonObject.put("confirmStatus", confirmStatus);
		jsonObject.put("registeredDate", getRegisteredDate());
		
		return jsonObject;
	}
	
	private String getResultNickname() {
		if (messageKind != null) {
			switch (messageKind) {
			case Constants.MESSAGE_KIND_RECEIVED: 
				return fromNickname;
			case Constants.MESSAGE_KIND_SENT:
				return toNickname;
			default:
				return null;
			}
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "Message [seq=" + seq + 
				", no=" + no + 
				", fromMemberId=" + fromMemberId +
				", fromNickname=" + fromNickname +
				", toMemberId=" + toMemberId +
				", toNickname=" + toNickname +
				", title=" + title + 
				", contents=" + contents + 
				", confirmStatus=" + confirmStatus +
				", messageKind=" + messageKind +
				", registeredDate=" + getRegisteredDate() + 
				"]";
	}
	
}
