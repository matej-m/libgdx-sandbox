package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.ashley.AshleyExample;

public class DesktopLauncherAshley {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Ashley example";
        config.width = 800;
        config.height = 600;
        config.forceExit = false;
        new LwjglApplication(new AshleyExample(), config);
    }
}
