package com.androidgame.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.androidgame.base.Sprite;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("game_over"));
        setHeightProportion(0.15f);
        setBottom(0.15f);
    }
}
