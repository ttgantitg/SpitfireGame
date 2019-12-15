package com.androidgame.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.androidgame.math.Rect;
import com.androidgame.screen.GameScreen;

public class PlayButton extends ScaledTouchUpButton {

    private Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("playButton"));
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
        super.resize(worldBounds);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
