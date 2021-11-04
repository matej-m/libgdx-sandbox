package si.um.feri.libgdxsandbox.particles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ParticlesExample extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Viewport hudViewport;
    private GlyphLayout layout;

    private BitmapFont font;

    private Texture roadImg;
    private Texture carImg;

    private float carHeight;
    private float carPositionY;

    private boolean help = false;
    private boolean indicatorLeft = false;
    private boolean indicatorRight = false;
    private boolean startEngine = false;

    private static final float INDICATOR_OFFSET = 0.75f;

    private ParticleEffect pEffectIndicatorLeftFront;
    private ParticleEffect pEffectIndicatorRightFront;
    private ParticleEffect pEffectIndicatorLeftBack;
    private ParticleEffect pEffectIndicatorRightBack;
    private ParticleEffect pEffectSmoke;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        layout = new GlyphLayout();

        font = new BitmapFont(Gdx.files.internal("fonts/arial-32.fnt"));
        font.getData().markupEnabled = true;    // https://github.com/libgdx/libgdx/wiki/Color-Markup-Language

        roadImg = new Texture("images/black-road.jpg");
        carImg = new Texture("images/red-car.png");

        carHeight = GameConfig.CAR_WIDTH * ((float) carImg.getHeight() / carImg.getWidth());
        carPositionY = GameConfig.WORLD_HEIGHT / 2f - carHeight / 2f;   // car center screen position y

        initParticleEffects();
    }

    private void initParticleEffects() {
        pEffectIndicatorLeftFront = new ParticleEffect();
        pEffectIndicatorLeftFront.load(Gdx.files.internal("particles/indicator.pe"), Gdx.files.internal(""));
        pEffectIndicatorLeftFront.setPosition(
                GameConfig.WORLD_WIDTH / 2f - GameConfig.CAR_WIDTH / 2f + INDICATOR_OFFSET,
                carPositionY + carHeight
        );
        pEffectIndicatorRightFront = new ParticleEffect();
        pEffectIndicatorRightFront.load(Gdx.files.internal("particles/indicator.pe"), Gdx.files.internal(""));
        pEffectIndicatorRightFront.setPosition(
                GameConfig.WORLD_WIDTH / 2f + GameConfig.CAR_WIDTH / 2f - INDICATOR_OFFSET,
                carPositionY + carHeight
        );
        pEffectIndicatorLeftBack = new ParticleEffect();
        pEffectIndicatorLeftBack.load(Gdx.files.internal("particles/indicator.pe"), Gdx.files.internal(""));
        pEffectIndicatorLeftBack.setPosition(
                GameConfig.WORLD_WIDTH / 2f - GameConfig.CAR_WIDTH / 2f + INDICATOR_OFFSET,
                carPositionY
        );
        pEffectIndicatorRightBack = new ParticleEffect();
        pEffectIndicatorRightBack.load(Gdx.files.internal("particles/indicator.pe"), Gdx.files.internal(""));
        pEffectIndicatorRightBack.setPosition(
                GameConfig.WORLD_WIDTH / 2f + GameConfig.CAR_WIDTH / 2f - INDICATOR_OFFSET,
                carPositionY
        );
        pEffectSmoke = new ParticleEffect();
        pEffectSmoke.load(Gdx.files.internal("particles/smoke.pe"), Gdx.files.internal(("")));
        pEffectSmoke.setPosition(
                GameConfig.WORLD_WIDTH / 2f + GameConfig.CAR_WIDTH / 2f - INDICATOR_OFFSET,
                carPositionY
        );
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

        renderGameplay();
        renderHud();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) help = !help;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) startEngine = !startEngine;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) indicatorLeft = !indicatorLeft;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) indicatorRight = !indicatorRight;
    }

    private void update(float delta) {
        pEffectIndicatorLeftFront.update(delta);
        pEffectIndicatorRightFront.update(delta);
        pEffectIndicatorLeftBack.update(delta);
        pEffectIndicatorRightBack.update(delta);
        pEffectSmoke.update(delta);

        if (startEngine) {
            if (pEffectSmoke.isComplete())
                pEffectSmoke.reset();
        }
        if (indicatorLeft) {
            if (pEffectIndicatorLeftFront.isComplete()) {
                pEffectIndicatorLeftFront.reset();
            }
            if (pEffectIndicatorLeftBack.isComplete()) {
                pEffectIndicatorLeftBack.reset();
            }
            indicatorLeft = !indicatorLeft;
        }
        if (indicatorRight) {
            if (pEffectIndicatorRightFront.isComplete()) {
                pEffectIndicatorRightFront.reset();
            }
            if (pEffectIndicatorRightBack.isComplete()) {
                pEffectIndicatorRightBack.reset();
            }
            indicatorRight = !indicatorRight;
        }
    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(roadImg, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        batch.draw(carImg,                                  // texture
                GameConfig.CAR_START_X, carPositionY,       // x, y
                GameConfig.CAR_WIDTH / 2f, carHeight / 2f,  // originX, originY
                GameConfig.CAR_WIDTH, carHeight,            // width, height
                1f, 1f,                                     // scaleX, scaleY
                0f,                                         // rotation
                0, 0,                                       // srcX, srcY
                carImg.getWidth(), carImg.getHeight(),      // srcWidth, srcHeight
                false, false                                // flipX, flipY
        );
        pEffectSmoke.draw(batch);
        pEffectIndicatorLeftFront.draw(batch);
        pEffectIndicatorRightFront.draw(batch);
        pEffectIndicatorLeftBack.draw(batch);
        pEffectIndicatorRightBack.draw(batch);

        batch.end();
    }

    private void renderHud() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        if (help) {
            layout.setText(font, "Press [GREEN]space[] to turn on/off the engine");
            font.draw(batch, layout,
                    GameConfig.HUD_WIDTH / 2f - layout.width / 2f,  // position x
                    GameConfig.HUD_HEIGHT - layout.height           // position y
            );
            layout.setText(font, "Press the [GREEN]left arrow[] to turn on the left indicator");
            font.draw(batch, layout,
                    GameConfig.HUD_WIDTH / 2f - layout.width / 2f,
                    4 * layout.height
            );
            layout.setText(font, "Press the [GREEN]right arrow[] to turn on the right indicator");
            font.draw(batch, layout,
                    GameConfig.HUD_WIDTH / 2f - layout.width / 2f,
                    2 * layout.height
            );
        } else {
            layout.setText(font, "Press F1 for help");
            font.draw(batch, layout,
                    GameConfig.HUD_WIDTH / 2f - layout.width / 2f,
                    GameConfig.HUD_HEIGHT - layout.height
            );
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        roadImg.dispose();
        carImg.dispose();
        pEffectSmoke.dispose();
        pEffectIndicatorLeftFront.dispose();
        pEffectIndicatorRightFront.dispose();
        pEffectIndicatorLeftBack.dispose();
        pEffectIndicatorRightBack.dispose();
    }
}
