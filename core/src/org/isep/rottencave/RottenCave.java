package org.isep.rottencave;

import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RottenCave extends Game {
	private static Skin uiSkin;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		this.setScreen(new MainMenuScreen(this));
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

	public static Skin getUiSkin() {
		return uiSkin;
	}
}
