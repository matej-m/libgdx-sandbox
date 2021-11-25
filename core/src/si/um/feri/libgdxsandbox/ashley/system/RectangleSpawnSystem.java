package si.um.feri.libgdxsandbox.ashley.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;

import si.um.feri.libgdxsandbox.ashley.system.passive.EntityFactorySystem;

/**
 * A simple {@link EntitySystem} that does not run its update logic every call to {@link EntitySystem#update(float)}, but after a
 * given interval. The actual logic should be placed in {@link IntervalSystem#updateInterval()}.
 */
public class RectangleSpawnSystem extends IntervalSystem {

    private static final Logger log = new Logger(RectangleSpawnSystem.class.getSimpleName(), Logger.DEBUG);

    private EntityFactorySystem factory;

    /**
     * @param interval time in seconds between calls to {@link IntervalSystem#updateInterval()}.
     */
    public RectangleSpawnSystem(float interval) {
        super(interval);
    }

    /**
     * Called when this EntitySystem is added to an {@link Engine}.
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        log.debug("added to engine");
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    /**
     * The processing logic of the system should be placed here.
     */
    @Override
    protected void updateInterval() {
        log.debug("creating rectangle...");
        factory.createRectangle();
    }
}
