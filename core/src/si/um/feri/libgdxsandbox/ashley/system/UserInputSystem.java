package si.um.feri.libgdxsandbox.ashley.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;

import si.um.feri.libgdxsandbox.ashley.component.MovementComponent;
import si.um.feri.libgdxsandbox.ashley.component.PositionComponent;

/**
 * A simple EntitySystem that iterates over the entities described by the family and calls processEntity()
 * for each entity from family every time the EntitySystem is updated.
 */
public class UserInputSystem extends IteratingSystem {

    private static final Logger log = new Logger(UserInputSystem.class.getSimpleName(), Logger.DEBUG);

    /**
     * Provides super fast {@link Component} retrieval from {@Link Entity} objects.
     */
    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    /**
     * The family of entities iterated over in this System.
     */
    private static final Family FAMILY = Family.all(
            MovementComponent.class,
            PositionComponent.class
    ).get();

    public UserInputSystem() {
        super(FAMILY);
    }

    /**
     * Called when this EntitySystem is added to an {@link Engine}.
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        log.debug("added to engine");
    }

    /**
     * This method is called on every entity from the family on every update call of the EntitySystem.
     *
     * @param entity    The current Entity being processed
     * @param deltaTime The delta time between the last and current frame
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        PositionComponent position = entity.getComponent(PositionComponent.class);  // This runs in O(logn).
        PositionComponent position = POSITION.get(entity);  // This runs in O(1).

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            position.x--;
            log.debug("Input.Keys.LEFT: position.x = " + position.x);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            position.x++;
            log.debug("Input.Keys.RIGHT: position.x = " + position.x);
        }
    }
}
