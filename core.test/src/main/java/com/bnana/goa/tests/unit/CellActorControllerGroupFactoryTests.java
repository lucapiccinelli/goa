package com.bnana.goa.tests.unit;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actors.CellActorController;
import com.bnana.goa.actors.factories.CellActorControllerGroupFactory;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.factories.CellControllerFactory;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 9/18/2015.
 */
public class CellActorControllerGroupFactoryTests {
    @Test
    public void ItCreatesANewCellActorControllerAndAddItToTheParentGroup(){
        Group group = mock(Group.class);
        CellControllerFactory factory = new CellActorControllerGroupFactory(group);
        CellActorController controller = (CellActorController)factory.make(mock(SwitchableCell.class));

        verify(group).addActor(same(controller));
    }

    @Test
    public void TheNewCellControllerIsThenUsedOnTheGivenCell(){
        SwitchableCell switchableCell = new SwitchableCell() {
            @Override
            public SwitchableCell sswitch() {
                return null;
            }

            @Override
            public void integrate(OffCell evolvedCell) {

            }

            @Override
            public void use(CellConsumer consumer) {
                consumer.use(this, mock(Point2D.Float.class), 1);
            }

            @Override
            public void usePosition(PositionConsumer positionConsumer) {

            }

            @Override
            public float distance(Cell cell) {
                return 0;
            }

            @Override
            public Cell prototype(Organism belongingOrganism, Point2D.Float position, float density) {
                return null;
            }

            @Override
            public Cell opposite(Point2D.Float position, float density) {
                return null;
            }

            @Override
            public OffCell getAnOffCell() {
                return null;
            }

            @Override
            public OnTouchAction createOnTouchAction(PhysicElement element) {
                return null;
            }

            @Override
            public void addDestroyListener(CellDestroyListener destroyListener) {

            }

            @Override
            public void destroy() {

            }

            @Override
            public void setPosition(Point2D.Float position) {

            }

            @Override
            public void updatePosition(PositionChangedEvent position) {

            }
        };
        Group group = mock(Group.class);

        CellControllerFactory factory = new CellActorControllerGroupFactory(group);
        CellController controller = factory.make(switchableCell);

        CellConsumer consumer = mock(CellConsumer.class);

        controller.useCell(consumer);

        verify(consumer).use(same(switchableCell), any(Point2D.Float.class), anyFloat());
    }
}
