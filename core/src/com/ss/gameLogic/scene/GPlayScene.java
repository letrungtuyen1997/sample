package com.ss.gameLogic.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.view.PlayView;

public class GPlayScene extends GScreen {
    TextureAtlas playAtlas;
    Group pauseGroup;
    Group uiGroup = new Group();
    BitmapFont whiteFont;
    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        playAtlas = GAssetsManager.getTextureAtlas("play.atlas");
        initParticle();
        initFont();
        initUI();
        PlayView.getView().init(this);
    }

    @Override
    public void run() {

    }

    private void initFont(){
        whiteFont = GAssetsManager.getBitmapFont("font_white.fnt");
    }
    private void initParticle(){
        new GParticleSystem("user3boom.p", "particleatlas.atlas", 1, 1);
        new GParticleSystem("baozou1p1.p", "particleatlas.atlas", 1, 1);
        new GParticleSystem("baozou1p2.p", "particleatlas.atlas", 1, 1);
        new GParticleSystem("baozou1p3.p", "particleatlas.atlas", 1, 1);
    }

    private void initUI(){
        GStage.addToLayer(GLayer.ui, uiGroup);
        Image bg = GUI.createImage(playAtlas, "bg");
        uiGroup.addActor(bg);

        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0,GMain.screenWidth, GMain.screenHeight);
        blackOverlay.setColor(0,0,0,0.5f);
        uiGroup.addActor(blackOverlay);


        Button pauseBtn = GUI.creatButton(playAtlas, "btn_pause");
        uiGroup.addActor(pauseBtn);
        pauseBtn.setPosition(GMain.screenWidth - pauseBtn.getWidth(), pauseBtn.getHeight(), Align.topLeft);
        pauseBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPausePanel();
            }
        });
    }

    public void showPausePanel(){
        if(pauseGroup !=null){
            pauseGroup.remove();
        }

        pauseGroup = new Group();
        GStage.addToLayer(GLayer.top, pauseGroup);


        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0,GMain.screenWidth, GMain.screenHeight);
        blackOverlay.setColor(0,0,0,0.5f);
        pauseGroup.addActor(blackOverlay);


        final Group childGroup = new Group();
        pauseGroup.addActor(childGroup);
        childGroup.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);


        Image panel = GUI.createImage(playAtlas, "panel");
        childGroup.addActor(panel);
        panel.setPosition(0, 0, Align.center);

        Image title = GUI.createImage(playAtlas, "tittle_pausing");
        childGroup.addActor(title);
        title.setPosition(0, -150, Align.center);

        childGroup.setScale(0);
        childGroup.addAction(Actions.scaleTo(1, 1, 0.5f, Interpolation.bounceOut));

        Label label = new Label("DANG TAM DUNG", new Label.LabelStyle(whiteFont, Color.WHITE));
        childGroup.addActor(label);
        label.setPosition(0, -100, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(0.5f);

        Button exitBtn = GUI.creatButton(playAtlas, "btn_exit");
        childGroup.addActor(exitBtn);
        exitBtn.setPosition(0, -20, Align.center);

        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new GHomeScene());
            }
        });
        Button resumeBtn = GUI.creatButton(playAtlas, "btn_resume");
        childGroup.addActor(resumeBtn);
        resumeBtn.setPosition(0, 80, Align.center);
        resumeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                childGroup.addAction(Actions.sequence(
                        Actions.scaleTo(0, 0 , 0.5f, Interpolation.bounceIn),
                        GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                            @Override
                            public boolean act(float var1, Actor var2) {
                                blackOverlay.addAction(Actions.sequence(
                                        Actions.fadeOut(0.5f),
                                        Actions.removeActor(pauseGroup)));
                                PlayView.getView().setPause(false);
                                return true;
                            }
                        })
                ));
            }
        });


        PlayView.getView().setPause(true);

    }
}
