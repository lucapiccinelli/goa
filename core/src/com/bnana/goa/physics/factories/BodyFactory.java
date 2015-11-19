package com.bnana.goa.physics.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Luca on 11/19/2015.
 */
public interface BodyFactory {
    Body create(Vector2 position, float density);
}
