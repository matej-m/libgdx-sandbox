package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.game.PiggyBankGame;

public class DesktopLauncherPiggyBank {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Piggy bank collecting coins";	// window title
		config.width = 800;		// window width in pixels
		config.height = 600;	// window height in pixels
		config.forceExit = false;	// https://gamedev.stackexchange.com/questions/109047/how-to-close-an-app-correctly-on-desktop

		// Light Weight Java Game Library
		new LwjglApplication(new PiggyBankGame(), config);
	}
}
