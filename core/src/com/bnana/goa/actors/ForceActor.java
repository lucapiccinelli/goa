package com.bnana.goa.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by Luca on 9/21/2015.
 */
public class ForceActor extends Actor {
    private ForceField forceField;
    private ForceRenderer forceRenderer;

    public ForceActor(ForceField forceField, ForceRenderer forceRenderer) {
        this.forceField = forceField;
        this.forceRenderer = forceRenderer;
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        forceField.render(forceRenderer);
        super.draw(batch, parentAlpha);
    }
}
