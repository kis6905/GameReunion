package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.FreeBoard;
import com.is.gr.vo.FreeBoardComment;

/**
 * @author iskwon
 */
public interface FreeBoardMapper {
	
	List<FreeBoard> getFreeBoardList(Map<String, Object> map);
	Integer getFreeBoardListCount(Map<String, String> map);
	FreeBoard getFreeBoard(FreeBoard freeBoard);
	
	List<FreeBoardComment> getFreeBoardCommentList(Integer boardNo);
	
	int insertFreeBoard(FreeBoard freeBoard);
	int updateFreeBoard(FreeBoard freeBoard);
	int deleteFreeBoard(FreeBoard freeBoard);
	
	int insertFreeBoardComment(FreeBoardComment freeBoardComment);
	int deleteFreeBoardComment(FreeBoardComment freeBoardComment);
}
