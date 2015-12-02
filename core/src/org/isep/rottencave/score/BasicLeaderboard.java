package org.isep.rottencave.score;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Basic leaderboard to display remote scores
 * @author ROSSATO Pierre
 *
 */
public class BasicLeaderboard {
	// Define score column default size
	private static final float NUMBER_COLUMN_WIDTH = 100;
	private static final float STRING_COLUMN_WIDTH = 160;
	
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
		// Create tables
		container = new Table(uiSkin);
		container.setFillParent(true);
		container.top();
		Table scoreContainer = new Table(uiSkin);
		// scoreContainer contains a scroll pane that contains the scoreTable
		scoreTable = new Table(uiSkin);
		ScrollPane scoreScrollPane = new ScrollPane(scoreTable);
		
		// Create widgets
		Label leaderBoard = new Label("LEADERBOARD", uiSkin, "title");
		Label returnLabel = new Label("Menu", uiSkin);
		returnLabel.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
		Label rang = new Label("RANG", uiSkin);
		Label joueur = new Label("JOUEUR", uiSkin);
		Label date = new Label("DATE", uiSkin);
		Label score = new Label("SCORE", uiSkin);
		Label seed = new Label("SEED", uiSkin);
		
		// Set scoreTable column defaults
		scoreTable.columnDefaults(0).width(NUMBER_COLUMN_WIDTH);
		scoreTable.columnDefaults(1).width(STRING_COLUMN_WIDTH);
		scoreTable.columnDefaults(2).width(STRING_COLUMN_WIDTH);
		scoreTable.columnDefaults(3).width(NUMBER_COLUMN_WIDTH);
		scoreTable.columnDefaults(4).width(NUMBER_COLUMN_WIDTH);
		
		// Fill tables with static content
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreContainer).expand().top();
		container.row();
		container.add(returnLabel).bottom().left().pad(10);
		scoreContainer.add(new Container<Label>(rang)).width(NUMBER_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(joueur)).width(STRING_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(date)).width(STRING_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(score)).width(NUMBER_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(seed)).width(NUMBER_COLUMN_WIDTH);
		scoreContainer.row();
		scoreContainer.add(scoreScrollPane).colspan(5).expandX().fillX();
	}
	
	public Table cleanScoreTable() {
		scoreTable.clear();
		return scoreTable;
	}

	public Table getContainer() {
		return container;
	}

	public Table getScoreTable() {
		return scoreTable;
	}
}
