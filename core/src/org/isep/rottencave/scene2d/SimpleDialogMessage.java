package org.isep.rottencave.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SimpleDialogMessage {
	private static final float DIALOG_WIDTH = 300;
	private static final float DIALOG_HEIGHT = 200;
	
	private Skin skin;
	private String title;
	private Label dialogText;
	private TextButton textButton;
	private Dialog dialog;
	
	public SimpleDialogMessage(Skin skin, String title, String dialogText) {
		this.skin = skin;
		this.title = title;
		this.dialogText = new Label(dialogText, skin);
		createDialog();
	}
	
	public SimpleDialogMessage(Skin skin, String title, String dialogText, String buttonText) {
		this.skin = skin;
		this.title = title;
		this.dialogText = new Label(dialogText, skin);
		this.textButton = new TextButton(buttonText, skin);
		createDialog();
	}
	
	private void createDialog() {
		dialog = new Dialog(title, skin);
		
		if (textButton == null) {
			textButton = new TextButton("ok", skin);
		}
		
		dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		dialog.button(textButton);
		dialog.text(dialogText);
		centerDialog(dialog);
	}

	public Dialog getDialog() {
		return dialog;
	}
	
	public static boolean centerDialog(Dialog dialog) {
		float x = (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f;
		float y = (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f;
		dialog.setPosition(x, y);
		return true;
	}
}
