package com.is.gr.commons;

/**
 * 상수는 최대한 겹치지 않도록 한다.
 * 관련 상수는 100의 자리를 맞춘다. (ex- 101 ~ 199, 201 ~ 299, 301 ~ 399...) 
 * 
 * @author iskwon
 */
public class Constants {
	
	public static final int COMMON_SERVER_ERROR	= 9999; // 서버 에러
	
	/**
	 * 로그인 관련
	 */
	public static final int LOGIN_SUCCESS					= 101; // 성공
	public static final int LOGIN_FAIL_MISMATCH				= 102; // 계정 or 비밀번호 불일치
	public static final int LOGIN_FAIL_DISABLED				= 103; // 계정 비활성화
	public static final int LOGIN_FAIL_ACCOUNT_EXPIRED		= 104; // 계정 만료
	public static final int LOGIN_FAIL_CREDENTIALS_EXPIRED	= 105; // 계정 권한 만료
	public static final int LOGIN_FAIL_LOCKED				= 106; // 계정 잠김
	
	/**
	 * 친구 추가 요청 관련
	 */
	public static final int FRIEND_REQUEST_SUCCESS		= 201; // 요청 성공
	public static final int FRIEND_REQUEST_FAIL_ALREADY	= 202; // 이미 요청함
	public static final int FRIEND_REQUEST_FAIL_REFUSE	= 203; // 거절 상태임
	
	/**
	 * 쪽지 종류(보낸 쪽지, 받은 쪽지)
	 */
	public static final String MESSAGE_KIND_SENT 		= "sent";
	public static final String MESSAGE_KIND_RECEIVED	= "received";
	
	/**
	 * 쪽지 상태
	 */
	public static final String MESSAGE_CONFIRMSTATUS_Y	= "Y";
	public static final String MESSAGE_CONFIRMSTATUS_N	= "N";
	
}
