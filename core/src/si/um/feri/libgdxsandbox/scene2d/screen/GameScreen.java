package si.um.feri.libgdxsandbox.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.libgdxsandbox.assets.AssetDescriptors;
import si.um.feri.libgdxsandbox.assets.RegionNames;
import si.um.feri.libgdxsandbox.scene2d.Scene2dExample;
import si.um.feri.libgdxsandbox.scene2d.config.GameConfig;

public class GameScreen extends ScreenAdapter {

    private final Scene2dExample game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private GlyphLayout layout;
    private BitmapFont font;

    private Viewport viewport;
    private Viewport hudViewport;

    private TextureRegion starRegion;

    public GameScreen(Scene2dExample game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        TextureAtlas scene2dAtlas = assetManager.get(AssetDescriptors.SCENE2D);
        starRegion = scene2dAtlas.findRegion(RegionNames.STAR);

        layout = new GlyphLayout();
        font = new BitmapFont();
        font.getData().markupEnabled = true;

        layout.setText(font, "Press [GREEN]'space'[] to return to the menu screen");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0.25f, 0f, 0f);

        handleInput();

        renderGameplay();
        renderHud();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) game.setScreen(new MenuScreen(game));
    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(starRegion,
                GameConfig.WORLD_WIDTH / 2f - GameConfig.STAR_SIZE / 2f, GameConfig.WORLD_HEIGHT / 2f - GameConfig.STAR_SIZE / 2f,
                GameConfig.STAR_SIZE, GameConfig.STAR_SIZE
        );

        batch.end();
    }

    private void renderHud() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        font.draw(batch, layout, GameConfig.HUD_WIDTH / 2f - layout.width / 2f, 20f);

        batch.end();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
