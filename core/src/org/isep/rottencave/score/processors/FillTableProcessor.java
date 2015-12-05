package org.isep.rottencave.score.processors;

import java.util.List;

import org.isep.rottencave.RottenCave;
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
	private Table scoreTable;
	private Skin skin;

	public FillTableProcessor(Table scoreTable, Skin skin) {
		this.scoreTable = scoreTable;
		this.skin = skin;
	}

	@Override
	public void processList(final List<RemoteScore> scoresList) {
		Gdx.app.debug("FillTableProcessor", "Begin process");
		if (scoresList.size() > 1){
			
			// Post runnable to synchronize update with rendering
			Gdx.app.debug("FillTableProcessor", "Send postRunnable");
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Label rang, playerName, formatedDate, score, seed;

					int rangIncremeter = 1;
					for (RemoteScore remoteScore : scoresList) {
						rang = new Label("" + rangIncremeter++, skin);
						playerName = new Label(remoteScore.getPlayerName(), skin);
						formatedDate = new Label(remoteScore.getFormatedPlayDate(), skin);
						score = new Label("" + remoteScore.getScore(), skin);
						seed = new Label("1", skin);

						scoreTable.row();
						scoreTable.add(new Container<Label>(rang));
						scoreTable.add(new Container<Label>(playerName));
						scoreTable.add(new Container<Label>(formatedDate));
						scoreTable.add(new Container<Label>(score));
						scoreTable.add(new Container<Label>(seed));
					}
				}
			});
		} else {
			
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
//					Dialog dialog = new Dialog("Server error", skin);
//					scoreTable.row();
//					scoreTable.add(dialog);
				}
			});
		}
	}
}
