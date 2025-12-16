package org.thunderfighter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/** Main */
public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    Pane pane = new Pane();
    Scene scene = new Scene(pane);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
