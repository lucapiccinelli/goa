package com.bnana.goa.rendering;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.force.ForceField;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/21/2015.
 */
public interface ForceRenderer {
    void renderForce(ForceField forceField, Vector2 position, float magnitude);
}
