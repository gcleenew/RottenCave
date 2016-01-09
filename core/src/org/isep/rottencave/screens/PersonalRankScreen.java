package org.isep.rottencave.screens;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.SeedPlayGameDialog;
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
		this.uiSkin = RottenCave.getUiSkin();
	}
	
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		SeedPlayGameDialog dialog = new SeedPlayGameDialog(game, "Confirmation", stage, RottenCave.getUiSkin());
		PersonalLeaderBoard leaderBoard = new PersonalLeaderBoard(game, uiSkin, dialog);
		stage.addActor(leaderBoard.getContainer());
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
