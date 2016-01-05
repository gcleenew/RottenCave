package org.isep.rottencave.scene2d;

import org.isep.rottencave.score.RemoteScore;
import org.isep.rottencave.score.ScoreRestClient;
import org.isep.rottencave.score.listeners.JsonListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class SubmitScoreListener extends InputListener {
	private TextField usernameField;
	private RemoteScore remoteScore;
	private boolean scoreSent;

	public SubmitScoreListener(TextField usernameField, RemoteScore score) {
		this.usernameField = usernameField;
		this.remoteScore = score;
		scoreSent = false;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		String username = usernameField.getText();
		if (!username.equals("") && !scoreSent) {
			scoreSent = true;
			Gdx.app.debug("GameOverScreen", "Post score : "+username+" / "+remoteScore.getScore());
			
			remoteScore.setPlayerName(username);
			ScoreRestClient scoreClient = ScoreRestClient.getScoreRestClient();
			scoreClient.pushRemoteScore(new JsonListener(), remoteScore);
		}
        return true;
    }
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		
	}
}
