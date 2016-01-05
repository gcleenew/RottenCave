package org.isep.rottencave.scene2d;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SeedPlayGameDialog extends Dialog {
	private static final float DIALOG_WIDTH = 300;
	private static final float DIALOG_HEIGHT = 200;

	
	public SeedPlayGameDialog(String title, Skin skin) {
		super(title, skin);
		
		setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		text("Voulez-vous lancer une partie avec cette seed ?\n1234");
		button(new TextButton("Oui", skin));
		button(new TextButton("Non", skin));
	}

}
