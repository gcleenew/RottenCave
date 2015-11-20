package org.isep.rottencave.screens;

import org.isep.rottencave.RottenCave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PersonalRankScreen implements Screen {
	private final RottenCave game;
	private Stage stage;
	private Table table;
	private Skin uiSkin;
	
	public PersonalRankScreen(final RottenCave game) {
		this.game = game;
		this.uiSkin = game.getUiSkin();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		table = createBaseTable();
		stage.addActor(table);
		
		// Draw table bounds
		table.setDebug(true);
	}
	
	private Table createBaseTable() {
		table = new Table(uiSkin);
		table.setFillParent(true);
		
		Label leaderBoard = new Label("Leaderboard", uiSkin);
		
		table.add(leaderBoard);
		
		return table;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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

}
