package com.androidgame.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.androidgame.base.Sprite;
import com.androidgame.math.Rect;
import com.androidgame.pool.BulletPool;
import com.androidgame.pool.ExplosionPool;

public class Ship extends Sprite {

    protected Rect worldBounds;
    Vector2 v = new Vector2();
    BulletPool bulletPool;
    TextureRegion bulletRegion;
    float reloadInterval;
    float reloadTimer;
    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;
    Sound shootSound;
    Vector2 bulletV;
    float bulletHeight;
    int damage;
    int hp;
    ExplosionPool explosionPool;

    Ship() {
        super();
    }

    Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        shootSound.play(0.7f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void dispose() {
        shootSound.dispose();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if(damageTimer >= damageInterval){
            frame = 0;
        }
    }

    public void damage(int damage){
        frame = 1;
        damageTimer = 0f;
        hp -= damage;
        if(hp <= 0){
            hp = 0;
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }
}
