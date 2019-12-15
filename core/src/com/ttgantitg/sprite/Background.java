package com.ttgantitg.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.ttgantitg.base.Sprite;
import com.ttgantitg.math.Rect;

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
