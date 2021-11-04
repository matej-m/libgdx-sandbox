package si.um.feri.libgdxsandbox.units;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldUnitsExample extends ApplicationAdapter {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private BitmapFont font;

    // world units
    private static final float WORLD_WIDTH = 600f;
    private static final float WORLD_HEIGHT = 300f;

    private static final float OFFSET = 1f; // drawing offset (just for demonstration purposes)

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        // font = new BitmapFont(); // Creates a BitmapFont using the default 15pt Arial font included in the libgdx JAR file.
        font = new BitmapFont(Gdx.files.internal("fonts/arial-32.fnt"));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    /**
     * Called when the Application should render itself. Runs every frame.
     */
    @Override
    public void render() {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        draw();

        renderer.end();

        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        drawHud();

        batch.end();
    }

    private void draw() {
        // draw world borders
        renderer.setColor(Color.GREEN);
        renderer.line(0f + OFFSET, 0f + OFFSET, WORLD_WIDTH - OFFSET, 0f + OFFSET);
        renderer.line(WORLD_WIDTH - OFFSET, 0f + OFFSET, WORLD_WIDTH - OFFSET, WORLD_HEIGHT - OFFSET);
        renderer.line(WORLD_WIDTH - OFFSET, WORLD_HEIGHT - OFFSET, 0f + OFFSET, WORLD_HEIGHT - OFFSET);
        renderer.line(0f + OFFSET, WORLD_HEIGHT - OFFSET, 0f + OFFSET, 0f + OFFSET);

        // draw rectangle in the middle of the world
        renderer.setColor(Color.YELLOW);
        renderer.rect(WORLD_WIDTH / 2f - 50f, WORLD_HEIGHT / 2f - 25f, 100f, 50f);
    }

    private void drawHud() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        String screenSize = "Screen/Window size: " + screenWidth + " x " + screenHeight + " px";
        String worldSize = "World size: " + (int) worldWidth + " x " + (int) worldHeight + " world units";
        String oneWorldUnit = "One world unit: " + (screenWidth / worldWidth) + " x " + (screenHeight / worldHeight) + " px";

        font.draw(batch,
                screenSize,
                20f,
                hudViewport.getWorldHeight() - 20f);

        font.draw(batch,
                worldSize,
                20f,
                hudViewport.getWorldHeight() - 50f);

        font.draw(batch,
                oneWorldUnit,
                20f,
                hudViewport.getWorldHeight() - 80f);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
