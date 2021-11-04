package si.um.feri.libgdxsandbox.pooling;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Circle implements Pool.Poolable {

    private static final float CIRCLE_SPEED = 5f;

    private final Color color;
    private final Vector2 position;
    private boolean alive;

    /**
     * Bullet constructor. Just initialize variables.
     */
    public Circle() {
        color = new Color(Color.WHITE);
        position = new Vector2();
        alive = false;
    }

    public Color getColor() {
        return color;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Initialize the bullet. Call this method after getting a bullet from the pool.
     */
    public void init(float posX, float posY, boolean fromPool) {
        if (fromPool) {
            color.set(Color.YELLOW);
        } else {
            color.set(Color.WHITE);
        }
        position.set(posX, posY);
        alive = true;
    }

    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
    @Override
    public void reset() {
        position.set(MathUtils.random(ObjectPoolingExample.WORLD_WIDTH / 2f, ObjectPoolingExample.WORLD_WIDTH - ObjectPoolingExample.CIRCLE_HIDE_MARGIN),
                -ObjectPoolingExample.CIRCLE_HIDE_MARGIN);
        alive = false;
    }

    /**
     * Method called each frame, which updates the bullet.
     */
    public void update(float delta) {
        // update bullet position
        position.add(0, delta * CIRCLE_SPEED);

        // if bullet is out of screen, set it to dead
        if (isOutOfScreen()) alive = false;
    }

    private boolean isOutOfScreen() {
        return position.y >= ObjectPoolingExample.WORLD_HEIGHT + ObjectPoolingExample.CIRCLE_HIDE_MARGIN;
    }
}
