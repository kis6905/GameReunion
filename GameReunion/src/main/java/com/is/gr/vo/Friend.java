package com.is.gr.vo;

import org.json.simple.JSONObject;

public class Friend {
	
	private String fromMemberId 	= null;
	private String toMemberId 		= null;
	private String nickname 		= null;
	private String status 			= null;
	private String requestedDate 	= null;
	
	public Friend() {
	}
	
	public Friend(String fromMemberId, String toMemberId) {
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
	}
	
	public Friend(String fromMemberId, String toMemberId, String status) {
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.status = status;
	}
	
	public Friend(String fromMemberId, String toMemberId, String nickname, String status, String requestedDate) {
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.nickname = nickname;
		this.status = status;
		this.requestedDate = requestedDate;
	}

	public String getFromMemberId() {
		return fromMemberId;
	}

	public void setFromMemberId(String fromMemberId) {
		this.fromMemberId = fromMemberId;
	}

	public String getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(String toMemberId) {
		this.toMemberId = toMemberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fromMemberId", fromMemberId);
		jsonObject.put("toMemberId", toMemberId);
		jsonObject.put("nickname", nickname);
		jsonObject.put("status", status);
		jsonObject.put("requestedDate", requestedDate);
		return jsonObject;
	}

	@Override
	public String toString() {
		return "Friend [fromMemberId=" + fromMemberId
				+ ", toMemberId=" + toMemberId
				+ ", nickname="	+ nickname
				+ ", status=" + status
				+ ", requestedDate=" + requestedDate
				+ "]";
	}
	
}
