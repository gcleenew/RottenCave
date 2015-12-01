package org.isep.rottencave.screens;

import org.isep.rottencave.GlobalConfiguration;
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
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

		Label rottenCave = new Label("Rotten Cave", uiSkin, "title");
		TextButton jouer = new TextButton("JOUER", uiSkin);
		jouer.addListener(new ButtonRedirectListener(game, new GenerationScreen(game)));
		TextButton personalScores = new TextButton("SCORES", uiSkin);
		personalScores.addListener(new ButtonRedirectListener(game, new PersonalRankScreen(game)));
		TextButton leaderboard = new TextButton("LEADERBOARD", uiSkin);
		leaderboard.addListener(new ButtonRedirectListener(game, new LeaderboardScreen(game)));
		TextButton config = new TextButton("CONFIGURATION", uiSkin);
		Label version = new Label ("Version "+ GlobalConfiguration.VERSION, uiSkin);
		
		Table buttonContainer = new Table();
		buttonContainer.defaults().pad(10);
		buttonContainer.add(jouer);
		buttonContainer.row();
		buttonContainer.add(personalScores);
		buttonContainer.row();
		buttonContainer.add(leaderboard);
		buttonContainer.row();
		buttonContainer.add(config);
		
		container.add(rottenCave).padTop(10);
		container.row();
		container.add(buttonContainer).expand().fill().top();
		container.row();
		container.add(version).bottom();
	}
}
