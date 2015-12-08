package org.isep.rottencave.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.isep.rottencave.RottenCave;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rotten Cave";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new RottenCave(), config);
	}
}
