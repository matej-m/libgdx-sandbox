package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.units.WorldUnitsExample;

public class DesktopLauncherWorldUnits {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "World units example";
		config.width = 1200;
		config.height = 600;
		config.forceExit = false;
		new LwjglApplication(new WorldUnitsExample(), config);
	}
}
