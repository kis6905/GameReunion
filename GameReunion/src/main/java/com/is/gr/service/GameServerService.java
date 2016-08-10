package com.is.gr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.is.gr.vo.GameServer;

@Service
public interface GameServerService {
	
	public List<GameServer> getGameServerList(String gameCode);
	
}
