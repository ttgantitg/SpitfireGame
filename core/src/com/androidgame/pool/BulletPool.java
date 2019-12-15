package com.androidgame.pool;

import com.androidgame.base.SpritesPool;
import com.androidgame.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
