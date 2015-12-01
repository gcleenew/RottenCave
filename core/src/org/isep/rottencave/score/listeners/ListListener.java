package org.isep.rottencave.score.listeners;

import java.util.LinkedList;
import java.util.List;

import org.isep.rottencave.score.RemoteScore;
import org.isep.rottencave.score.processors.ScoreListProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Listener that execute his ScoreListProcessor process function
 * @author prossato
 *
 */
public class ListListener extends JsonListener {
	private List<RemoteScore> remoteScores;
	private ScoreListProcessor processor;
	
	public ListListener(ScoreListProcessor processor) {
		super();
		remoteScores = new LinkedList<RemoteScore>();
		this.processor = processor;
	}
	
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		Gdx.app.debug("Score response", "ScoresList recieved !");
		
		String result = httpResponse.getResultAsString();
		@SuppressWarnings("unchecked")
		LinkedList<JsonValue> list = json.fromJson(LinkedList.class, result);
		
		for (JsonValue jsonValue : list) {
			remoteScores.add(json.readValue(RemoteScore.class, jsonValue));
		}
		
		Gdx.app.debug("Score response", result);
		Gdx.app.debug("Remote list size", ""+remoteScores.size());
		
		Gdx.app.debug("Score response", "Process data");
		processor.processList(remoteScores);
	}

	public List<RemoteScore> getRemoteScores() {
		return remoteScores;
	}
}
