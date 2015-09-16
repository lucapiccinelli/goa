package com.bnana.goa.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.PositionListener;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellActor extends Actor implements CellConsumer, CellDestroyListener {
    private OffCell offCell;

    public CellActor(final OffCell offCell) {
        this.offCell = offCell;
        this.offCell.addDestroyListener(this);

        this.offCell.use(this);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                offCell.turnOn();
                return true;
            }
        });
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
    }

    @Override
    public void notifyDestroy(CellDestroyEvent cellDestroyEvent) {
        remove();
    }

    @Override
    public void use(Cell cell, Point2D.Float position, float density) {

    }

    @Override
    public void useItOff(OffCell cell, Point2D.Float position, float density) {
        setBounds(0, 0, 1f * Const.RENDERING_SCALE, 1f * Const.RENDERING_SCALE);
        setPosition(position.x * Const.RENDERING_SCALE, position.y * Const.RENDERING_SCALE, Align.center);
    }
}
