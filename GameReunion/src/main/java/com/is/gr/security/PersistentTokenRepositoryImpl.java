package com.is.gr.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.is.gr.dao.MemberMapper;
import com.is.gr.dao.PersistentLoginMapper;
import com.is.gr.vo.Member;
import com.is.gr.vo.PersistentLogin;

/**
 * PersistentTokenRepository 구현 클래스
 * 자동 로그인 관련
 * 
 * @author iskwon
 */
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {

	@Autowired
	private PersistentLoginMapper persistentLoginMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		persistentLoginMapper.insertToken(new PersistentLogin(token));
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		persistentLoginMapper.updateToken(new PersistentLogin(null, series, tokenValue, lastUsed));
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		System.out.println("=== getTokenForSeries() ===");
		PersistentLogin token = persistentLoginMapper.getTokenForSeries(seriesId);
		
		// request를 가져와 session에 MEMBER_ID, NICKNAME 세팅
		HttpSession session = request.getSession(true);
		session.setAttribute("MEMBER_ID", token.getMemberId());
		
		Member member = memberMapper.getMemberOfId(token.getMemberId());
		session.setAttribute("NICKNAME", member.getNickname());
		
		return new PersistentRememberMeToken(token.getMemberId(), token.getSeries(), token.getTokenValue(), token.getLastUsed());
	}

	@Override
	public void removeUserTokens(String username) {
		persistentLoginMapper.deleteToken(username);
	}

}
