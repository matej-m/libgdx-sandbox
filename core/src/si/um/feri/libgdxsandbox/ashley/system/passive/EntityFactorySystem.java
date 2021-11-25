package si.um.feri.libgdxsandbox.ashley.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;

import si.um.feri.libgdxsandbox.ashley.component.DimensionComponent;
import si.um.feri.libgdxsandbox.ashley.component.MovementComponent;
import si.um.feri.libgdxsandbox.ashley.component.PositionComponent;

/**
 * A passive {@link EntitySystem} that contains methods for creating and adding entities to an {@link Engine}.
 */
public class EntityFactorySystem extends EntitySystem {

    private static final Logger log = new Logger(EntityFactorySystem.class.getSimpleName(), Logger.DEBUG);

    private PooledEngine engine;

    public EntityFactorySystem() {
        setProcessing(false);   // the engine won't process the system; it's a passive system
    }

    /**
     * Called when this EntitySystem is added to an {@link Engine}.
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        log.debug("added to engine");
        this.engine = (PooledEngine) engine;
    }

    public void createRectangle() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, 10);
        position.y = MathUtils.random(0, 10);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = 10;
        dimension.height = 10;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void createMovableRectangle() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, 10);
        position.y = MathUtils.random(0, 10);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = 10;
        dimension.height = 10;

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(movement);

        engine.addEntity(entity);
    }
}
