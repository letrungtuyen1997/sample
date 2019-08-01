package com.ss.view;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ss.core.action.exAction.GArcMoveToAction;
import com.ss.core.action.exAction.GScreenShakeAction;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.scene.GPlayScene;

public class PlayView {
    public static PlayView instance;
    public GPlayScene playScene;
    private GLayerGroup playGroup;
    private TextureAtlas playAtlas;

    public static PlayView getView(){
        if(instance == null)
            instance = new PlayView();
        return instance;
    }


    public void init(GPlayScene screen){
        playAtlas = GAssetsManager.getTextureAtlas("play.atlas");
        this.playScene = screen;
        instance = this;
        initGame();
    }


    public void stopParticle(){
        if(eff2!=null)
            eff2.remove();

        GParticleSystem.getGParticleSystem("baozou1p3.p").create(playGroup, 500, 400).setAdditive(true);
    }

    GParticleSprite eff2;
    public void initGame(){
        playGroup = new GLayerGroup();
        GStage.addToLayer(GLayer.ui, playGroup);

        Image obj = GUI.createImage(playAtlas, "2");
        playGroup.addActor(obj);
        obj.setPosition(400,100);

        obj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stopParticle();
            }
        });

        GParticleSprite eff1= GParticleSystem.getGParticleSystem("baozou1p1.p").create(playGroup, 500, 400);
        //eff1.setLoop(true);

        eff1.addAction(delay(0.5f, GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
            @Override
            public boolean act(float var1, Actor var2) {
                eff2=  GParticleSystem.getGParticleSystem("baozou1p2.p").create(playGroup, 500, 400);
                eff2.setAdditive(true);
                return true;
            }
        })));

        obj.addAction(forever( sequence(
                GArcMoveToAction.arcMoveTo(800, 100, 600,0, 1, linear),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        playGroup.addAction(GScreenShakeAction.screenShake(0.5f, GLayer.ui));
                        GParticleSystem.getGParticleSystem("user3boom.p").create(playGroup, 800, 100);
                        return true;
                    }
                }),
                GArcMoveToAction.arcMoveTo(400, 100, 600,200, 1, linear),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        playGroup.addAction(GScreenShakeAction.screenShake(0.5f, GLayer.ui));
                        GParticleSystem.getGParticleSystem("user3boom.p").create(playGroup, 400, 100);
                        return true;
                    }
                }))));
    }

    public void setPause(boolean isPause){
        playGroup.setPause(isPause);
    }
}
