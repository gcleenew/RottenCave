package org.isep.rottencave.scene2d;

import org.isep.rottencave.GlobalConfiguration;
import org.isep.rottencave.RottenCave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MusicCheckBoxListener extends ChangeListener {

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		GlobalConfiguration.musicOn = !GlobalConfiguration.musicOn;
		Music menuMusic = RottenCave.getMenuMusic();
		
		if (menuMusic != null) {
			if (GlobalConfiguration.musicOn) {
				menuMusic.play();
				Gdx.app.debug("Change music conf ", "On");
			} else {
				menuMusic.pause();
				Gdx.app.debug("Change music conf ", "Off");
			}
		}
	}

}
