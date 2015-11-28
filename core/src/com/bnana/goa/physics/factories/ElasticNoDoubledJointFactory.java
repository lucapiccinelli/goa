package com.bnana.goa.physics.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Luca on 11/19/2015.
 */
public class ElasticNoDoubledJointFactory implements JointFactory {
    private final RopeJointDef ropeJointDef;
    private final DistanceJointDef distanceJointDef;

    public ElasticNoDoubledJointFactory() {
        distanceJointDef = new DistanceJointDef();
        distanceJointDef.collideConnected = true;
        distanceJointDef.dampingRatio = 1f;
        distanceJointDef.frequencyHz = 1f;

        ropeJointDef = new RopeJointDef();
    }

    @Override
    public void make(Body a, Body b) {
        Array<JointEdge> aJoints = a.getJointList();
        for(JointEdge e : aJoints){
            if(e.other == b) return;
        }

        World world = a.getWorld();

        Vector2 anchorA = a.getWorldCenter();
        Vector2 anchorB = b.getWorldCenter();
        distanceJointDef.initialize(a, b, anchorA, anchorB);
        world.createJoint(distanceJointDef);

        ropeJointDef.maxLength = a.getPosition().dst(b.getPosition()) * 3f;
        ropeJointDef.localAnchorA.set(0, 0);
        ropeJointDef.localAnchorB.set(0, 0);
        ropeJointDef.bodyA = a;
        ropeJointDef.bodyB = b;
        world.createJoint(ropeJointDef);
    }
}
