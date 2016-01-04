package org.isep.rottencave.score;

import org.isep.rottencave.GlobalConfiguration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

/**
 * Singleton
 * @author ROSSATO Pierre
 */
public class ScoreRestClient {
	private static final String URL_BASE = GlobalConfiguration.REST_URL_BASE + "json/scores/";
	public static ScoreRestClient scoreRestClient;
	
	private HttpRequestBuilder requestBuilder;
	
	private ScoreRestClient() {
		requestBuilder = new HttpRequestBuilder();
	}
	
	public void getScoreById(HttpResponseListener responseListener, int id) {
		String url = URL_BASE + "get/" + id;
		HttpRequest request = requestBuilder.newRequest().method(HttpMethods.GET).url(url).build();
		Gdx.net.sendHttpRequest(request, responseListener);
	}
	
	public void getScoresList(HttpResponseListener responseListener) {
		String url = URL_BASE + "list";
		HttpRequest request = requestBuilder.newRequest().method(HttpMethods.GET).url(url).build();
		Gdx.net.sendHttpRequest(request, responseListener);
	}
	
	public void pushRemoteScore(HttpResponseListener responseListener, RemoteScore score) {
		String url = URL_BASE + "create";
		Json json = new Json(OutputType.json);
		HttpRequest request = requestBuilder.newRequest().method(HttpMethods.POST).url(url).build();
		request.setHeader("Content-Type", "application/json");
		request.setContent(json.toJson(score, RemoteScore.class));
		
		Gdx.app.debug("Push remote score ", json.toJson(score));
		Gdx.net.sendHttpRequest(request, responseListener);
	}
	
	public static ScoreRestClient getScoreRestClient() {
		if (scoreRestClient == null)
			scoreRestClient = new ScoreRestClient();
		return scoreRestClient;
	}
}
