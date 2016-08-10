package com.is.gr.vo;

public class GameServer extends CommonVo {
	
	private String serverName 	= null;
	private String gameCode		= null;
	
	public GameServer() {
	}
	
	public GameServer(String serverName, String gameCode) {
		super();
		this.serverName = serverName;
		this.gameCode = gameCode;
	}

	public String getServerName() {
		return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getGameCode() {
		return gameCode;
	}
	
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@Override
	public String toString() {
		return "GameServer [serverName=" + serverName
				+ ", gameCode=" + gameCode
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}
	
}
