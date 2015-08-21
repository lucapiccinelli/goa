package com.bnana.goa.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.stage.OverviewStage;

/**
 * Created by Luca on 8/21/2015.
 */
public class GameScreen implements Screen {
    private Stage overviewStage;
    private GameOfAttraction game;

    private Stage currentStage;

    public GameScreen(GameOfAttraction game) {
        this.game = game;
        overviewStage = new OverviewStage(game);

        currentStage = overviewStage;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentStage.draw();
        currentStage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        overviewStage.dispose();
    }
}
