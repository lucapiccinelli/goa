package com.bnana.goa.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.tween.PercentageManager;
import com.bnana.goa.utils.ScaleManager;

import java.sql.Time;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Bounce;

/**
 * Created by Luca on 9/21/2015.
 */
public class FlatGeneratedGraphicCellRenderer implements CellRenderer {
    private final TweenManager animationManager;
    private ShapeRenderer shapeRenderer;
    private ScaleManager sm;
    private PercentageManager widthPercentageManager;
    private PercentageManager heightPercentageManager;
    private final Dictionary<Cell, PercentageManager[]> percentageManagers;
    private float alpha;

    public FlatGeneratedGraphicCellRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {
        widthPercentageManager = new PercentageManager(1);
        heightPercentageManager = new PercentageManager(1);
        animationManager = new TweenManager();

        percentageManagers = new Hashtable<Cell, PercentageManager[]>();

        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
        alpha = 0.6f;

        animate();
    }

    private void animate() {
        Tween.call(animateCallback).start(animationManager);
    }

    private final TweenCallback animateCallback = new TweenCallback() {
        @Override
        public void onEvent(int i, BaseTween<?> baseTween) {
            float delay = MathUtils.random(10, 20);
            float duration = MathUtils.random(1.5f, 2f);

            float targetWidth = MathUtils.random() < 0.5 ? MathUtils.random(0.85f, 0.9f) : MathUtils.random(1.1f, 1.15f);
            float targetHeight = MathUtils.random() < 0.5 ? MathUtils.random(0.85f, 0.9f) : MathUtils.random(1.1f, 1.15f);

            Timeline.createParallel()
                    .push(Tween.to(widthPercentageManager, 0, duration).target(targetWidth).ease(Bounce.IN).repeatYoyo(1, 0))
                    .push(Tween.to(heightPercentageManager, 0, duration).target(targetHeight).ease(Bounce.IN).repeatYoyo(1, 0))
                    .delay(delay)
                    .setCallback(animateCallback)
                    .start(animationManager);
        }
    };

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        setPercentageManagers(cell);

        animationManager.update(Gdx.graphics.getDeltaTime());

        cell.render(this);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float width = widthPercentageManager.scale(Math.abs(density) * 2);
        float height = heightPercentageManager.scale(Math.abs(density) * 2);
        shapeRenderer.ellipse(position.x - width * 0.5f, position.y - height * 0.5f, width, height, 30);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.getColor().a = 1;
        shapeRenderer.ellipse(position.x - width * 0.5f, position.y - height * 0.5f, width, height, 30);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void setPercentageManagers(Cell cell) {
        PercentageManager[] managers = percentageManagers.get(cell);
        if (managers == null) {
            widthPercentageManager = new PercentageManager(1);
            heightPercentageManager = new PercentageManager(1);
            managers = new PercentageManager[]{widthPercentageManager, heightPercentageManager};

            percentageManagers.put(cell, managers);
        }

        widthPercentageManager = managers[0];
        heightPercentageManager = managers[1];
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        this.use(cell, position, density);
    }

    @Override
    public void renderAttractorOffCell(AttractorOffCell attractorOffCell, Vector2 position, float density) {
        shapeRenderer.setColor(0.776f, 0.313f, 0.313f, alpha);
    }

    @Override
    public void renderRepulsorOffCell(RepulsorOffCell repulsorOffCell, Vector2 position, float density) {
        shapeRenderer.setColor(0.545f, 0.698f, 0.823f, alpha);
    }

    @Override
    public void renderAttractorOnCell(AttractorOnCell attractorOnCell, Vector2 position, float density) {
        shapeRenderer.setColor(1, 0, 0, alpha);
    }

    @Override
    public void renderRepulsorOnCell(RepulsorOnCell repulsorOnCell, Vector2 position, float density) {
        shapeRenderer.setColor(0, 0, 1, alpha);
    }

    @Override
    public void renderWanderingCell(WanderingCell wanderingCell, Vector2 position, float density) {
        shapeRenderer.setColor(0.58f, 0.823f, 0.545f, alpha);
    }
}
