package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.scene2d.Scene2dExample;
import si.um.feri.libgdxsandbox.scene2d.config.GameConfig;

public class DesktopLauncherScene2d {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Scene2d example";
        config.width = (int) GameConfig.WIDTH;
        config.height = (int) GameConfig.HEIGHT;
        config.forceExit = false;
        new LwjglApplication(new Scene2dExample(), config);
    }
}
