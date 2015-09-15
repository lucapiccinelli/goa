package com.bnana.goa.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.cell.OffCell;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellActor extends Actor{
    private OffCell offCell;

    public CellActor(OffCell offCell) {
        this.offCell = offCell;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
    }

    public void turnOn(){
        offCell.turnOn();
    }
}
