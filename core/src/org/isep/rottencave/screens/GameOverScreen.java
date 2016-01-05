package org.isep.rottencave.screens;

import java.util.Date;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.generation.ProceduralGeneration;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.scene2d.SubmitScoreListener;
import org.isep.rottencave.score.RemoteScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {
	private final RottenCave game;
	private Skin uiSkin;
	private Stage stage;
	
	private RemoteScore remoteScore;
	private TextField usernameField;
	private TextButton submitButton;
	
	public GameOverScreen(final RottenCave game, int score) {
		this.game = game;
		this.uiSkin = RottenCave.getUiSkin();
		remoteScore = new RemoteScore();
		remoteScore.setScore(score);
		remoteScore.setPlayDateFromDate(new Date());
		remoteScore.setSeed(ProceduralGeneration.getLastSeedUsed());
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

		Label title = new Label("Game Over", uiSkin, "title");
		Label scoreLabel = new Label ("Score : "+ remoteScore.getScore(), uiSkin);
		usernameField = new TextField("", uiSkin);
		submitButton = new TextButton("Envoyer", uiSkin);
		submitButton.addListener(new SubmitScoreListener(usernameField, remoteScore));
		TextButton menuButton = new TextButton("Quitter", uiSkin);
		menuButton.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
				
		Table actorContainer = new Table();
		actorContainer.add(scoreLabel).padBottom(10).colspan(2);
		actorContainer.row();
		actorContainer.add(usernameField).padRight(10);
		actorContainer.add(submitButton);
		
		container.add(title).padTop(10);
		container.row();
		container.add(actorContainer).expand();
		container.row();
		container.add(menuButton).bottom().left().pad(10);
	}
}
