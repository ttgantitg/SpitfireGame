package com.androidgame;

import com.badlogic.gdx.Game;

import com.androidgame.screen.MenuScreen;

public class AndroidGame extends Game {
    @Override
    public void create(){
        setScreen(new MenuScreen(this));
    }
}
