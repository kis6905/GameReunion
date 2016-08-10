package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.Game;

/**
 * @author iskwon
 */
public interface BookmarkMapper {
	
	Game getBookmark(Map<String, String> map);
	List<Game> getBookmarkList(String memberId);
	
	int insertBookmark(Map<String, String> map);
	int deleteBookmark(Map<String, String> map);
	
}
