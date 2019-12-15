package com.androidgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.androidgame.base.Sprite;
import com.androidgame.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.pos);
        setHeightProportion(worldBounds.getHeight());
    }
}
