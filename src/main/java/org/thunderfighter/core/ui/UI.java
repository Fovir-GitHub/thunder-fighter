package org.thunderfighter.core.abstractor;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.List;

public final class UI {
  private UI() {}
  // UI: Scene (Start/Game)
  public static abstract class AbstractScene {
    protected double width;
    protected double height;
    public final Scene build(Stage stage, double width, double height) {
      this.width = width;
      this.height = height; //size
      Scene scene = createScene(stage);
      onShow(stage, scene);
      return scene;
    }
    protected abstract Scene createScene(Stage stage);
    protected void onShow(Stage stage, Scene scene){};
  }

  // Pause Overlay
  public static abstract class AbstractOverlay {
    protected boolean visible_flag = false;
    protected Pane root;
    public final Pane build(){
      root = createOverlay();
      root.setVisible(visible_flag);
      onBuild(root);
      return root;
    }
    public final void show(){
      visible_flag = true;
      if (root != null) root.setVisible(true);
      onVisibleChanged(true);
    }
    public final void hide(){
      visible_flag = false;
      if (root != null) root.setVisible(false);
      onVisibleChanged(false);
    }
    public final void toggle(){
      if (visible_flag) hide();
      else show();
    }
    public final boolean isVisible(){
      return visible_flag;
    }
    protected abstract Pane createOverlay();
    protected void onBuild(Pane root){};
    protected void onVisibleChanged(boolean visible){};
  }

  // UI: Dialog (Setting)
  public static abstract class AbstractDialog {
    public final void show() {
      onBeforeShow();
      onShow();
      onAfterShow();
    }
    protected abstract void onShow();
    protected void onBeforeShow(){};
    protected void onAfterShow(){};
  }

  // UI: Menu (Start/Pause)
  public static abstract class AbstractMenu {
    public final void init() {
      createLayout();
      bindActions();
      onReady();
    }
    protected abstract void createLayout();
    protected void bindActions(){};
    protected void onReady(){};
  }

  // Core: Sound (volume + switch)
  public static abstract class AbstractSoundController {
    protected boolean soundOn = true;
    protected double volume = 1.0;
    public final double getVolume() {
      return soundOn ? volume : 0.0;
    }
    public final boolean isSoundOn() {
      return soundOn;
    }
    public final void setSoundOn(boolean on) {
      soundOn = on;
      onSoundSwitch(on);
    }
    public final void setVolume(double v) {
      volume = clamp(v);
      onVolumeChanged(volume);
    }//set volume
    protected double clamp(double v) {
      if (v < 0.0) return 0.0;
      if (v > 1.0) return 1.0;
      return v;
    }//limitation between 0.0 and 1.0
    protected void onSoundSwitch(boolean on){};
    protected void onVolumeChanged(double volume){};
  }

  // Core: Score Storage (file/db/memory)
  public static abstract class AbstractScoreStorage {
    public final List<Integer> readScores(){
      return onReadScores();
    }
    public final void addScore(int score){
      onAddScore(score);
    }
    protected abstract List<Integer> onReadScores();
    protected abstract void onAddScore(int score);
  }
}