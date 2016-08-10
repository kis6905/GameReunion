package com.is.gr.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.is.gr.vo.FreeBoard;
import com.is.gr.vo.FreeBoardComment;

@Service
public interface FreeBoardService {
	
	public List<FreeBoard> getFreeBoardList(String gameCode, String search, long offset, long limit);
	public int getFreeBoardListCount(String gameCode, String search);
	public JSONArray getFreeBoardListToJsonArray(String gameCode, String search, long offset, long limit);
	public FreeBoard getFreeBoard(FreeBoard freeBoard, String flag);
	public List<FreeBoardComment> getFreeBoardCommentList(Integer boardNo);
	public boolean insertFreeBoard(FreeBoard freeBoard);
	public boolean updateFreeBoard(FreeBoard freeBoard);
	public boolean deleteFreeBoard(FreeBoard freeBoard);
	public JSONArray getFreeBoardCommentListToJsonArray(Integer boardNo);
	public boolean insertFreeBoardComment(FreeBoardComment freeBoardComment);
	public boolean deleteFreeBoardComment(FreeBoardComment freeBoardComment);
	
}
