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
import com.is.gr.dao.FreeBoardMapper;
import com.is.gr.vo.FreeBoard;
import com.is.gr.vo.FreeBoardComment;

@Service(value = "freeBoardServiceImpl")
public class FreeBoardServiceImpl implements FreeBoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(FreeBoardServiceImpl.class);
	
	@Autowired
	private FreeBoardMapper freeBoardMapper;
	
	/**
	 * 자유게시판 목록 리턴
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 */
	@Override
	public List<FreeBoard> getFreeBoardList(String gameCode, String search, long offset, long limit) {
		if (search == null || search.isEmpty())
			search = null;
		else
			search = Utility.convertSpecialCharToEntity(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameCode", gameCode);
		map.put("search", search);
		map.put("start", offset);
		map.put("end", offset + limit);
		
		List<FreeBoard> freeBoardList = new ArrayList<FreeBoard>();
		try {
			freeBoardList = freeBoardMapper.getFreeBoardList(map);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return freeBoardList;
	}
	
	/**
	 * 자유게시판 목록 개수 리턴
	 * 
	 * @param search
	 */
	@Override
	public int getFreeBoardListCount(String gameCode, String search) {
		if (search == null || search.isEmpty())
			search = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameCode", gameCode);
		map.put("search", search);
		
		try {
			return freeBoardMapper.getFreeBoardListCount(map);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return 0;
	}
	
	/**
	 * 자유게시판 목록을 JSONArray 형태로 리턴
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONArray getFreeBoardListToJsonArray(String gameCode, String search, long offset, long limit) {
		List<FreeBoard> freeBoardList = getFreeBoardList(gameCode, search, offset, limit);
		JSONArray jsonArray = new JSONArray();
		
		for (FreeBoard freeBoard : freeBoardList)
			jsonArray.add(freeBoard.toJSONObject());
		
		return jsonArray;
	}
	
	/**
	 * 자유게시판 상세
	 * 업데이트 화면으로 갈 때 자기가 작성한 글인지 확인하기 위해 memberId로도 확인한다.
	 * 
	 * @param freeBoard memberId 가 null 인 경우 상세 보기, null이 아니면 글 수정 화면으로 넘어갈 때 이다.
	 */
	@Override
	public FreeBoard getFreeBoard(FreeBoard freeBoard, String flag) {
		try {
			FreeBoard freeBoardDetail = freeBoardMapper.getFreeBoard(freeBoard);
			// 수정 화면에선 <textarea> 에 뿌려주기 때문에 엔터 key 코드 그대로 주면되지만
			// 상세 화면에선 <div> 에 뿌려주기 때문에 엔터 key 코드를 <br/> 태그로 바꿔준다.
			if (flag.equals("detail"))
				freeBoardDetail.setContents(Utility.convertEnterCodeToHtml(freeBoardDetail.getContents()));
			return freeBoardDetail;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return null;
	}
	
	/**
	 * 자유게시판 글의 댓글 리스트 리턴
	 * 
	 * @param boardNo
	 * @return
	 */
	@Override
	public List<FreeBoardComment> getFreeBoardCommentList(Integer boardNo) {
		List<FreeBoardComment> freeBoardCommentList = new ArrayList<FreeBoardComment>();
		try {
			freeBoardCommentList = freeBoardMapper.getFreeBoardCommentList(boardNo);
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return freeBoardCommentList;
	}
	
	/**
	 * 자유게시판 글 작성
	 * 게시글의 MAX(NO)를 하기때문에 동기화함
	 * 
	 * @param freeBoard
	 */
	// TODO 서버를 이중화할 경우 다른 서버와의 동기화도 고려해야 함
	@Override
	public synchronized boolean insertFreeBoard(FreeBoard freeBoard) {
		try {
			freeBoard.setTitle(Utility.convertSpecialCharToEntity(freeBoard.getTitle()));
			freeBoard.setContents(Utility.convertSpecialCharToEntity(freeBoard.getContents()));
			
			return freeBoardMapper.insertFreeBoard(freeBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 자유게시판 글 수정
	 * 
	 * @param freeBoard
	 */
	@Override
	public boolean updateFreeBoard(FreeBoard freeBoard) {
		try {
			freeBoard.setTitle(Utility.convertSpecialCharToEntity(freeBoard.getTitle()));
			freeBoard.setContents(Utility.convertSpecialCharToEntity(freeBoard.getContents()));
			
			return freeBoardMapper.updateFreeBoard(freeBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 자유게시판 글 삭제
	 * 
	 * @param freeBoard
	 */
	@Override
	public boolean deleteFreeBoard(FreeBoard freeBoard) {
		try {
			return freeBoardMapper.deleteFreeBoard(freeBoard) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 자유게시판 글의 댓글 목록을 JSONArray로 리턴
	 * 
	 * @param boardNo
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONArray getFreeBoardCommentListToJsonArray(Integer boardNo) {
		JSONArray jsonArray = new JSONArray();
		List<FreeBoardComment> commentList = getFreeBoardCommentList(boardNo);
		for (FreeBoardComment comment : commentList)
			jsonArray.add(comment.toJSONObject());
		
		return jsonArray;
	}
	
	/**
	 * 자유게시판 댓글 등록
	 * 
	 * @param freeBoardComment
	 */
	@Override
	public synchronized boolean insertFreeBoardComment(FreeBoardComment freeBoardComment) {
		try {
			freeBoardComment.setContents(Utility.convertSpecialCharToEntity(freeBoardComment.getContents()));
			return freeBoardMapper.insertFreeBoardComment(freeBoardComment) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
	
	/**
	 * 자유게시판 댓글 삭제
	 * 
	 * @param freeBoardComment
	 */
	@Override
	public boolean deleteFreeBoardComment(FreeBoardComment freeBoardComment) {
		try {
			return freeBoardMapper.deleteFreeBoardComment(freeBoardComment) > 0;
		} catch (Exception e) {
			logger.error("~~ [An error occurred]", e);
		}
		return false;
	}
}
