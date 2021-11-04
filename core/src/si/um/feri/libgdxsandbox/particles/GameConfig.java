package si.um.feri.libgdxsandbox.particles;

/**
 * Class should be public if you would use it in some game, but we set it to 'package-private' for demonstration purposes.
 * This means that this class is only visible in the package 'particles'.
 */
class GameConfig {

    public static final float WIDTH = 800f; // pixels; should be used in the desktop launcher
    public static final float HEIGHT = 680f;    // pixels; should be used in the desktop launcher

    public static final float HUD_WIDTH = 800f; // world units
    public static final float HUD_HEIGHT = 680f;    // world units

    public static final float WORLD_WIDTH = 40f;    // world units
    public static final float WORLD_HEIGHT = 68f;   // world units

    public static final float CAR_WIDTH = 5f;   // world units
    public static final float CAR_START_X = WORLD_WIDTH / 2f - CAR_WIDTH / 2f;

    private GameConfig() {
    }
}
