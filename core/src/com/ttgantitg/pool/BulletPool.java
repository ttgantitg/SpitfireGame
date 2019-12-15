package com.ttgantitg.pool;

import com.ttgantitg.base.SpritesPool;
import com.ttgantitg.sprite.game.Bullet;

public class BulletPool extends SpritesPool<com.ttgantitg.sprite.game.Bullet> {
    @Override
    protected com.ttgantitg.sprite.game.Bullet newObject() {
        return new Bullet();
    }
}
