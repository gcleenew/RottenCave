package org.isep.rottencave.screens;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
	private final RottenCave game;
	private Skin uiSkin;
	private Stage stage;
	
	public MainMenuScreen(final RottenCave game) {
		this.game= game;
		this.uiSkin = game.getUiSkin();
		stage = new Stage(new ScreenViewport());
		createStaticMenu();
	}

	@Override
	public void show() {
		// Set input processor in shwo method to avoid loose of event listener
		Gdx.input.setInputProcessor(stage);
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	private void createStaticMenu() {
		Table container = new Table();
		container.setFillParent(true);
		container.top();
		stage.addActor(container);

		Label rottenCave = new Label("Rotten Cave", uiSkin, "title");
		TextButton jouer = new TextButton("JOUER", uiSkin);
		jouer.addListener(new ButtonRedirectListener(game, new GenerationScreen(game)));
		TextButton scores = new TextButton("SCORES", uiSkin);
		scores.addListener(new ButtonRedirectListener(game, new PersonalRankScreen(game)));
		TextButton config = new TextButton("CONFIGURATION", uiSkin);
		Label version = new Label ("Version "+ RottenCave.VERSION, uiSkin);
		
		Table buttonContainer = new Table();
		buttonContainer.defaults().pad(10);
		buttonContainer.add(jouer);
		buttonContainer.row();
		buttonContainer.add(scores);
		buttonContainer.row();
		buttonContainer.add(config);
		
		container.add(rottenCave).padTop(10);
		container.row();
		container.add(buttonContainer).expand().fill().top();
		container.row();
		container.add(version).bottom();
	}
}
