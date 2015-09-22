package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.utils.ScaleManager;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by Luca on 9/21/2015.
 */
public class GeneratedGraphicForceRenderer implements ForceRenderer{
    private final ShapeRenderer shapeRenderer;
    private final ScaleManager sm;

    public GeneratedGraphicForceRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {
        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
    }

    @Override
    public void renderForce(ForceField forceField, Vector2 position, float magnitude){
        if (magnitude == 0) return;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int distanceGrow = 1;
        int distance = 1;
        float forceValue = 0;
        Array<Float> values = new Array<Float>();
        float max = Float.MIN_VALUE;
        do {
            forceValue = Math.abs(forceField.valueAtDistance(distance));
            if(forceValue > max) max = forceValue;
            values.add(forceValue);
            distance += distanceGrow;
        }while (forceValue > 0.1);

        float scale = 1f / max;
        for (int i = values.size - 1; i >= 0; i--){
            shapeRenderer.setColor(1f, 1f, 1 - values.get(i) * scale, 1f);
            shapeRenderer.circle(position.x, position.y, distance);
            distance -= distanceGrow;
        }

        shapeRenderer.end();
    }
}
