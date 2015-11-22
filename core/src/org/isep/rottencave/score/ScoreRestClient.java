package org.isep.rottencave.score;

import org.isep.rottencave.GlobalConfiguration;

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
	
	public void getScoreById() {
		HttpRequest request = requestBuilder.newRequest().method(HttpMethods.GET).url(GlobalConfiguration.REST_URL_BASE + "json/scores/get/1").build();
	}
	
	public ScoreRestClient getScoreRestClient() {
		if (scoreRestClient == null)
			scoreRestClient = new ScoreRestClient();
		return scoreRestClient;
	}
	
}
