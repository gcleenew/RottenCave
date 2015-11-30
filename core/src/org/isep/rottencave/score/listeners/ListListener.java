package org.isep.rottencave.score.listeners;

import java.util.LinkedList;

import org.isep.rottencave.score.RemoteScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.utils.JsonValue;

public class ListListener extends ScoreListener {
	private LinkedList<RemoteScore> remoteScores;
	
	public ListListener() {
		super();
		remoteScores = new LinkedList<RemoteScore>();
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		String result = httpResponse.getResultAsString();
		LinkedList<JsonValue> list = json.fromJson(LinkedList.class, result);
		
		for (JsonValue jsonValue : list) {
			remoteScores.add(json.readValue(RemoteScore.class, jsonValue));
		}
		
		Gdx.app.debug("Score response", result);
		Gdx.app.debug("Remote list size", ""+remoteScores.size());
	}

	public LinkedList<RemoteScore> getRemoteScores() {
		return remoteScores;
	}
}
