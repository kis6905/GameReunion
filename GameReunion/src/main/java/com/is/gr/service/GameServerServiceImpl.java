package com.is.gr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.gr.dao.GameServerMapper;
import com.is.gr.vo.GameServer;

@Service(value = "gameServerServiceImpl")
public class GameServerServiceImpl implements GameServerService {
	
	@Autowired
	private GameServerMapper gameServerMapper;
	
	/**
	 * 게임 서버 리스트 리턴
	 * 
	 * @param gameCode
	 * @return
	 */
	@Override
	public List<GameServer> getGameServerList(String gameCode) {
		return gameServerMapper.getGameServerList(gameCode);
	}
	
}
