package org.isep.rottencave.score;

import java.util.List;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.ButtonRedirectListener;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PersonalLeaderBoard {
	private RottenCave game;
	private Table container;
	private Table scoreTable;
	private Skin uiSkin;
	
	public PersonalLeaderBoard(RottenCave game, Skin skin) {
		this.game = game;
		this.uiSkin = skin;
		createStaticContent();
		fillLeaderBoard();
	}
	
	private void createStaticContent() {
		container = new Table();
		container.setFillParent(true);
		container.top();
		scoreTable = new Table();
		
		Label leaderBoard = new Label("LEADERBOARD", uiSkin, "title");
		Label returnLabel = new Label("Menu", uiSkin);
		returnLabel.addListener(new ButtonRedirectListener(game, new MainMenuScreen(game)));
		Label date = new Label("DATE", uiSkin);
		Label score = new Label("SCORE", uiSkin);
		Label seed = new Label("SEED", uiSkin);
		scoreTable.add(date).expandX();
		scoreTable.add(score).expandX();
		scoreTable.add(seed).expandX();
		
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreTable).expand().fillX().top();
		container.row();
		container.add(returnLabel).bottom().left().pad(10);
	}
	
	/**
	 * Fill leaderboard with local scores
	 */
	private void fillLeaderBoard() {
		PersonalScoreDAO psDAO = PersonalScoreDAO.getPersonalScoreDAO();
		List<PersonalScore> scores = psDAO.getPersonalScores();
		for (PersonalScore personalScore : scores) {
			addScoreToLeaderBoard(personalScore);
		}
	}
	
	private void addScoreToLeaderBoard(PersonalScore ps) {
		scoreTable.row();
		scoreTable.add(new Label(ps.getDate(), uiSkin));
		scoreTable.add(new Label(""+ps.getScore(), uiSkin));
		scoreTable.add(new Label(""+ps.getSeed(), uiSkin));
	}

	public Table getContainer() {
		return container;
	}

	public Table getScoreTable() {
		return scoreTable;
	}
}