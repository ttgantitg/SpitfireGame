package com.ttgantitg.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.ttgantitg.base.SpritesPool;
import com.ttgantitg.math.Rect;
import com.ttgantitg.sprite.game.MainShip;
import com.ttgantitg.sprite.game.Enemy;

public class EnemyPool extends SpritesPool<com.ttgantitg.sprite.game.Enemy> {

    private Sound shootSound;
    private com.ttgantitg.pool.BulletPool bulletPool;
    private Rect worldBounds;
    private com.ttgantitg.pool.ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_plane_shoot.mp3"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
    }

    @Override
    protected com.ttgantitg.sprite.game.Enemy newObject() {
        return new Enemy(shootSound, bulletPool, explosionPool, worldBounds, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
