package com.is.gr.dao;

import java.util.List;
import java.util.Map;

import com.is.gr.vo.Game;

/**
 * @author iskwon
 */
public interface GameMapper {
	
	List<Game> getGameList();
	List<Game> getSearchGameList(Map<String, String> map);
	Game getGameDetail(String gameCode);
	
}
