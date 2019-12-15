package com.ttgantitg.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ttgantitg.math.Rect;

public class ExitButton extends ScaledTouchUpButton {

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("buttonExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.02f);
        setLeft(worldBounds.getLeft() + 0.02f);
        super.resize(worldBounds);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
