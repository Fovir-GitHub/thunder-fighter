package org.thunderfighter;

import javafx.application.Application;
import javafx.stage.Stage;
import org.thunderfighter.game.Game;

public class Main extends Application {
  public static void main(final String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) {
    final Game game = new Game(stage);
    game.start();
  }
}
