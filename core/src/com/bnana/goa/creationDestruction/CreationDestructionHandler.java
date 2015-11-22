package com.bnana.goa.creationDestruction;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * Created by Luca on 11/22/2015.
 */
public interface CreationDestructionHandler {
    void destroyBody(Body body);
    void destroyJoint(Joint joint);
    void register(CreationDestructionSubscriber creationSubscriber);
}
