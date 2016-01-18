package org.isep.rottencave;

import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RottenCave extends Game {
	private static Skin uiSkin;
	private static Music menuMusic;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		if (Gdx.app.getType() == ApplicationType.Android) {
			GlobalConfiguration.REST_URL_BASE = "http://10.0.2.2:8080/RottenCaveServices/";
		}
		
		createSkin();
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/fight01.mp3"));
		menuMusic.setLooping(true);
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
		menuMusic.dispose();
	}
	
	private void createSkin() {
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		uiSkin.add("touchpad-background", new Texture("scene2d/stick-background.png"));
		uiSkin.add("touchpad-knob", new Texture("scene2d/stick-knob.png"));
	}

	public static Skin getUiSkin() {
		return uiSkin;
	}

	public static Music getMenuMusic() {
		return menuMusic;
	}
}
