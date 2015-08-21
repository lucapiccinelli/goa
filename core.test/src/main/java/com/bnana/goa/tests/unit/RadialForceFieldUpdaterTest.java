package com.bnana.goa.tests.unit;

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
    private Point2D.Float[] points;
    private float[] masses;
    private ForceField field;
    private int testDimension;


    @BeforeClass
    public void fixtureSetup(){
        testDimension = 3;

        points = new Point2D.Float[]{
                new Point2D.Float(1, 1),
                new Point2D.Float(2, 2),
                new Point2D.Float(3, 3)
        };
        masses = new float[]{1f, 2f, 3f};


        field = mock(ForceField.class);
    }

    @Test
    public void GivenAGroupOfCellsAForceFieldShoudBeGeneratedAtTheCenterOfMassOfTheCells(){
        CellConsumer forceFieldUpdater = new RadialForceFieldUpdater(field);

        for (int i = 0; i < testDimension; i++) {
            forceFieldUpdater.use(points[i], masses[i]);
        }

        Point2D.Float[] forceCenter = new Point2D.Float[]{new Point2D.Float(2f, 2f)};
        verify(field, atLeastOnce()).update(
                AdditionalMatchers.aryEq(forceCenter),
                AdditionalMatchers.aryEq(new float[]{6}));
    }
}
