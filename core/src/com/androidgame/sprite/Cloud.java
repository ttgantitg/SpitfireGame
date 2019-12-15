package com.androidgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.androidgame.base.Sprite;
import com.androidgame.math.Rect;
import com.androidgame.math.Rnd;

public class Cloud extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Cloud(TextureAtlas atlas) {
        super(atlas.findRegion("cloud"));
        setHeightProportion(0.1f);
        v.set(Rnd.nextFloat(-0.001f, 0.001f), Rnd.nextFloat(-0.1f, -0.1f));
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    private void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
