package org.isep.rottencave.score.processors;

import java.util.List;

import org.isep.rottencave.score.RemoteScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Insert data coming from list rest service in a table 
 * @author ROSSATO Pierre
 *
 */
public class FillTableProcessor implements ScoreListProcessor {
	private Table scoreTable;
	private Skin skin;
	
	public FillTableProcessor(Table scoreTable, Skin skin) {
		this.scoreTable = scoreTable;
		this.skin = skin;
	}
	
	@Override
	public void processList(List<RemoteScore> scoresList) {
		Gdx.app.debug("Fill list in table", "Begin process");
		for (RemoteScore remoteScore : scoresList) {
			scoreTable.row();
			scoreTable.add(new Label("1", skin)).expandX();
			scoreTable.add(new Label(remoteScore.getPlayerName(), skin)).expandX();
			scoreTable.add(new Label(remoteScore.getFormatedPlayDate(), skin)).expandX();
			scoreTable.add(new Label(""+remoteScore.getScore(), skin)).expandX();
			scoreTable.add(new Label("1", skin)).expandX();
		}
	}
}
