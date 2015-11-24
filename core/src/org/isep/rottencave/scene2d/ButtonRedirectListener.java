package org.isep.rottencave.scene2d;

import org.isep.rottencave.RottenCave;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ButtonRedirectListener extends InputListener {
	private RottenCave game;
	private Screen redirectScreen;
	
	public ButtonRedirectListener(RottenCave game, Screen redirectScreen) {
		this.game = game;
		this.redirectScreen = redirectScreen;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        Screen currenScreen = game.getScreen();
        game.setScreen(redirectScreen);
        currenScreen.dispose();
        return true;
    }
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		
	}
}
