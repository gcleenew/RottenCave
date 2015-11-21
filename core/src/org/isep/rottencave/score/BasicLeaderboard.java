package org.isep.rottencave.score;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * TODO Connection with web services
 * @author ROSSATO Pierre
 *
 */
public class BasicLeaderboard {
	private Table container;
	private Table scoreTable;
	private Skin uiSkin;
	
	public BasicLeaderboard(Skin skin) {
		this.uiSkin = skin;
		createStaticContent();
	}
	
	private void createStaticContent() {
		container = new Table();
		// TODO Remove debug drawing
		container.setDebug(true);
		container.setFillParent(true);
		container.top();
		scoreTable = new Table();
		scoreTable.debug();
		
		Label leaderBoard = new Label("LEADERBOARD", uiSkin, "title");
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
		
		container.add(leaderBoard).expandX();
		container.row().padTop(15);
		container.add(scoreTable).expand().fillX().top();
	}

	public Table getContainer() {
		return container;
	}

	public Table getScoreTable() {
		return scoreTable;
	}
}
