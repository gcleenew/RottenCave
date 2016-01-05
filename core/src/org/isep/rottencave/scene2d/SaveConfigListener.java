package org.isep.rottencave.scene2d;

import org.isep.rottencave.GlobalConfiguration;
import org.isep.rottencave.RottenCave;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class SaveConfigListener extends InputListener {
	private static final String DIALOG_TITLE = "Erreur";
	private static final String DIALOG_MESSAGE = "La seed doit contenir 8 chiffres";
	private TextField seedField;
	private Stage dialogStage;
	
	public SaveConfigListener(TextField seedField, Stage dialogStage) {
		this.seedField = seedField;
		this.dialogStage = dialogStage;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if(validateSeed()){
			GlobalConfiguration.configuredSeed = Long.parseLong(seedField.getText());
		} else {
			GlobalConfiguration.configuredSeed = null;
			showErrorDialog();
		}
		return true;
	}
	
	private void showErrorDialog() {
		if(dialogStage != null){
			SimpleDialogMessage dialogMessage = new SimpleDialogMessage(RottenCave.getUiSkin(), DIALOG_TITLE, DIALOG_MESSAGE);
			dialogStage.addActor(dialogMessage.getDialog());
		}
	}

	private boolean validateSeed() {
		String stringSeed = seedField.getText();
		
		try {
			Integer.parseInt(stringSeed);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (stringSeed.length() >= 8 && stringSeed.length() < 1) {
			return false;
		}
		
		return true;
	}
}
