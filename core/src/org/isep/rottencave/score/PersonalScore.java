package org.isep.rottencave.score;

import java.util.Date;

import org.isep.rottencave.GlobalConfiguration;

public class PersonalScore implements Comparable<PersonalScore> {
	private String date;
	private int score;
	private Long seed;

	public PersonalScore() {
		
	}
	
	public PersonalScore(Date date, int score, Long seed) {
		this.date = GlobalConfiguration.MEDIUM_DATE_FORMAT.format(date);
		this.score = score;
		this.seed = seed;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Set date from java date object
	 */
	public void setDate(Date date) {
		this.date = GlobalConfiguration.MEDIUM_DATE_FORMAT.format(date);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getSeed() {
		return seed;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}

	@Override
	public int compareTo(PersonalScore o) {
		return score - o.getScore();
	}
}
