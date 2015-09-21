package com.bnana.goa.force;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.rendering.ForceRenderer;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface ForceField {
    void update(Point2D.Float[] positions, float[] magnitudes);

    void apply(Body body);

    void render(ForceRenderer forceRenderer);

    float valueAtDistance(float distance);
}
