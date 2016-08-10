package com.is.gr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.gr.dao.BookmarkMapper;
import com.is.gr.dao.GameMapper;
import com.is.gr.vo.Game;

/**
 * @author iskwon
 */
@Service(value = "gameServiceImpl")
public class GameServiceImpl implements GameService {
	
	private static final String FLAG_ADD_BOOKMARK 		= "add";
	private static final String FLAG_REMOVE_BOOKMARK 	= "remove";
	
	@Autowired
	private GameMapper gameMapper;
	@Autowired
	private BookmarkMapper bookmarkMapper;
	
	/**
	 * 사용자가 북마크한 게임 리스트 리턴
	 * 
	 * @param memberId
	 */
	@Override
	public List<Game> getBookmarkList(String memberId) {
		return bookmarkMapper.getBookmarkList(memberId);
	}
	
	/**
	 * 모든 게임 리스트 리턴
	 */
	@Override
	public List<Game> getGameList() {
		return gameMapper.getGameList();
	}
	
	/**
	 * 검색에어 해당하는 게임 리스트 리턴
	 * 
	 * @param search 게임 명
	 */
	@Override
	public List<Game> getSearchGameList(String search) {
		if (search == null || search.isEmpty())
			search = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("search", search);
		return gameMapper.getSearchGameList(map);
	}
	
	/**
	 * 게임 상세 정보 리턴
	 * 
	 * @param gameCode
	 */
	@Override
	public Game getGameDetail(String gameCode) {
		return gameMapper.getGameDetail(gameCode);
	}
	
	/**
	 * 해당 사용자가 해당 게임을 북마크 해놨는지 여부
	 * 
	 * @param memberId
	 * @param gameCode
	 * @return true = 북마크 O, false = 북마크 X
	 */
	@Override
	public boolean isBookmark(String memberId, String gameCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("gameCode", gameCode);
		Game game = bookmarkMapper.getBookmark(map);
		return game != null;
	}
	
	/**
	 * 북마크 추가 or 삭제
	 * 
	 * @param memberId
	 * @param gameCode
	 * @param flag
	 * @return 성공 여부
	 */
	@Override
	public boolean changeBookmark(String memberId, String gameCode, String flag) {
		if (flag == null || flag.isEmpty())
			return false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("gameCode", gameCode);
		
		int result = 0;
		if (flag.equals(FLAG_ADD_BOOKMARK))
			result = bookmarkMapper.insertBookmark(map);
		else if (flag.equals(FLAG_REMOVE_BOOKMARK))
			result = bookmarkMapper.deleteBookmark(map);
		
		return result > 0;
	}

}
