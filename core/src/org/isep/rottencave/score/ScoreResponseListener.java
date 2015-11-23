package org.isep.rottencave.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class ScoreResponseListener implements HttpResponseListener {

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		System.out.println(httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
		Gdx.app.error("Score response error", t.getMessage());
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub

	}

}
