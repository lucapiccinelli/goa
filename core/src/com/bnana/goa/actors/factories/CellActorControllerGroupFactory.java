package com.bnana.goa.actors.factories;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.bnana.goa.actors.CellActorController;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.factories.CellControllerFactory;

/**
 * Created by Luca on 9/18/2015.
 */
public class CellActorControllerGroupFactory implements CellControllerFactory {
    private Group group;

    public CellActorControllerGroupFactory(Group group) {
        this.group = group;
    }

    public CellController make(SwitchableCell switchableCell) {
        CellActorController controller = new CellActorController(switchableCell);
        group.addActor(controller);
        return controller;
    }
}
