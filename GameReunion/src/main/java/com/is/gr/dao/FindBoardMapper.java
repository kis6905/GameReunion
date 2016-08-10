package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.FindBoard;

/**
 * @author iskwon
 */
public interface FindBoardMapper {
	
	List<FindBoard> getFindBoardList(Map<String, Object> map);
	Integer getFindBoardListCount(Map<String, String> map);
	
	int insertFindBoard(FindBoard FindBoard);
	int updateFindBoard(FindBoard FindBoard);
	int deleteFindBoard(FindBoard FindBoard);
	
}
