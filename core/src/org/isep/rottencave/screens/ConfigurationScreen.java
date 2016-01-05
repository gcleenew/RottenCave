package org.isep.rottencave.screens;

import org.isep.rottencave.GlobalConfiguration;
import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.scene2d.MusicCheckBoxListener;
import org.isep.rottencave.scene2d.SaveConfigListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ConfigurationScreen implements Screen {
	private final RottenCave game;
	private Skin uiSkin;
	private Stage stage;
	
	public ConfigurationScreen(final RottenCave game) {
		this.game = game;
		this.uiSkin = RottenCave.getUiSkin();
	}
	
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		createStaticMenu();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    stage.act(delta);
	    stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();

	}

	private void createStaticMenu() {
		Table container = new Table();
		container.setFillParent(true);
		container.top();
		stage.addActor(container);

		Label title = new Label("Configuration", uiSkin, "title");
		Label musicLabel = new Label("Musique :", uiSkin);
		
		CheckBox musicCheckBox = new CheckBox("", uiSkin);
		musicCheckBox.setChecked(GlobalConfiguration.musicOn);
		musicCheckBox.addListener(new MusicCheckBoxListener());
		
		Label seedLabel = new Label("Seed :", uiSkin);
		String configuredSeed = (GlobalConfiguration.configuredSeed == null) ? null : ""+GlobalConfiguration.configuredSeed;
		TextField seedField = new TextField(configuredSeed, uiSkin);
		TextButton saveButton = new TextButton("Sauvegarder", uiSkin);
		saveButton.addListener(new SaveConfigListener(seedField, stage));
		
		Label returnLabel = new Label("Menu", uiSkin);
		returnLabel.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
		
		Table actorContainer = new Table();
		actorContainer.add(musicLabel);
		actorContainer.add(musicCheckBox);
		actorContainer.row().padTop(10);
		actorContainer.add(seedLabel).padRight(10);
		actorContainer.add(seedField);
		actorContainer.row().padTop(10);
		actorContainer.add(saveButton).colspan(2);
		
		container.add(title).padTop(10);
		container.row();
		container.add(actorContainer).expand();
		container.row();
		container.add(returnLabel).bottom().left().pad(10);
	}
}
