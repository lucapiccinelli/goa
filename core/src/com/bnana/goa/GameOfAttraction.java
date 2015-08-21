package com.bnana.goa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bnana.goa.screen.GameScreen;

public class GameOfAttraction extends Game {
    private Screen gameScreen;

    @Override
	public void create () {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
	}

	@Override
	public void render () {
        super.render();
	}
}
