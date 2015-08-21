package com.bnana.goa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.utils.Const;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Const.APP_WIDTH;
		config.height = Const.APP_HEIGHT;
        config.title = Const.TITLE;
		new LwjglApplication(new GameOfAttraction(), config);
	}
}
