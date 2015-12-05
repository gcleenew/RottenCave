package org.isep.rottencave.score.listeners;

import java.util.LinkedList;
import java.util.List;

import org.isep.rottencave.score.RemoteScore;
import org.isep.rottencave.score.processors.ScoreListProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Listener that execute his ScoreListProcessor process function on the list received
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
	
	public ListListener(ScoreListProcessor processor, Stage stage) {
		super(stage);
		remoteScores = new LinkedList<RemoteScore>();
		this.processor = processor;
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		HttpStatus status = httpResponse.getStatus();
		
		if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
			String result = httpResponse.getResultAsString();
			
			Gdx.app.debug("Score response", "ScoresList recieved !");
			Gdx.app.debug("Score response", result);
			
			@SuppressWarnings("unchecked")
			LinkedList<JsonValue> list = json.fromJson(LinkedList.class, result);
			
			if (list != null) {
				for (JsonValue jsonValue : list) {
					remoteScores.add(json.readValue(RemoteScore.class, jsonValue));
				}
				
				Gdx.app.debug("Remote list size", ""+remoteScores.size());
			} else {
				Gdx.app.debug("Score response", "No data to process !");
			}
			
			Gdx.app.debug("Score response", "Process data");
			processor.processList(remoteScores);
		} else {
			Gdx.app.debug("Score response", "http error, code : "+status.getStatusCode());
			showDialog();
		}
	}

	public List<RemoteScore> getRemoteScores() {
		return remoteScores;
	}
}
