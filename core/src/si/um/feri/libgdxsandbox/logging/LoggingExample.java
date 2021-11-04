package si.um.feri.libgdxsandbox.logging;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;

public class LoggingExample extends ApplicationAdapter {

    /**
     * A simple logger that uses the {@link Application} logging facilities to output messages. The first parameter is a tag of
     * the message, and the second parameter is log level. Log levels:
     * {@link com.badlogic.gdx.utils.Logger#NONE} will mute all log output.
     * {@link com.badlogic.gdx.utils.Logger#ERROR} will only let error messages through.
     * {@link com.badlogic.gdx.utils.Logger#INFO} will let all non-debug messages through, and
     * {@link com.badlogic.gdx.utils.Logger#DEBUG} will let all messages through.
     */
    private static final Logger log = new Logger(LoggingExample.class.getSimpleName(), Logger.INFO);

    private static final float RENDER_LOG_INTERVAL = 2f;    // in seconds
    private float logTime;

    /**
     * Called when the {@link Application} is first created.
     * You should initialize all needed variables here so you can start the application.
     */
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);   // Sets the log level of application.

        log.info("I am a message printed from the 'create' method. :-)");

        logTime = 0f;    // 1 second = 1000 milliseconds
    }

    /**
     * Called when the {@link Application} is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to {@link #create()}. It is also called once just after the {@link #create()} method.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        log.info("I am a message printed from the 'resize' method. :-)");
    }

    /**
     * Called when the {@link Application} should render itself.
     * It runs every frame, and it is called 60 times per second by default.
     */
    @Override
    public void render() {
        float elapsedTime = (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f);

        // print log message once every two seconds
        if (elapsedTime - logTime > RENDER_LOG_INTERVAL) {
            log.info("I am a message printed from the 'render' method. :-)");
            logTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
        }
    }

    /**
     * Called when the {@link Application} is destroyed.
     * You have to release all native resources here.
     */
    @Override
    public void dispose() {
        log.info("I am a message printed from the 'dispose' method. :-)");
    }
}
