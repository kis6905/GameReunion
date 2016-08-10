package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.Member;

/**
 * @author iskwon
 */
public interface MemberMapper {
	
	List<Member> getSearchMemberList(Map<String, Object> map);
	Member getMemberOfId(String memberId);
	Member getMemberOfNickname(String nickname);
	int getCountOfMemberIdList(Map<String, Object> map);
	
	int insertMember(Member member);
	int updateWhenLoginSuccess(String memberId);
	int increasePasswordFailCnt(String memberId);
	
}
