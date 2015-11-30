package org.isep.rottencave;

import org.isep.rottencave.score.ScoreRestClient;
import org.isep.rottencave.score.listeners.GetListener;
import org.isep.rottencave.score.listeners.ListListener;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RottenCave extends Game {
	private Skin uiSkin;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		
		this.setScreen(new MainMenuScreen(this));
		
		ScoreRestClient client = ScoreRestClient.getScoreRestClient();
		GetListener gl = new GetListener();
		client.getScoreById(gl, 1);
		ListListener ll = new ListListener();
		client.getScoresList(ll);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		uiSkin.dispose();
	}

	public Skin getUiSkin() {
		return uiSkin;
	}
}
