package org.isep.rottencave.score;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Singleton that manage personalScore storage and access in JSON file
 * @author ROSSATO Pierre
 *
 */
public class PersonalScoreDAO {
	private static PersonalScoreDAO personalScoreDAO;
	// TODO check path on every environnement
	private static final FileHandle JSON_SCORE_FILE = Gdx.files.local("save/scores.json");
	
	private Json json;
	
	private PersonalScoreDAO() {
		json = new Json();
	}
	
	public List<PersonalScore> getPersonalScores() {
		List<PersonalScore> scores = new LinkedList<PersonalScore>();
		BufferedReader reader = JSON_SCORE_FILE.reader(100);
		
		try {
			String psJSON = reader.readLine();
			while (psJSON != null) {
				scores.add(json.fromJson(PersonalScore.class, psJSON));
				psJSON = reader.readLine();
			};
		} catch (IOException e) {
			Gdx.app.error("Score reading error", e.toString());
		}
		
		return scores;
	}
	
	public void addPersonalScore(PersonalScore score) {
		JSON_SCORE_FILE.writeString(json.toJson(score)+"\n", true);
	}
	
	public void flushSave() {
		JSON_SCORE_FILE.writeString("", false);;
	}
	
	public static PersonalScoreDAO getPersonalScoreDAO() {
		if (personalScoreDAO == null)
			personalScoreDAO = new PersonalScoreDAO();
		return personalScoreDAO;
	}
}
