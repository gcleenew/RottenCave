package org.isep.rottencave.score;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * 
 * @author ROSSATO Pierre
 *
 */
public class BasicLeaderboard {

	private RottenCave game;
	private Table container;
	private Table scoreTable;
	private Skin uiSkin;
	
	public BasicLeaderboard(final RottenCave game) {
		this.game = game;
		this.uiSkin = game.getUiSkin();
		createStaticContent();
	}
	
	private void createStaticContent() {
		container = new Table();
		container.setFillParent(true);
		container.top();
		scoreTable = new Table();
		scoreTable.debug();
		
		Label leaderBoard = new Label("LEADERBOARD", uiSkin, "title");
		Label returnLabel = new Label("Menu", uiSkin);
		returnLabel.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
		resetScoreTable();
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreTable).expand().fillX().top();
		container.row();
		container.add(returnLabel).bottom().left().pad(10);
	}
	
	private void resetScoreTable() {
		scoreTable.reset();
		Label rang = new Label("RANG", uiSkin);
		Label joueur = new Label("JOUEUR", uiSkin);
		Label date = new Label("DATE", uiSkin);
		Label score = new Label("SCORE", uiSkin);
		Label seed = new Label("SEED", uiSkin);
		scoreTable.add(rang).expandX();
		scoreTable.add(joueur).expandX();
		scoreTable.add(date).expandX();
		scoreTable.add(score).expandX();
		scoreTable.add(seed).expandX();
	}

	public Table cleanScoreTable() {
		resetScoreTable();
		return scoreTable;
	}

	public Table getContainer() {
		return container;
	}

	public Table getScoreTable() {
		return scoreTable;
	}
}
