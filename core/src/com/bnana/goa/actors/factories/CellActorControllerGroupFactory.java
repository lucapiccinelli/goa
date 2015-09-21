package com.bnana.goa.actors.factories;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.bnana.goa.actors.CellActorController;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.factories.CellControllerFactory;
import com.bnana.goa.utils.ScaleManager;

/**
 * Created by Luca on 9/18/2015.
 */
public class CellActorControllerGroupFactory implements CellControllerFactory {
    private Group group;
    private ScaleManager sm;

    public CellActorControllerGroupFactory(Group group, ScaleManager sm) {
        this.group = group;
        this.sm = sm;
    }

    public CellController make(SwitchableCell switchableCell) {
        CellActorController controller = new CellActorController(switchableCell, sm);
        group.addActor(controller);
        return controller;
    }
}
