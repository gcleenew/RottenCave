package org.isep.rottencave.score.processors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.scene2d.SeedPlayGameListener;
import org.isep.rottencave.score.RemoteScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Insert data coming from list rest service in a table in a post runnable
 * 
 * @author ROSSATO Pierre
 *
 */
public class FillTableProcessor implements ScoreListProcessor {
	private final RottenCave game;
	private Table scoreTable;
	private Skin skin;

	public FillTableProcessor(final RottenCave game, Table scoreTable, Skin skin) {
		this.game = game;
		this.scoreTable = scoreTable;
		this.skin = skin;
	}

	@Override
	public void processList(final List<RemoteScore> scoresList) {
		Gdx.app.debug("FillTableProcessor", "Begin process");
		if (scoresList.size() > 1){
			
			// Sort remote score list
			Gdx.app.debug("FillTableProcessor", "Sort collection");
			// Use comparator to sort with descending order
			Collections.sort(scoresList, new Comparator<RemoteScore>() {
				@Override
				public int compare(RemoteScore o1, RemoteScore o2) {
					return o2.compareTo(o1);
				};
			});
			
			// Post runnable to synchronize update with rendering
			Gdx.app.debug("FillTableProcessor", "Send postRunnable");
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					int rangIncremeter = 1;
					for (RemoteScore remoteScore : scoresList) {
						scoreTable.row();
						scoreTable.add(new Container<Label>(new Label("" + rangIncremeter++, skin)));
						scoreTable.add(new Container<Label>(new Label(remoteScore.getPlayerName(), skin)));
						scoreTable.add(new Container<Label>(new Label(remoteScore.getFormatedPlayDate(), skin)));
						scoreTable.add(new Container<Label>(new Label("" + remoteScore.getScore(), skin)));
						Label seedLabel = new Label("" + remoteScore.getSeed(), skin);
						seedLabel.addListener(new SeedPlayGameListener(game, remoteScore.getSeed()));
						scoreTable.add(new Container<Label>(seedLabel));
					}
				}
			});
		}
	}
}
