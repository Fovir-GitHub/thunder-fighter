package org.thunderfighter;

import javafx.application.Application;
import javafx.stage.Stage;
import org.thunderfighter.game.Game;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    Game game = new Game(stage);
    game.start();
  }

  public static void main(String[] args) {
    launch();
  }
}
