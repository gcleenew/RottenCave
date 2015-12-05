package org.isep.rottencave.score.listeners;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.SimpleDialogMessage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;

/**
 * Use only for debug purpose or extends
 * @author prossato
 *
 */
public class JsonListener implements HttpResponseListener {
	protected static final String DIALOG_TITLE = "Erreur de connexion";
	protected static final String DIALOG_MESSAGE = "Les données ne sont pas accessible\nVeuillez réessayer plus tard";
	
	protected Json json;
	protected Stage stage;
	
	public JsonListener() {
		json = new Json();
	}
	
	/**
	 * Define stage to show dialog report
	 */
	public JsonListener(Stage stage) {
		json = new Json();
		this.stage = stage;
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		Gdx.app.debug("Score response", httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
		Gdx.app.error("Score response error", t.getMessage());
		t.printStackTrace();
		showDialog();
	}

	@Override
	public void cancelled() {
		Gdx.app.debug("Score response", "Request canceled");
	}
	
	protected void showDialog() {
		if (stage != null) {
			SimpleDialogMessage dialogMessage = new SimpleDialogMessage(RottenCave.getUiSkin(), DIALOG_TITLE, DIALOG_MESSAGE);
			stage.addActor(dialogMessage.getDialog());
		}
	}
}
