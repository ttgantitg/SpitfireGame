package com.ttgantitg.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.ttgantitg.math.Rect;
import com.ttgantitg.pool.BulletPool;
import com.ttgantitg.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;
    private final Vector2 v0 = new Vector2(0.5f, 0);
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, com.ttgantitg.math.Rect worldBouns) {
        super(atlas.findRegion("main_plane"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainPlane");
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/main_plane_shoot.mp3"));
        this.worldBounds = worldBouns;
        this.bulletPool = bulletPool;
        setHeightProportion(0.07f);
        this.reloadInterval = 0.3f;
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.hp = 100;
        this.explosionPool = explosionPool;
        startNewGame();
    }

    public void startNewGame(){
        stop();
        pos.x = worldBounds.pos.x;
        this.hp = 100;
        flushDestroy();
    }

    @Override
    public void resize(com.ttgantitg.math.Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP: //shoot by button UP
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return super.touchUp(touch, pointer);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    @Override
    public void shoot() {
        shootSound.play(0.5f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos.cpy().add(new Vector2(0f, getHalfHeight())), bulletV, bulletHeight, worldBounds, damage);
    }
}
