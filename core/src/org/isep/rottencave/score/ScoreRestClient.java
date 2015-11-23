package org.isep.rottencave.score;

import org.isep.rottencave.GlobalConfiguration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.net.HttpRequestBuilder;

/**
 * Singleton
 * @author ROSSATO Pierre
 *
 */
public class ScoreRestClient {
	public static ScoreRestClient scoreRestClient;
	
	private HttpRequestBuilder requestBuilder;
	
	private ScoreRestClient() {
		requestBuilder = new HttpRequestBuilder();
	}
	
	public void getScoreById(int id) {
		String url = GlobalConfiguration.REST_URL_BASE + "json/scores/get/" + id;
		HttpRequest request = requestBuilder.newRequest().method(HttpMethods.GET).url(url).build();
		ScoreResponseListener responseListener = new ScoreResponseListener();
		Gdx.net.sendHttpRequest(request, responseListener);
	}
	
	public static ScoreRestClient getScoreRestClient() {
		if (scoreRestClient == null)
			scoreRestClient = new ScoreRestClient();
		return scoreRestClient;
	}
}
