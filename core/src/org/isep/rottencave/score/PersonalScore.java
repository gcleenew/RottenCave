package org.isep.rottencave.score;

import java.util.Date;

/**
 * Immutable personnal score
 * @author ROSSATO Pierre
 *
 */
public class PersonalScore {
	private Date date;
	private int score;
	private int seed;

	public PersonalScore(Date date, int score, int seed) {
		this.date = date;
		this.score = score;
		this.seed = seed;
	}

	public Date getDate() {
		return date;
	}

	public int getScore() {
		return score;
	}

	public int getSeed() {
		return seed;
	}
}
