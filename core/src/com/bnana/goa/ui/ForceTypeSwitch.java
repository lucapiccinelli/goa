package com.bnana.goa.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.bnana.goa.actors.ForceSubject;
import com.bnana.goa.actors.OrganismActor;
import com.bnana.goa.actors.WanderingCellActor;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.stage.OverviewStage;

/**
 * Created by Luca on 10/9/2015.
 */
public class ForceTypeSwitch extends WidgetGroup implements Disposable{

    private final OverviewStage overviewStage;
    private final OrganismActor organismActor;
    private final ForceField outForceField;
    private final ForceField inForceField;
    private final Matrix4 projectionMatrix;
    private WanderingCellActor wanderingCellActor;
    private Skin forcesButtonSkin;
    private int selectedButton;
    private final Button[] buttons;
    private final ForceSubject[] subjects;
    private final Button internalForcesButton;
    private final Button externalForcesButton;

    public ForceTypeSwitch(Vector2 position, Vector2 size, OverviewStage overviewStage, OrganismActor organismActor, final ForceField outForceField, final ForceField inForceField) {
        super();

        projectionMatrix = overviewStage.getCamera().combined.cpy();

        this.overviewStage = overviewStage;
        this.organismActor = organismActor;
        this.outForceField = outForceField;
        this.inForceField = inForceField;

        TextureAtlas forcesButtonAtlas = new TextureAtlas(Gdx.files.internal("forces_buttons\\uiskin.atlas"));
        forcesButtonSkin = new Skin(Gdx.files.internal("forces_buttons\\uiskin.json"), forcesButtonAtlas);

        internalForcesButton = new ImageButton(forcesButtonSkin, "toggle_in");
        internalForcesButton.setPosition(position.x, position.y);

        internalForcesButton.setSize(size.x, size.y);
        addActor(internalForcesButton);

        externalForcesButton = new ImageButton(forcesButtonSkin, "toggle_out");
        externalForcesButton.setPosition(position.x + size.x + size.x * 0.2f, position.y);
        externalForcesButton.setSize(size.x, size.y);
        addActor(externalForcesButton);

        externalForcesButton.setChecked(true);

        subjects = new ForceSubject[]{this.organismActor, this.wanderingCellActor};
        buttons = new Button[]{internalForcesButton, externalForcesButton};
        selectedButton = 1;

        overviewStage.setForceField(outForceField);

        internalForcesButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (internalForcesButton.isChecked()) {
                    event.cancel();
                    return true;
                }
                toggleForce(inForceField);
                return true;
            }
        });

        externalForcesButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (externalForcesButton.isChecked()) {
                    event.cancel();
                    return true;
                }
                toggleForce(outForceField);
                return true;
            }
        });
    }

    private void toggleForce(ForceField forceField) {
        buttons[selectedButton].setChecked(false);
        selectedButton = (selectedButton + 1) % buttons.length;
        subjects[selectedButton].setAsForceSubject(overviewStage);
        subjects[0].setForceField(forceField);
        subjects[1].setForceField(forceField);
        overviewStage.setForceField(forceField);
    }


    public void setWanderingCellActor(WanderingCellActor wanderingCellActor) {
        this.wanderingCellActor = wanderingCellActor;
        subjects[1] = this.wanderingCellActor;
        if(selectedButton == 1) this.wanderingCellActor.setAsForceSubject(overviewStage);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.setProjectionMatrix(projectionMatrix);
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(overviewStage.getCamera().combined);
    }

    @Override
    public void dispose() {
        forcesButtonSkin.dispose();
    }
}
