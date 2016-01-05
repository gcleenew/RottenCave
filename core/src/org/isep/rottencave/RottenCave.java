package org.isep.rottencave;

import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RottenCave extends Game {
	private static Skin uiSkin;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);	
		createSkin();
		
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
	
	private void createSkin() {
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		uiSkin.add("touchpad-background", new Texture("scene2d/stick-background.png"));
		uiSkin.add("touchpad-knob", new Texture("scene2d/stick-knob.png"));
	}

	public static Skin getUiSkin() {
		return uiSkin;
	}
}
