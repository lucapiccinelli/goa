package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.rendering.ForceRenderer;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface ForceField {
    void update(Array<Vector2> positions, Array<Float> magnitudes);

    void apply(Body body);

    void render(ForceRenderer forceRenderer);

    float valueAtDistance(float distance);

    float valueAtPoint(Vector2 position);

    Vector2 direction(Vector2 position);

    Vector2 forceAtPoint(Vector2 bodyPosition);
}
