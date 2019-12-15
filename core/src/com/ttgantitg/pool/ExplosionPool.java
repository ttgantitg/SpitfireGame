package com.ttgantitg.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.ttgantitg.base.SpritesPool;
import com.ttgantitg.sprite.game.Explosion;

public class ExplosionPool extends SpritesPool<com.ttgantitg.sprite.game.Explosion> {

    private TextureRegion region;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas) {
        this.region = atlas.findRegion("explosion");
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected com.ttgantitg.sprite.game.Explosion newObject() {
        return new Explosion(region, 8, 8, 59, explosionSound);
    }

    public void dispose() {
        explosionSound.dispose();
        super.dispose();
    }
}
