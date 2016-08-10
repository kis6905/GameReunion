package com.is.gr.dao;

import java.util.List;

import com.is.gr.vo.GameServer;

/**
 * @author iskwon
 */
public interface GameServerMapper {
	
	List<GameServer> getGameServerList(String gameCode);
	
}
