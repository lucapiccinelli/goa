package com.bnana.goa.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.utils.wrappers.WorldWrapper;

/**
 * Created by Luca on 8/27/2015.
 */
public class BodyWrapper {
    public static Body getNewBody() {
        return WorldWrapper.GetNewWorldZeroGravity().createBody(new BodyDef());
    }
}
