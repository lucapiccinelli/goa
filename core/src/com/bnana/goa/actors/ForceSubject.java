package com.bnana.goa.actors;

import com.bnana.goa.force.ForceField;
import com.bnana.goa.stage.OverviewStage;

/**
 * Created by luca.piccinelli on 13/10/2015.
 */
public interface ForceSubject {
    void setAsForceSubject(OverviewStage stage);
    void setForceField(ForceField forceField);
}
