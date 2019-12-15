package com.ttgantitg.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ttgantitg.screen.GameScreen;

public class RestartButton extends ScaledTouchUpButton {

    private com.ttgantitg.screen.GameScreen gameScreen;

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
