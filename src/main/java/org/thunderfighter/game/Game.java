package org.thunderfighter.game;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.thunderfighter.core.abstractor.AbstractEntity;

/** Control and manage the game. */
public class Game {

  private AnimationTimer animationTimer;
  private Canvas canvas;
  private GraphicsContext graphicsContext;

  // Manage all enetities.
  private List<AbstractEntity> entities = new ArrayList<>();

  public Game(Stage stage) {
    canvas = new Canvas();
    graphicsContext = canvas.getGraphicsContext2D();

    Scene scene = new Scene(new StackPane(canvas));
    stage.setScene(scene);
    stage.show();

    initGame();
  }

  private void initGame() {
    initEntities();
    initAnimationTimer();
  }

  /** Initialize entities and register them into the {@code entites} list. */
  private void initEntities() {}

  private void initAnimationTimer() {
    animationTimer =
        new AnimationTimer() {
          @Override
          public void handle(long now) {
            update();
            draw();
          }
        };
  }

  public void start() {
    animationTimer.start();
  }

  public void stop() {
    animationTimer.stop();
  }

  public void update() {
    for (AbstractEntity entity : entities) {
      entity.update();
    }
  }

  public void draw() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    for (AbstractEntity entity : entities) {
      entity.draw(graphicsContext);
    }
  }
}
