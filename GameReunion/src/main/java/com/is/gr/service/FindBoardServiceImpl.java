package com.is.gr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.gr.commons.Utility;
import com.is.gr.dao.FindBoardMapper;
import com.is.gr.vo.FindBoard;

@Service(value = "findBoardServiceImpl")
public class FindBoardServiceImpl implements FindBoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(FindBoardServiceImpl.class);
	
	@Autowired
	private FindBoardMapper findBoardMapper;
	
	/**
	 * 친구 찾기 게시판 목록 리턴
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 */
	@Override
	public List<FindBoard> getFindBoardList(String gameCode, String serverName, String search, long offset, long limit) {
		if (search == null || search.isEmpty())
			search = null;
		else
			search = Utility.convertSpecialCharToEntity(search);
		
		if (serverName == null || serverName.isEmpty())
			serverName = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameCode", gameCode);
		map.put("serverName", serverName);
		map.put("search", search);
		map.put("start", offset);
		map.put("end", offset + limit);
		
		List<FindBoard> findBoardList = new ArrayList<FindBoard>();
		try {
			findBoardList = findBoardMapper.getFindBoardList(map);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return findBoardList;
	}
	
	/**
	 * 친구 찾기 게시판 목록 개수 리턴
	 * 
	 * @param search
	 */
	@Override
	public int getFindBoardListCount(String gameCode, String serverName, String search) {
		if (search == null || search.isEmpty())
			search = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameCode", gameCode);
		map.put("serverName", serverName);
		map.put("search", search);
		
		try {
			return findBoardMapper.getFindBoardListCount(map);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return 0;
	}
	
	/**
	 * 친구 찾기 게시판 목록을 JSONArray 형태로 리턴
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONArray getFindBoardListToJsonArray(String gameCode, String serverName, String search, long offset, long limit) {
		JSONArray jsonArray = new JSONArray();

		List<FindBoard> findBoardList = getFindBoardList(gameCode, serverName, search, offset, limit);
		for (FindBoard findBoard : findBoardList)
			jsonArray.add(findBoard.toJSONObject());
		
		return jsonArray;
	}
	
	/**
	 * 친구 찾기 게시판 글 작성.
	 * 게시글의 MAX(NO)를 하기때문에 동기화함
	 * 
	 * @param findBoard
	 */
	// TODO 서버를 이중화할 경우 다른 서버와의 동기화도 고려해야 함
	@Override
	public synchronized boolean insertFindBoard(FindBoard findBoard) {
		try {
			findBoard.setServerName(Utility.convertSpecialCharToEntity(findBoard.getServerName()));
			findBoard.setContents(Utility.convertSpecialCharToEntity(findBoard.getContents()));
			
			return findBoardMapper.insertFindBoard(findBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 친구 찾기 게시판 글 수정
	 * 
	 * @param findBoard
	 */
	@Override
	public boolean updateFindBoard(FindBoard findBoard) {
		try {
			findBoard.setTitle(Utility.convertSpecialCharToEntity(findBoard.getTitle()));
			findBoard.setContents(Utility.convertSpecialCharToEntity(findBoard.getContents()));
			
			return findBoardMapper.updateFindBoard(findBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 친구 찾기 게시판 글 삭제
	 * 
	 * @param findBoard
	 */
	@Override
	public boolean deleteFindBoard(FindBoard findBoard) {
		try {
			return findBoardMapper.deleteFindBoard(findBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
}
