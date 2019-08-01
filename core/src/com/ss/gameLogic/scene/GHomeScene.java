package com.ss.gameLogic.scene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GHomeScene extends GScreen {
    TextureAtlas homeAtlas;
    Group uiGroup = new Group();
    @Override
    public void init() {
        homeAtlas = GAssetsManager.getTextureAtlas("home.atlas");
        initUi();
    }

    private void initUi(){
        GStage.addToLayer(GLayer.ui, uiGroup);

        Image bg = GUI.createImage(homeAtlas, "bg");
        uiGroup.addActor(bg);

        Button startBtn = GUI.creatButton(homeAtlas, "btn_newgame");
        uiGroup.addActor(startBtn);
        startBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);

        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new GPlayScene());

            }
        });
    }


    @Override
    public void run() {

    }
    @Override
    public void dispose() {

    }
}
