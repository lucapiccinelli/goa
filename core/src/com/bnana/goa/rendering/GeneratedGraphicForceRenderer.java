package com.bnana.goa.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.tween.PercentageManager;
import com.bnana.goa.tween.ShapeRendererAccessor;
import com.bnana.goa.utils.ScaleManager;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Luca on 9/21/2015.
 */
public class GeneratedGraphicForceRenderer implements ForceRenderer{
    private final ShapeRenderer shapeRenderer;
    private final ScaleManager sm;
    private PercentageManager percentageManager;
    private TweenManager tweenManager;
    private float lastMagnitude;
    private boolean setOffAnimation;
    private boolean setOnAnimation;
    private TweenManager positionTweenManager;

    public GeneratedGraphicForceRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {
        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
        lastMagnitude = 0;

        setOffAnimation = false;
        setOnAnimation = false;

        setTweenAnimation();
    }

    private void setTweenAnimation() {
        this.percentageManager = new PercentageManager(0.1f);
        shapeRenderer.setColor(1f, 1f, 0.5f, 0f);
        tweenManager = new TweenManager();

        positionTweenManager = new TweenManager();
    }

    final TweenCallback animationCallback = new TweenCallback() {
        @Override
        public void onEvent(int i, BaseTween<?> baseTween) {
            Timeline.createParallel()
                    .push(Tween.to(percentageManager, 0, 0.9f).target(1).repeatYoyo(1, 0))
                    .push(Tween.to(shapeRenderer, ShapeRendererAccessor.COLOR_ALPHA, 0.9f).target(0.35f).repeatYoyo(1, 0))
                    .setCallback(animationCallback)
                    .start(tweenManager);
        }
    };

    @Override
    public void renderForce(ForceField forceField, Vector2 position, float magnitude){
        if (magnitude == 0) {
            if(shapeRenderer.getColor().a > 0f) {
                if(!setOffAnimation) {
                    setOnAnimation = false;
                    setOffAnimation = true;

                    tweenManager.killAll();

                    Tween.to(shapeRenderer, ShapeRendererAccessor.COLOR_ALPHA, 0.3f).target(0).start(tweenManager);
                }
                magnitude = lastMagnitude;
            }
            else {
                if(!setOnAnimation) {
                    setOnAnimation = true;
                    setOffAnimation = false;

                    tweenManager.killAll();
                    percentageManager.setPercentage(0.8f);
                    Timeline.createParallel()
                            .push(Tween.to(shapeRenderer, ShapeRendererAccessor.COLOR_ALPHA, 0.3f).target(0.85f))
                            .push(Tween.to(percentageManager, 0, 0.2f).target(0.8f))
                            .setCallback(animationCallback)
                            .start(tweenManager);
                }
                lastMagnitude = magnitude;
                return;
            }
        }

        lastMagnitude = magnitude;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float unscaledRadius = MathUtils.log(5, 1 + Math.abs(magnitude));

        tweenManager.update(Gdx.graphics.getDeltaTime());
        positionTweenManager.update(Gdx.graphics.getDeltaTime());

        float radius = percentageManager.scale(unscaledRadius);
        shapeRenderer.circle(position.x, position.y, radius, 50);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
