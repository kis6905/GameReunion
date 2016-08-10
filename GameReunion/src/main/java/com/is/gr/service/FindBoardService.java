package com.is.gr.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.is.gr.vo.FindBoard;

@Service
public interface FindBoardService {
	
	public List<FindBoard> getFindBoardList(String gameCode, String serverName, String search, long offset, long limit);
	public int getFindBoardListCount(String gameCode, String serverName, String search);
	public JSONArray getFindBoardListToJsonArray(String gameCode, String serverName, String search, long offset, long limit);
	public boolean insertFindBoard(FindBoard findBoard);
	public boolean updateFindBoard(FindBoard findBoard);
	public boolean deleteFindBoard(FindBoard findBoard);
	
}
