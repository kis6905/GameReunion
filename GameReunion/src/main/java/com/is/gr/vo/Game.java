package com.is.gr.vo;

public class Game extends CommonVo {
	
	private String gameCode		= null;
	private String gameName		= null;
	private String description	= null;
	private String genreCode	= null;
	private String genreName	= null;
	
	public Game() {
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenreCode() {
		return genreCode;
	}

	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	@Override
	public String toString() {
		return "Game [gameCode=" + gameCode
				+ ", gameName=" + gameName
				+ ", description=" + description
				+ ", genreCode=" + genreCode
				+ ", genreName=" + genreName
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}

}
