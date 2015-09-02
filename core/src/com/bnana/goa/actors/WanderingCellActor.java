package com.bnana.goa.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;

/**
 * Created by Luca on 9/2/2015.
 */
public class WanderingCellActor extends Actor{

    public WanderingCellActor(RandomCellGenerator cellGenerator){

    }

    @Override
    public void act(float delta){
        super.act(delta);
//        physicOrganism.apply(force);
    }
}
