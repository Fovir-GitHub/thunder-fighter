package org.thunderfighter.core.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/*
  Used to create StartScene / GameScene User Interface.
  Design notes:
  1.width/height is valued by build() , so subclasses can use them in createScene().
  2.createScene() is abstract and must be implemented by subclasses.
  3.onShow() is an optional hook after Scene is created and before shown.
 */
public abstract class UiScene {

  protected double width;
  protected double height;// Define the size of the scene

  public final Scene build(Stage stage, double width, double height) {
    this.width = width;
    this.height = height;
    Scene scene = createScene(stage);
    onShow(stage, scene);
    return scene;
  }// the subclass should create and return a Scene.

  protected abstract Scene createScene(Stage stage);

  //Scene is created and before shown.
  protected void onShow(Stage stage, Scene scene) {}
}
