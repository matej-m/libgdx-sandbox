package si.um.feri.libgdxsandbox.ashley.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Logger;

/**
 * A passive {@link EntitySystem} that contains logic that needs to be executed at the start
 * of the game (when {@link StartUpSystem} is added to the {@link Engine}.
 */
public class StartUpSystem extends EntitySystem {

    private static final Logger log = new Logger(StartUpSystem.class.getSimpleName(), Logger.DEBUG);

    private EntityFactorySystem factory;

    public StartUpSystem() {
        setProcessing(false);   // the engine won't process the system; it's a passive system
    }

    /**
     * Called when this EntitySystem is added to an {@link Engine}.
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
        onStartUp();
    }

    private void onStartUp() {
        log.debug("creating movable rectangle...");
        factory.createMovableRectangle();
    }
}
