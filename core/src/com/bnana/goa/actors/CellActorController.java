package com.bnana.goa.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellActorController extends Actor implements CellController, CellConsumer, CellDestroyListener {
    private SwitchableCell switchableCell;

    public CellActorController(final SwitchableCell offCell) {

        this.switchableCell = offCell;
        this.switchableCell.addDestroyListener(this);

        this.switchableCell.use(this);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CellActorController actor = (CellActorController) event.getTarget();
                actor.sswitch();
                return true;
            }
        });
    }

    @Override
    public void use(Cell cell, Point2D.Float position, float density) {

    }

    @Override
    public void useItOff(OffCell cell, Point2D.Float position, float density) {
        setBounds(0, 0, 1f * Const.RENDERING_SCALE, 1f * Const.RENDERING_SCALE);
        setPosition(position.x * Const.RENDERING_SCALE, position.y * Const.RENDERING_SCALE, Align.center);
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
    public void sswitch() {
        switchableCell = switchableCell.sswitch();
    }

    @Override
    public void useCell(CellConsumer cellConsumer) {
        switchableCell.use(cellConsumer);
    }
}