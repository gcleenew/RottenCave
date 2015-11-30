package org.isep.rottencave.score;

/**
 * Match score coming from the server
 * @author ROSSATO Pierre
 *
 */
public class RemoteScore {
	private int id;
	private int score;
	private String playDate;
	private String playerName;
	
	public RemoteScore() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getDate() {
		return playDate;
	}
	public void setDate(String date) {
		this.playDate = date;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
}
