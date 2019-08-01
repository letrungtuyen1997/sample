package com.ss;

import com.platform.IPlat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GDirectedGame;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GStage.StageBorder;
import com.ss.gameLogic.scene.GHomeScene;
//import com.ss.gameLogic.model.GUITools;
//import com.ss.gameLogic.scene.GMenu;
//import com.ss.gameLogic.scene.GPlay;

public class GMain
  extends GDirectedGame
{

  public static boolean isDebug = false;
  public static final boolean isTest = false;
  public static GScreen mapEditorScreen;
  public static GMain me;
  public static int screenHeight = 0;
  public static int screenWidth = 0;
  public static int screenId = -1;
  public static GScreen shooterTestScreen;
  public static final int testType = 2;


  public static IPlat platform;
  public GMain(IPlat plat){
    platform = plat;
  }


  private void init()
  {

    screenWidth = 1280;
    screenHeight = 720;

    GStage.init(screenWidth, screenHeight, 0, 0, new StageBorder() {
      @Override
      public void drawHorizontalBorder(Batch spriteBatch, float paramFloat1, float paramFloat2) {

      }

      @Override
      public void drawVerticalBorder(Batch spriteBatch, float paramFloat1, float paramFloat2) {

      }
    });
  }
  
//  public static GScreen menuScreen()
//  {
//    return new GMenu();
//  }
//
//  public static GScreen playScreen()
//  {
//    return new GPlay();
//  }

  public void create()
  {
    this.init();
    //GUITools.initFont();
    //this.setScreen(menuScreen());
    this.setScreen(new GHomeScene());
  }
  
  public void dispose()
  {
    GMain.platform.log("############## gmain dispose");
    GParticleSystem.saveAllFreeMin();
    super.dispose();
  }
}
