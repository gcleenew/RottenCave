package org.isep.rottencave.score;

import java.util.Date;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PersonalLeaderBoard {
	private Table container;
	private Table scoreTable;
	private Skin uiSkin;
	
	public PersonalLeaderBoard(Skin skin) {
		this.uiSkin = skin;
		createStaticContent();
		fillLeaderBoard();
	}
	
	private void createStaticContent() {
		container = new Table();
		// TODO Remove debug drawing
//		container.setDebug(true);
		container.setFillParent(true);
		container.top();
		scoreTable = new Table();
		
		Label leaderBoard = new Label("LEADERBOARD", uiSkin, "title");
		Label date = new Label("DATE", uiSkin);
		Label score = new Label("SCORE", uiSkin);
		Label seed = new Label("SEED", uiSkin);
		scoreTable.add(date).expandX();
		scoreTable.add(score).expandX();
		scoreTable.add(seed).expandX();
		
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreTable).expand().fillX().top();
	}
	
	/**
	 * Fill leaderboard with local scores
	 * TODO Manage local score creation with local JSON
	 */
	private void fillLeaderBoard() {
		PersonalScore ps = new PersonalScore(new Date(), 32, 1);
		addScoreToLeaderBoard(ps);
		ps = new PersonalScore(new Date(), 323, 2);
		addScoreToLeaderBoard(ps);
		ps = new PersonalScore(new Date(), 3243, 3);
	}
	
	private void addScoreToLeaderBoard(PersonalScore ps) {
		scoreTable.row();
		scoreTable.add(new Label(ps.getDate().toString(), uiSkin));
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
