package org.isep.rottencave.screens;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.SimpleDialogMessage;
import org.isep.rottencave.score.BasicLeaderboard;
import org.isep.rottencave.score.ScoreRestClient;
import org.isep.rottencave.score.listeners.ListListener;
import org.isep.rottencave.score.processors.FillTableProcessor;
import org.isep.rottencave.score.processors.ScoreListProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author ROSSATO Pierre
 *
 */
public class LeaderboardScreen implements Screen {
	private final RottenCave game;
	private Stage stage;
	private BasicLeaderboard leaderBoard;
	
	public LeaderboardScreen(final RottenCave game) {
		this.game = game;

		stage = new Stage(new ScreenViewport());
		leaderBoard = new BasicLeaderboard(game);
		stage.addActor(leaderBoard.getContainer());
	}
	
	private void refreshLeaderboard () {
		Table scoreTable = leaderBoard.cleanScoreTable();
		ScoreRestClient client = ScoreRestClient.getScoreRestClient();
		ScoreListProcessor processor = new FillTableProcessor(game, scoreTable, RottenCave.getUiSkin());
		ListListener listListener = new ListListener(processor, stage);
		client.getScoresList(listListener);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		refreshLeaderboard();
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
		
		// Resize dialog if exist
		for (Actor actor : stage.getActors()) {
			if (actor.getClass() == Dialog.class) {
				SimpleDialogMessage.centerDialog((Dialog) actor);
			}
		}
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
		leaderBoard = null;
	}
}
