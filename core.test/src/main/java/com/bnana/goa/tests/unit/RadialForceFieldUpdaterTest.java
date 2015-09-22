package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceFieldUpdater;

import org.mockito.AdditionalMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/21/2015.
 */
public class RadialForceFieldUpdaterTest {
    private Vector2[] points;
    private float[] masses;
    private ForceField field;
    private int testDimension;


    @BeforeClass
    public void fixtureSetup(){
        testDimension = 3;

        points = new Vector2[]{
                new Vector2(1, 1),
                new Vector2(2, 2),
                new Vector2(3, 3)
        };
        masses = new float[]{1f, 2f, 3f};


        field = mock(ForceField.class);
    }

    @Test
    public void GivenAGroupOfCellsAForceFieldShoudBeGeneratedAtTheCenterOfMassOfTheCells(){
        CellConsumer forceFieldUpdater = new RadialForceFieldUpdater(field);

        Cell cell = mock(Cell.class);
        for (int i = 0; i < testDimension; i++) {
            forceFieldUpdater.use(cell, points[i], masses[i]);
        }

        Vector2[] forceCenter = new Vector2[]{new Vector2(2f, 2f)};
        verify(field, atLeastOnce()).update(
                AdditionalMatchers.aryEq(forceCenter),
                AdditionalMatchers.aryEq(new float[]{6}));
    }
}
