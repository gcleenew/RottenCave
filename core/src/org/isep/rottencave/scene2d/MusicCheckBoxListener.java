package org.isep.rottencave.scene2d;

import org.isep.rottencave.GlobalConfiguration;
import org.isep.rottencave.screens.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MusicCheckBoxListener extends ChangeListener {

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		GlobalConfiguration.musicOn = !GlobalConfiguration.musicOn;
		if (GlobalConfiguration.musicOn) {
			MainMenuScreen.menuMusic.play();
			Gdx.app.debug("Change music conf ", "On");
		} else {
			MainMenuScreen.menuMusic.stop();
			Gdx.app.debug("Change music conf ", "Off");
		}
	}

}
