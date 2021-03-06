package com.androidgame.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.androidgame.screen.GameScreen;

public class RestartButton extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public RestartButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("restartButton"));
        setHeightProportion(0.07f);
        setBottom(-0.35f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
