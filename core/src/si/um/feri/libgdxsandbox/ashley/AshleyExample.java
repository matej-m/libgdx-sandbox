package si.um.feri.libgdxsandbox.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.libgdxsandbox.ashley.system.RectangleSpawnSystem;
import si.um.feri.libgdxsandbox.ashley.system.UserInputSystem;
import si.um.feri.libgdxsandbox.ashley.system.passive.EntityFactorySystem;
import si.um.feri.libgdxsandbox.ashley.system.passive.StartUpSystem;

public class AshleyExample extends ApplicationAdapter {

    private static final Logger log = new Logger(AshleyExample.class.getSimpleName(), Logger.DEBUG);

    private static final float INTERVAL = 5;   // In seconds.

    private float duration = 0;

    private Viewport viewport;
    private SpriteBatch batch;

    // The heart of the Entity framework. It is responsible for keeping track of entities
    // and managing EntitySystem objects.
    private Engine engine;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        viewport = new FitViewport(80, 60); // world units
        batch = new SpriteBatch();

        engine = new PooledEngine();

        // The order of adding systems is important!
        engine.addSystem(new EntityFactorySystem());
        engine.addSystem(new StartUpSystem());
        engine.addSystem(new RectangleSpawnSystem(INTERVAL));
        engine.addSystem(new UserInputSystem());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        engine.update(Gdx.graphics.getDeltaTime()); // Updated all systems in the engine.

        logEngineSize();
    }

    @Override
    public void dispose() {
        batch.dispose();
        engine.removeAllEntities();
    }

    private void logEngineSize() {
        duration += Gdx.graphics.getDeltaTime();
        if (duration >= INTERVAL) {
            duration = 0;
            log.debug("engine.getEntities().size() = " + engine.getEntities().size());
        }
    }
}
