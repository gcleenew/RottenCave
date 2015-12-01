package org.isep.rottencave.score.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.Json;

/**
 * Use only for debug purpose or extends
 * @author prossato
 *
 */
public class JsonListener implements HttpResponseListener {
	protected Json json;
	
	public JsonListener() {
		json = new Json();
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		Gdx.app.debug("Score response", httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
		Gdx.app.error("Score response error", t.getMessage());
		t.printStackTrace();
	}

	@Override
	public void cancelled() {
		Gdx.app.debug("Score response", "Request canceled");
	}
}
