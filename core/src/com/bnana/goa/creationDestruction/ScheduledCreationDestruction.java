package com.bnana.goa.creationDestruction;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Luca on 11/22/2015.
 */
public class ScheduledCreationDestruction implements CreationDestructionHandler {
    private World world;
    private Array<Body> bodiesToDestroy;
    private Array<Joint> jointsToDestroy;
    private Array<CreationDestructionSubscriber> creationDestructionSubscribers;

    public ScheduledCreationDestruction(World world) {
        this.world = world;

        bodiesToDestroy = new Array<Body>();
        jointsToDestroy = new Array<Joint>();
        creationDestructionSubscribers = new Array<CreationDestructionSubscriber>();
    }

    @Override
    public void destroyBody(Body body) {
        bodiesToDestroy.add(body);
    }

    @Override
    public void destroyJoint(Joint joint) {
        jointsToDestroy.add(joint);
    }

    @Override
    public void register(CreationDestructionSubscriber creationSubscriber) {
        creationDestructionSubscribers.add(creationSubscriber);
    }

    public void startScheduled(){
        for (Joint joint : jointsToDestroy) world.destroyJoint(joint);
        for (Body body : bodiesToDestroy) world.destroyBody(body);
        for (CreationDestructionSubscriber creationDestructionSubscriber : creationDestructionSubscribers){
            creationDestructionSubscriber.doActions();
        }
        creationDestructionSubscribers.clear();
        jointsToDestroy.clear();
        bodiesToDestroy.clear();
    }
}
