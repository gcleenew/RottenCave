package org.isep.rottencave.score;

import java.util.Date;

import org.isep.rottencave.GlobalConfiguration;

/**
 * Match score coming from the server
 * @author ROSSATO Pierre
 *
 */
public class RemoteScore implements Comparable<RemoteScore> {
	private int id;
	private int score;
	/**
	 * Saved as timestamp
	 */
	private Long playDate;
	private String playerName;
	private Long seed;
	
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
	public Long getPlayDate() {
		return playDate;
	}
	public void setPlayDate(Long playDate) {
		this.playDate = playDate;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Long getSeed() {
		return seed;
	}
	public void setSeed(Long seed) {
		this.seed = seed;
	}
	
	/**
	 * Get formated date
	 */
	public String getFormatedPlayDate() {
		return GlobalConfiguration.MEDIUM_DATE_FORMAT.format(new Date(playDate));
	}
	
	public void setPlayDateFromDate(Date date) {
		this.playDate = date.getTime();
	}

	@Override
	public int compareTo(RemoteScore o) {
		return score - o.getScore();
	}
}
