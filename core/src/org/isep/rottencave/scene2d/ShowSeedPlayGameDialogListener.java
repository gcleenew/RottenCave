package org.isep.rottencave.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ShowSeedPlayGameDialogListener extends InputListener {
	private SeedPlayGameDialog dialog;
	private Long seed;

	public ShowSeedPlayGameDialogListener(SeedPlayGameDialog dialog, Long seed) {
		this.dialog = dialog;
		this.seed = seed;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
 		dialog.setNewSeed(seed);
 		dialog.show();
 		return true;
 	}

}
