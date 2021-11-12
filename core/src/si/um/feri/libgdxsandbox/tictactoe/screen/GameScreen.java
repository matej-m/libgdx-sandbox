package si.um.feri.libgdxsandbox.tictactoe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.libgdxsandbox.assets.AssetDescriptors;
import si.um.feri.libgdxsandbox.assets.RegionNames;
import si.um.feri.libgdxsandbox.tictactoe.CellActor;
import si.um.feri.libgdxsandbox.tictactoe.CellState;
import si.um.feri.libgdxsandbox.tictactoe.TicTacToeGame;
import si.um.feri.libgdxsandbox.tictactoe.common.GameManager;
import si.um.feri.libgdxsandbox.tictactoe.config.GameConfig;

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final TicTacToeGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;

    private Stage gameplayStage;
    private Stage hudStage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private CellState move = GameManager.INSTANCE.getInitMove();
    private Image infoImage;

    public GameScreen(TicTacToeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        gameplayStage.addActor(createGrid(3, 3, 5));
        hudStage.addActor(createInfo());
        hudStage.addActor(createBackButton());

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, hudStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(195 / 255f, 195 / 255f, 195 / 255f, 0f);

        // update
        gameplayStage.act(delta);
        hudStage.act(delta);

        // draw
        gameplayStage.draw();
        hudStage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        hudStage.dispose();
    }

    private Actor createGrid(int rows, int columns, final float cellSize) {
        final Table table = new Table();
        table.setDebug(false);   // turn on all debug lines (table, cell, and widget)

        final Table grid = new Table();
        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

        final TextureRegion emptyRegion = gameplayAtlas.findRegion(RegionNames.CELL_EMPTY);
        final TextureRegion xRegion = gameplayAtlas.findRegion(RegionNames.CELL_X);
        final TextureRegion oRegion = gameplayAtlas.findRegion(RegionNames.CELL_O);

        if (move == CellState.X) {
            infoImage = new Image(xRegion);
        } else if (move == CellState.O) {
            infoImage = new Image(oRegion);
        }

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final CellActor cell = new CellActor(emptyRegion);
                cell.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        final CellActor clickedCell = (CellActor) event.getTarget(); // it will be an image for sure :-)
                        if (clickedCell.isEmpty()) {
                            switch (move) {
                                case X:
                                    clickedCell.setState(move);
                                    clickedCell.setDrawable(xRegion);
                                    infoImage.setDrawable(new TextureRegionDrawable(oRegion));
                                    move = CellState.O;
                                    break;
                                case O:
                                    clickedCell.setState(move);
                                    clickedCell.setDrawable(oRegion);
                                    infoImage.setDrawable(new TextureRegionDrawable(xRegion));
                                    move = CellState.X;
                                    break;
                            }
                        }
                        log.debug("clicked");
                    }
                });
                grid.add(cell);
            }
            grid.row();
        }

        table.add(grid).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back", skin);
        backButton.setWidth(100);
        backButton.setPosition(GameConfig.HUD_WIDTH / 2f - backButton.getWidth() / 2f, 20f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }

    private Actor createInfo() {
        final Table table = new Table();
        table.add(new Label("Turn: ", skin));
        table.add(infoImage).size(30).row();
        table.center();
        table.pack();
        table.setPosition(
                GameConfig.HUD_WIDTH / 2f - table.getWidth() / 2f,
                GameConfig.HUD_HEIGHT - table.getHeight() - 20f
        );
        return table;
    }
}
