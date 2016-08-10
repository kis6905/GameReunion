package com.is.gr.vo;

import org.json.simple.JSONObject;


public class FreeBoardComment extends CommonVo {
	
	private Integer commentNo		= null;
	private Integer boardNo			= null;
	private String memberId			= null;
	private String nickname			= null;
	private String contents			= null;
	
	public FreeBoardComment() {
	}
	
	public FreeBoardComment(Integer commentNo, String memberId) {
		this.commentNo = commentNo;
		this.memberId = memberId;
	}
	
	public FreeBoardComment(Integer boardNo, String memberId, String contents) {
		this.boardNo = boardNo;
		this.memberId = memberId;
		this.contents = contents;
	}

	public Integer getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(Integer commentNo) {
		this.commentNo = commentNo;
	}

	public Integer getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(Integer boardNo) {
		this.boardNo = boardNo;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("commentNo", commentNo);
		jsonObj.put("boardNo", boardNo);
		jsonObj.put("memberId", memberId);
		jsonObj.put("nickname", nickname);
		jsonObj.put("contents", contents);
		jsonObj.put("registeredDate", getRegisteredDate());
		jsonObj.put("modifiedDate", getModifiedDate());
		
		return jsonObj;
	}

	@Override
	public String toString() {
		return "FreeBoard [commentNo=" + commentNo
				+ ", boardNo=" + boardNo
				+ ", memberId=" + memberId
				+ ", nickname=" + nickname
				+ ", contents=" + contents
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}
}
