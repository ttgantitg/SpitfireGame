package com.androidgame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.androidgame.base.SpritesPool;
import com.androidgame.math.Rect;
import com.androidgame.sprite.game.Enemy;
import com.androidgame.sprite.game.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private Sound shootSound;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_plane_shoot.mp3"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shootSound, bulletPool, explosionPool, worldBounds, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
