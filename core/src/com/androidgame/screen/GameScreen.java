package com.androidgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import java.util.List;
import com.androidgame.base.Base2DScreen;
import com.androidgame.math.Rect;
import com.androidgame.pool.BulletPool;
import com.androidgame.pool.EnemyPool;
import com.androidgame.pool.ExplosionPool;
import com.androidgame.sprite.Background;
import com.androidgame.sprite.Cloud;
import com.androidgame.sprite.game.Bullet;
import com.androidgame.sprite.game.Enemy;
import com.androidgame.sprite.game.MainShip;
import com.androidgame.sprite.menu.GameOver;
import com.androidgame.sprite.menu.RestartButton;
import com.androidgame.utils.EnemyEmitter;
import com.androidgame.utils.Font;

public class GameScreen extends Base2DScreen {

    private static final int CLOUDS_COUNT = 16;
    private static final String FRAGS = "FRAGS: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "LEVEL: ";
    private static final String YOUR_SCORE = "YOUR SCORE: ";
    private static final String BEST_SCORE = "BEST SCORE: ";
    private Texture bgTexture;
    private Background background;
    private TextureAtlas textureAtlas;
    private Cloud[] clouds;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private Music music;
    private State state;
    private enum State {PLAY, GAME_OVER, PAUSE, RESUME}
    private GameOver gameOver;
    private RestartButton restartButton;
    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();
    private StringBuilder sbYourScore = new StringBuilder();
    private StringBuilder sbBestScore = new StringBuilder();
    private int frags = 0;
    private Preferences prefs = Gdx.app.getPreferences("bestScore");
    private int bestScore;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/plane_sound.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();
        bgTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("textures/planes.pack");
        clouds = new Cloud[CLOUDS_COUNT];
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud(textureAtlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(textureAtlas);
        mainShip = new MainShip(textureAtlas, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(textureAtlas, enemyPool, worldBounds);
        gameOver = new GameOver(textureAtlas);
        restartButton = new RestartButton(textureAtlas, this);
        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setSize(0.02f);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        if (state == State.PLAY) {
            checkCollisions();
        }
        deleteAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Cloud cloud : clouds) {
            cloud.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        music.dispose();
        font.dispose();
        super.dispose();
    }

    private void update(float delta) {
        for (Cloud cloud : clouds) {
            cloud.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state){
            case PLAY:
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generate(delta, frags);
                break;
            case GAME_OVER:
                break;
        }
    }

    private void checkCollisions() {
        if (state == State.PLAY) {
            List<Enemy> enemyList = enemyPool.getActiveObjects();
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
                if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                    enemy.destroy();
                    mainShip.damage(enemy.getDamage());
                    return;
                }
            }
            List<Bullet> bulletList = bulletPool.getActiveObjects();

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                }
            }

            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(mainShip.getDamage());
                        if (enemy.isDestroyed()) {
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        if(mainShip.isDestroyed()){
            state = State.GAME_OVER;
        }
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Cloud cloud : clouds) {
            cloud.draw(batch);
        }
        switch (state){
            case PLAY:
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                if (frags >= bestScore){
                    prefs.putInteger("bestScore", frags);
                    prefs.flush();
                }
                gameOver.draw(batch);
                sbYourScore.setLength(0);
                sbBestScore.setLength(0);
                font.draw(batch, sbYourScore.append(YOUR_SCORE).append(frags), 0, 0, Align.center);
                if (frags >= bestScore) {
					font.draw(batch, sbBestScore.append(BEST_SCORE).append(frags), 0, -0.05f, Align.center);
				} else {
					font.draw(batch, sbBestScore.append(BEST_SCORE).append(bestScore), 0, -0.05f, Align.center);
				}
                restartButton.draw(batch);
                break;
            case PAUSE:
                pause();
                break;
            case RESUME:
                resume();
                this.state = State.PLAY;
                break;
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + 0.01f, worldBounds.getTop());
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - 0.01f, worldBounds.getTop(), Align.right);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAY) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAY) {
            mainShip.touchDown(touch, pointer);
        } else {
            restartButton.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAY) {
            mainShip.touchUp(touch, pointer);
        } else {
            restartButton.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void startNewGame(){
        state = State.PLAY;
        mainShip.startNewGame();
        frags = 0;
        enemyEmitter.setLevel(1);
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        bestScore = prefs.getInteger("bestScore");
    }

    @Override
    public void pause() {
        super.pause();
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        super.resume();
        this.state = State.RESUME;
    }
}

