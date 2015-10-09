package com.bnana.goa.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.bnana.goa.stage.OverviewStage;

/**
 * Created by Luca on 10/9/2015.
 */
public class ForceTypeSwitch extends Group implements Disposable{

    private final OverviewStage overviewStage;
    private Skin forcesButtonSkin;
    private int selectedButton;
    private final Button[] buttons;
    private final Button internalForcesButton;
    private final Button externalForcesButton;

    public ForceTypeSwitch(Vector2 position, Vector2 size, OverviewStage overviewStage) {
        super();
        this.overviewStage = overviewStage;

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

        buttons = new Button[]{internalForcesButton, externalForcesButton};
        selectedButton = 1;

        internalForcesButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (internalForcesButton.isChecked()) {
                    event.cancel();
                    return true;
                }
                toggleForce();
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
                toggleForce();
                return true;
            }
        });
    }

    private void toggleForce() {
        buttons[selectedButton].setChecked(false);
        selectedButton = (selectedButton + 1) % buttons.length;
    }


    @Override
    public void dispose() {
        forcesButtonSkin.dispose();
    }
}
