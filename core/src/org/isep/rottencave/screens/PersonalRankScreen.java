package org.isep.rottencave.screens;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.score.PersonalLeaderBoard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PersonalRankScreen implements Screen {
	private final RottenCave game;
	private Stage stage;
	private Skin uiSkin;
	
	public PersonalRankScreen(final RottenCave game) {
		this.game = game;
		this.uiSkin = game.getUiSkin();
		stage = new Stage(new ScreenViewport());
		PersonalLeaderBoard leaderBoard = new PersonalLeaderBoard(uiSkin);
		stage.addActor(leaderBoard.getContainer());
	}
	
	@Override
	public void show() {
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
		stage.dispose();
	}

}
