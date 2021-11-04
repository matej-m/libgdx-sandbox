package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.pooling.ObjectPoolingExample;
import si.um.feri.libgdxsandbox.units.WorldUnitsExample;

public class DesktopLauncherObjectPooling {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Object pooling example";
		config.width = 1280;
		config.height = 720;
		config.forceExit = false;
		new LwjglApplication(new ObjectPoolingExample(), config);
	}
}
