package com.bnana.goa.utils.wrappers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by luca.piccinelli on 27/08/2015.
 */
public class WorldWrapper {
    public static World GetNewWorldZeroGravity() {
        return new World(new Vector2(0, 0), true);
    }

    public static int countBodies(World world){
        return world.getBodyCount();
    }
}
