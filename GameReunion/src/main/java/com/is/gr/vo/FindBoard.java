package com.is.gr.vo;

import org.json.simple.JSONObject;

public class FindBoard extends CommonVo {
	
	private Integer no			= null;
	private String gameCode		= null;
	private String serverName	= null;
	private String memberId		= null;
	private String nickname		= null;
	private String title		= null;
	private String contents		= null;
	
	public FindBoard() {
	}
	
	public FindBoard(int no, String memberId) {
		this.no = no;
		this.memberId = memberId;
	}
	
	public FindBoard(String gameCode, String serverName, String memberId, String contents) {
		this.gameCode = gameCode;
		this.serverName = serverName;
		this.memberId = memberId;
		this.contents = contents;
	}
	
	public FindBoard(int no, String gameCode, String serverName, String memberId, String contents) {
		this.no = no;
		this.gameCode = gameCode;
		this.serverName = serverName;
		this.memberId = memberId;
		this.contents = contents;
	}
	
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	/**
	 * 친구찾기 목록에 사용될 JSONObject
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("no", no);
		jsonObject.put("serverName", serverName);
		jsonObject.put("memberId", memberId);
		jsonObject.put("nickname", nickname);
		jsonObject.put("contents", contents);
		jsonObject.put("registeredDate", getRegisteredDate());
		return jsonObject;
	}

	@Override
	public String toString() {
		return "FreeBoard [no=" + no
				+ ", gameCode=" + gameCode
				+ ", serverName=" + serverName
				+ ", memberId=" + memberId
				+ ", nickname=" + nickname
				+ ", title=" + title
				+ ", contents=" + contents
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}
}
