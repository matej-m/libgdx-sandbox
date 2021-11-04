package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.particles.ParticlesExample;

public class DesktopLauncherParticles {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Particle example";
        config.width = 800;    // (int) GameConfig.WIDTH;
        config.height = 680;    // (int) GameConfig.HEIGHT;
        config.forceExit = false;
        new LwjglApplication(new ParticlesExample(), config);
    }
}
