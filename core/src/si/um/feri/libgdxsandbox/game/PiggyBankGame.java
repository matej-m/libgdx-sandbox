package si.um.feri.libgdxsandbox.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class PiggyBankGame extends ApplicationAdapter {
    private SpriteBatch batch;

    private Texture piggyImg;
    private Texture coinImg;
    private Texture hammerImg;
    private Sound piggyVoice;
    private Sound coinCollect;

    private BitmapFont font;

    private Rectangle piggy;
    private Array<Rectangle> coins;
    private Array<Rectangle> hammers;

    private float coinSpawnTime;  // in seconds
    private int coinsCollected;

    private float hammerSpawnTime;  // in seconds
    private int health;

    private static final float PIGGY_SPEED = 250f;
    private static final float COIN_SPEED = 100f;
    private static final float COIN_SPAWN_TIME = 4f;    // in seconds
    private static final float HAMMER_SPEED = 150f;
    private static final float HAMMER_DAMAGE = 25f;
    private static final float HAMMER_SPAWN_TIME = 2f;  // in seconds

    @Override
    public void create() {
        batch = new SpriteBatch();

        piggyImg = new Texture("images/piggy-bank.png");
        coinImg = new Texture("images/coin.png");
        hammerImg = new Texture("images/hammer.png");
        piggyVoice = Gdx.audio.newSound(Gdx.files.internal("sounds/wreee.wav"));
        coinCollect = Gdx.audio.newSound(Gdx.files.internal("sounds/coin-collect.wav"));

        font = new BitmapFont(Gdx.files.internal("fonts/arial-32.fnt"));

        piggy = new Rectangle();
        piggy.x = Gdx.graphics.getWidth() / 2f - piggyImg.getWidth() / 2f;
        piggy.y = 20f;
        piggy.width = piggyImg.getWidth();
        piggy.height = piggyImg.getHeight();

        coins = new Array<>();
        coinsCollected = 0;
        spawnCoin();

        hammers = new Array<>();
        health = 100;
        spawnHammer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0.5f, 0, 1);

        if (health > 0) {
            handleInput();
            update(Gdx.graphics.getDeltaTime());
        }

        batch.begin();

        draw();

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveLeft(Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveRight(Gdx.graphics.getDeltaTime());
    }

    private void update(float delta) {
        float elapsedTime = (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f);
        if (elapsedTime - coinSpawnTime > COIN_SPAWN_TIME) spawnCoin();
        if (elapsedTime - hammerSpawnTime > HAMMER_SPAWN_TIME) spawnHammer();

        for (Iterator<Rectangle> it = coins.iterator(); it.hasNext(); ) {
            Rectangle coin = it.next();
            coin.y -= COIN_SPEED * delta;
            if (coin.y + coinImg.getHeight() < 0) {
                it.remove();
            }
            if (coin.overlaps(piggy)) {
                coinsCollected++;
                coinCollect.play();
                it.remove();
            }
        }

        for (Iterator<Rectangle> it = hammers.iterator(); it.hasNext(); ) {
            Rectangle hammer = it.next();
            hammer.y -= HAMMER_SPEED * delta;
            if (hammer.y + hammerImg.getHeight() < 0) {
                it.remove();
            }
            if (hammer.overlaps(piggy)) {
                health -= HAMMER_DAMAGE;
                piggyVoice.play();
                it.remove();
            }
        }
    }

    private void draw() {
        if (health <= 0) {
            font.setColor(Color.RED);
            font.draw(batch,
                    "GAME OVER",
                    20f, Gdx.graphics.getHeight() - 20f
            );
            return;
        }
        for (Rectangle coin : coins) {
            batch.draw(coinImg, coin.x, coin.y);
        }
        for (Rectangle hammer : hammers) {
            batch.draw(hammerImg, hammer.x, hammer.y);
        }
        batch.draw(piggyImg, piggy.x, piggy.y);

        font.setColor(Color.RED);
        font.draw(batch,
                "HEALTH: " + health,
                20f, Gdx.graphics.getHeight() - 20f
        );

        font.setColor(Color.YELLOW);
        font.draw(batch,
                "SCORE: " + coinsCollected,
                20f, Gdx.graphics.getHeight() - 60f
        );
    }

    private void moveLeft(float delta) {
        piggy.x -= PIGGY_SPEED * delta;
        if (piggy.x < 0)
            piggy.x = 0f;
    }

    private void moveRight(float delta) {
        piggy.x += PIGGY_SPEED * delta;
        if (piggy.x > Gdx.graphics.getWidth() - piggyImg.getWidth())
            piggy.x = Gdx.graphics.getWidth() - piggyImg.getWidth();
    }

    private void spawnCoin() {
        Rectangle coin = new Rectangle();
        coin.x = MathUtils.random(0f, Gdx.graphics.getWidth() - piggyImg.getWidth());
        coin.y = Gdx.graphics.getHeight();
        coin.width = coinImg.getWidth();
        coin.height = coinImg.getHeight();
        coins.add(coin);
        coinSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;    // 1 second = 1000 miliseconds
    }

    private void spawnHammer() {
        Rectangle hammer = new Rectangle();
        hammer.x = MathUtils.random(0f, Gdx.graphics.getWidth() - hammerImg.getWidth());
        hammer.y = Gdx.graphics.getHeight();
        hammer.width = hammerImg.getWidth();
        hammer.height = hammerImg.getHeight();
        hammers.add(hammer);
        hammerSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        piggyImg.dispose();
        coinImg.dispose();
        hammerImg.dispose();
        piggyVoice.dispose();
        coinCollect.dispose();
    }
}
