package com.ttgantitg;

import com.badlogic.gdx.Game;

import com.ttgantitg.screen.MenuScreen;

public class AndroidGame extends Game {
    @Override
    public void create(){
        setScreen(new MenuScreen(this));
    }
}
