package si.um.feri.libgdxsandbox.scene2d.config;

/**
 * Be careful which 'GameConfig' class you use in a specific examples. This class is 'public' because
 * of demonstration purposes. In this way, you can use it in all packages inside scene2d and also in
 * Desktop Launcher for this example. But because it is set to 'public' you can also access it from
 * other examples in different packages. Be careful which class/package you include inside an example.
 */
public class GameConfig {

    public static final float WIDTH = 800f; // pixels
    public static final float HEIGHT = 600f;    // pixels

    public static final float HUD_WIDTH = 800f; // pixels
    public static final float HUD_HEIGHT = 600f;    // pixels

    public static final float WORLD_WIDTH = 80f;    // world units
    public static final float WORLD_HEIGHT = 60f;   // world units

    public static final float STAR_SIZE = 3f;    // world units

    private GameConfig() {
    }
}
