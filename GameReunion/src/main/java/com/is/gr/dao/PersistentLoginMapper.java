package com.is.gr.dao;

import com.is.gr.vo.PersistentLogin;

/**
 * @author iskwon
 */
public interface PersistentLoginMapper {
	
	PersistentLogin getTokenForSeries(String series);
	int insertToken(PersistentLogin token);
	int updateToken(PersistentLogin token);
	int deleteToken(String memberId);
	
}
