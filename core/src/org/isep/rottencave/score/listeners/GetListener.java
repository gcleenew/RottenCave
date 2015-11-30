package org.isep.rottencave.score.listeners;

import org.isep.rottencave.score.RemoteScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;

public class GetListener extends ScoreListener {
	private RemoteScore rs;
	
	public GetListener() {
		super();
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		String result = httpResponse.getResultAsString();
		rs = json.fromJson(RemoteScore.class, result);
		
		Gdx.app.debug("Score response", result);
		Gdx.app.debug("Remote score got", 
				"Date : " + rs.getDate() + 
				" / Player name : " + rs.getPlayerName() +
				" / Score : " + rs.getScore());
	}

	public RemoteScore getRs() {
		return rs;
	}
}
