package org.isep.rottencave.scene2d;

import org.isep.rottencave.RottenCave;
import org.isep.rottencave.screens.GenerationScreen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Listener which launch a new game with the seed
 * 
 * @author ROSSATO Pierre
 *
 */
public class SeedPlayGameListener extends InputListener {
	private Long seed;
	private final RottenCave game;

	public SeedPlayGameListener(final RottenCave game, Long seed) {
		this.seed = seed;
		this.game = game;
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {		
		Screen currentScreen = game.getScreen();
		game.setScreen(new GenerationScreen(game, seed));
		currentScreen.dispose();
		return true;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

	}
}
