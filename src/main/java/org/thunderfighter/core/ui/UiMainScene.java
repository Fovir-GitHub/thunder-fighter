package org.thunderfighter.core.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
// UI: Scene (Start/Game)
public abstract class UiMainScene {

  protected double width;
  protected double height;

  public final Scene build(Stage stage, double width, double height) {
    this.width = width;
    this.height = height;//size
    Scene scene = createScene(stage);
    onShow(stage, scene);
    return scene;
  }

  protected abstract Scene createScene(Stage stage);
  protected void onShow(Stage stage, Scene scene) {};
}