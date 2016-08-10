package com.is.gr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.is.gr.vo.Game;

@Service
public interface GameService {
	
	public List<Game> getBookmarkList(String memberId);
	public List<Game> getGameList();
	public List<Game> getSearchGameList(String search);
	public Game getGameDetail(String gameCode);
	public boolean isBookmark(String memberId, String gameCode);
	public boolean changeBookmark(String memberId, String gameCode, String flag);
	
}
