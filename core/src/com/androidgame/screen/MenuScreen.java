package com.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.androidgame.base.Base2DScreen;
import com.androidgame.math.Rect;
import com.androidgame.sprite.Background;
import com.androidgame.sprite.menu.ExitButton;
import com.androidgame.sprite.menu.PlayButton;

public class MenuScreen extends Base2DScreen {

    private static final float EXIT_BUTTON_HEIGHT = 0.2f;
    private static final float PLAY_BUTTON_HEIGHT = 0.2f;
    private Texture bgTexture;
    private Background background;
    private TextureAtlas textureAtlas;
    private PlayButton btPlay;
    private ExitButton btExit;
    private Game game;
    private Music menuMusic;

    public MenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("textures/menu_bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("textures/planes.pack");
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music.mp3"));
        menuMusic.setLooping(true);
        btExit = new ExitButton(textureAtlas);
        btExit.setHeightProportion(EXIT_BUTTON_HEIGHT);
        btPlay = new PlayButton(textureAtlas, game);
        btPlay.setHeightProportion(PLAY_BUTTON_HEIGHT);
        menuMusic.setVolume(0.1f);
        menuMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {

    }

    private void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        btPlay.draw(batch);
        btExit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        btExit.resize(worldBounds);
        btPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        menuMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        btExit.touchDown(touch, pointer);
        btPlay.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        btExit.touchUp(touch, pointer);
        btPlay.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
