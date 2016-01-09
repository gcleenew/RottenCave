package org.isep.rottencave.scene2d;

import org.isep.rottencave.RottenCave;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SeedPlayGameDialog extends Dialog {
	private static final float DIALOG_WIDTH = 300;
	private static final float DIALOG_HEIGHT = 200;
	private RottenCave game;
	private Stage stage;
	private TextButton acceptButton;
	
	public SeedPlayGameDialog(final RottenCave game, String title, Stage stage, Skin skin) {
		super(title, skin);
		this.game = game;
		this.stage = stage;
		
		setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		text("Voulez-vous lancer une partie avec cette seed ?");
		
		acceptButton = new TextButton("Oui", skin);
		button(acceptButton);
		button(new TextButton("Non", skin));
	}
	
	public void show() {
		show(stage);
	}
	
	public void setNewSeed(Long seed) {
		acceptButton.clearListeners();
		acceptButton.addListener(new SeedPlayGameListener(game, seed));
	}
}
