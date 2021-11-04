package si.um.feri.libgdxsandbox.pooling;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ObjectPoolingExample extends ApplicationAdapter {

    static final float WORLD_WIDTH = 80.0f; // package private variable
    static final float WORLD_HEIGHT = 60.0f;    // package private variable

    static final float CIRCLE_SIZE = 0.75f; // package private variable
    static final float CIRCLE_HIDE_MARGIN = 3f;  // package private variable

    private boolean isZoomed = false;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private BitmapFont font;

    // array containing the active circles
    private final Array<Circle> activeCircles = new Array<Circle>();

    // three different ways of creating a pool for objects of type Circle
    //private final Pool<Circle> circlePool = Pools.get(Circle.class);
    private final Pool<Circle> circlePool = Pools.get(Circle.class, 50);
    /*private final Pool<Circle> circlePool = new Pool<Circle>() {
        @Override
        protected Circle newObject() {
            return new Circle();
        }
    };*/

    private int circlesObtained;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));

        circlePool.fill(2);

        circlesObtained = 0;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        handleInput();

        update(Gdx.graphics.getDeltaTime());

        if (isZoomed) camera.zoom = 1.15f;
        else camera.zoom = 1.0f;

        camera.update();
        viewport.apply();

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        // drawing a text on hud viewport (font size!)
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        drawHud();

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) isZoomed = !isZoomed;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) spawnCircle();
        if (Gdx.input.isKeyPressed(Input.Keys.F)) spawnCircle();    // brutal mode
    }

    private void spawnCircle() {
        boolean fromPool = circlePool.getFree() != 0;   // check if the circle is obtained from the pool
        if (fromPool) {
            circlesObtained++;
        }
        Circle circle = circlePool.obtain();
        circle.init(
                MathUtils.random(WORLD_WIDTH / 2f,
                        WORLD_WIDTH - CIRCLE_HIDE_MARGIN), -CIRCLE_HIDE_MARGIN,
                fromPool
        );
        activeCircles.add(circle);
    }

    private void update(float delta) {
        // freeing dead circles/returning them to the pool
        Circle item;
        int len = activeCircles.size;
        for (int i = len; --i >= 0; ) {
            item = activeCircles.get(i);
            item.update(delta);
            if (!item.isAlive()) {
                activeCircles.removeIndex(i);
                circlePool.free(item);
            }
        }
    }

    private void draw() {
        // drawing edges of the screen
        renderer.setColor(Color.GREEN);
        renderer.line(0f, WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        renderer.line(WORLD_WIDTH, WORLD_HEIGHT, 0f, WORLD_WIDTH, 0f, 0f);
        renderer.line(WORLD_WIDTH, 0f, 0f, 0f, 0f, 0f);
        renderer.line(0f, 0f, 0f, 0f, WORLD_HEIGHT, 0f);

        // drawing circles
        for (Circle circle : activeCircles) {
            renderer.setColor(circle.getColor());
            renderer.circle(circle.getX(), circle.getY(), CIRCLE_SIZE, 25);
        }
    }

    private void drawHud() {
        font.draw(batch,
                "active circles: " + activeCircles.size,
                20f,
                hudViewport.getWorldHeight() - 20f);

        font.draw(batch,
                "circles in pool: " + circlePool.getFree(),
                20f,
                hudViewport.getWorldHeight() - 60f);

        font.draw(batch,
                "===",
                20f,
                hudViewport.getWorldHeight() - 100f);

        font.draw(batch,
                "circles obtained: " + circlesObtained,
                20f,
                hudViewport.getWorldHeight() - 140f);

        font.draw(batch,
                "===",
                20f,
                hudViewport.getWorldHeight() - 180f);

        font.draw(batch,
                "pool 'max' size: " + circlePool.max,
                20f,
                hudViewport.getWorldHeight() - 220f);

        font.draw(batch,
                "pool 'peak' size: " + circlePool.peak,
                20f,
                hudViewport.getWorldHeight() - 260f);

        font.draw(batch,
                "Press 'z' to zoom out/in",
                20f,
                hudViewport.getWorldHeight() - 600);

        font.draw(batch,
                "Press 'space' to spawn the circle",
                20f,
                hudViewport.getWorldHeight() - 640f);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
