package com.bnana.goa.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 9/15/2015.
 */
public class ActorsFactoryCellGroup implements CellGroup{
    private List<OffCell> offCells;
    private Stage stage;

    public ActorsFactoryCellGroup(Stage stage) {
        this.stage = stage;
        this.offCells = new ArrayList<OffCell>();
    }

    @Override
    public void add(OffCell offCell) {
        offCells.add(offCell);
        CellActorController actor = new CellActorController(offCell);
        stage.addActor(actor);
    }

    @Override
    public void use(CellConsumer consumer) {
        for (OffCell cell :
                offCells) {
            cell.use(consumer);
        }
    }

    public void turnOn(){
        for (OffCell cell :
                offCells) {
            cell.turnOn();
        }
    }

    public void turnOff(){
        for (OffCell cell :
                offCells) {
            cell.turnOn().turnOff();
        }
    }
}
