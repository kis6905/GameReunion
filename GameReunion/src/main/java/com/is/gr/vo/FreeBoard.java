package com.is.gr.vo;

import org.json.simple.JSONObject;

public class FreeBoard extends CommonVo {
	
	private Integer no				= null;
	private String gameCode			= null;
	private String memberId			= null;
	private String nickname			= null;
	private String title			= null;
	private String contents			= null;
	private Integer commentCount	= null;
	
	public FreeBoard() {
	}
	
	public FreeBoard(int no, String memberId) {
		this.no = no;
		this.memberId = memberId;
	}
	
	public FreeBoard(String gameCode, String memberId, String title, String contents) {
		this.gameCode = gameCode;
		this.memberId = memberId;
		this.title = title;
		this.contents = contents;
	}
	
	public FreeBoard(int no, String gameCode, String memberId, String title, String contents) {
		this.no = no;
		this.gameCode = gameCode;
		this.memberId = memberId;
		this.title = title;
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
	
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	/**
	 * 자유게시판 목록에 사용될 JSONObject
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("no", no);
		jsonObject.put("nickname", nickname);
		jsonObject.put("title", title);
		jsonObject.put("commentCount", commentCount);
		jsonObject.put("registeredDate", getRegisteredDate());
		
		return jsonObject;
	}

	@Override
	public String toString() {
		return "FreeBoard [no=" + no
				+ ", gameCode=" + gameCode
				+ ", memberId=" + memberId
				+ ", nickname=" + nickname
				+ ", title=" + title
				+ ", contents=" + contents
				+ ", commentCount=" + commentCount
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}
}
