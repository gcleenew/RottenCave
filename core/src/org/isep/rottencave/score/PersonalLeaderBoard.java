package org.isep.rottencave.score;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.scene2d.SeedPlayGameDialog;
import org.isep.rottencave.scene2d.ShowSeedPlayGameDialogListener;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PersonalLeaderBoard {
	// Define score column default size
	private static final float NUMBER_COLUMN_WIDTH = 270;
	private static final float STRING_COLUMN_WIDTH = 280;
	
	private RottenCave game;
	private Table container;
	private Table scoreTable;
	private Skin uiSkin;
	private SeedPlayGameDialog confirmSeedDialog;
	
	public PersonalLeaderBoard(RottenCave game, Skin skin) {
		this.game = game;
		this.uiSkin = skin;
		createStaticContent();
		fillLeaderBoard();
	}
	
	public PersonalLeaderBoard(RottenCave game, Skin skin, SeedPlayGameDialog confirmSeedDialog) {
		this.game = game;
		this.uiSkin = skin;
		this.confirmSeedDialog = confirmSeedDialog;
		createStaticContent();
		fillLeaderBoard();
	}
	
	private void createStaticContent() {
		// Create tables
		container = new Table();
		container.setFillParent(true);
		container.top();
		Table scoreContainer = new Table(uiSkin);
		// scoreContainer contains a scroll pane that contains the scoreTable
		scoreTable = new Table(uiSkin);
		ScrollPane scoreScrollPane = new ScrollPane(scoreTable);
		
		Label leaderBoard = new Label("SCORES", uiSkin, "title");
		Label returnLabel = new Label("Menu", uiSkin);
		returnLabel.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
		Label date = new Label("DATE", uiSkin);
		Label score = new Label("SCORE", uiSkin);
		Label seed = new Label("SEED", uiSkin);
		
		scoreTable.columnDefaults(0).width(STRING_COLUMN_WIDTH);
		scoreTable.columnDefaults(1).width(NUMBER_COLUMN_WIDTH);
		scoreTable.columnDefaults(2).width(NUMBER_COLUMN_WIDTH);
		
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreContainer).expand().top();
		container.row();
		container.add(returnLabel).bottom().left().pad(10);
		
		scoreContainer.add(new Container<Label>(date)).width(STRING_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(score)).width(NUMBER_COLUMN_WIDTH);
		scoreContainer.add(new Container<Label>(seed)).width(NUMBER_COLUMN_WIDTH);
		scoreContainer.row();
		scoreContainer.add(scoreScrollPane).colspan(3).expandX().fillX();
	}
	
	/**
	 * Fill leaderboard with local scores
	 */
	private void fillLeaderBoard() {
		PersonalScoreDAO psDAO = PersonalScoreDAO.getPersonalScoreDAO();
		List<PersonalScore> scores = psDAO.getPersonalScores();
		
		// Sort scores with descending order
		Collections.sort(scores, new Comparator<PersonalScore>() {
			@Override
			public int compare(PersonalScore o1, PersonalScore o2) {
				return o2.compareTo(o1);
			}
		});
		
		for (PersonalScore personalScore : scores) {
			addScoreToLeaderBoard(personalScore);
		}
	}
	
	private void addScoreToLeaderBoard(PersonalScore ps) {
		scoreTable.row();
		scoreTable.add(new Container<Label>(new Label(ps.getDate(), uiSkin)));
		scoreTable.add(new Container<Label>(new Label(""+ps.getScore(), uiSkin)));
		Label seedLabel = new Label(""+ps.getSeed(), uiSkin);
		if (confirmSeedDialog != null)
			seedLabel.addListener(new ShowSeedPlayGameDialogListener(confirmSeedDialog, ps.getSeed()));
		scoreTable.add(new Container<Label>(seedLabel));
	}

	public Table getContainer() {
		return container;
	}

	public Table getScoreTable() {
		return scoreTable;
	}
}
