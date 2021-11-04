package si.um.feri.libgdxsandbox.scene2d.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.libgdxsandbox.assets.AssetDescriptors;
import si.um.feri.libgdxsandbox.assets.RegionNames;
import si.um.feri.libgdxsandbox.scene2d.Scene2dExample;
import si.um.feri.libgdxsandbox.scene2d.config.GameConfig;

public class IntroScreen extends ScreenAdapter {

    public static final float INTRO_DURATION_IN_SEC = 3f;   // duration of the (intro) animation

    private Viewport viewport;

    private final Scene2dExample game;
    private final AssetManager assetManager;
    private TextureAtlas scene2dAtlas;

    private float duration;

    private Stage stage;

    public IntroScreen(Scene2dExample game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        duration = 0f;

        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.SCENE2D);
        assetManager.finishLoading();   // blocks until all assets are loaded

        scene2dAtlas = assetManager.get(AssetDescriptors.SCENE2D);

        stage.addActor(createKeyhole());
        stage.addActor(createAnimation());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        duration += delta;

        // go to the MenuScreen after INTRO_DURATION_IN_SEC seconds
        if (duration > INTRO_DURATION_IN_SEC) {
            game.setScreen(new MenuScreen(game));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createKeyhole() {
        Image keyhole = new Image(scene2dAtlas.findRegion(RegionNames.KEYHOLE));
        // position the image to the center of the window
        keyhole.setPosition(viewport.getWorldWidth() / 2f - keyhole.getWidth() / 2f,
                viewport.getWorldHeight() / 2f - keyhole.getHeight() / 2f);
        return keyhole;
    }

    private Actor createAnimation() {
        Image key = new Image(scene2dAtlas.findRegion(RegionNames.KEY));

        // set positions x, y to center the image to the center of the window
        float posX = (viewport.getWorldWidth() / 2f) - key.getWidth() / 2f;
        float posY = (viewport.getWorldHeight() / 2f) - key.getHeight() / 2f;

        key.setOrigin(Align.center);
        key.addAction(
                /* animationDuration = Actions.sequence + Actions.rotateBy + Actions.scaleTo
                                      = 1.5 + 1 + 0.5 = 3 sec */
                Actions.sequence(
                        Actions.parallel(
                                Actions.rotateBy(1080, 1.5f),   // rotate the image three times
                                Actions.moveTo(posX, posY, 1.5f)   // // move image to the center of the window
                        ),
                        Actions.rotateBy(-360, 1),  // rotate the image for 360 degrees to the left side
                        Actions.scaleTo(0, 0, 0.5f),    // "minimize"/"hide" image
                        Actions.removeActor()   // // remove image
                )
        );

        return key;
    }
}
