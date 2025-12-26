package org.thunderfighter.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.manager.ScoreManager;
import org.thunderfighter.game.aircraft.player.PlayerAircraft;
import org.thunderfighter.game.spawn.EnemySpawner;
import org.thunderfighter.ui.KeyboardController;
import org.thunderfighter.utils.Constant;

/** Control and manage the game. */
public class Game {

  private AnimationTimer animationTimer;
  private Canvas canvas;
  private GraphicsContext graphicsContext;
  private PlayerAircraft playerAircraft;
  private KeyboardController keyboardController;
  private Scene scene;

  // Manage all enetities.
  private List<AbstractEntity> entities = new ArrayList<>();

  private EnemySpawner enemySpawner;
  private int numberOfEnemy = 0;

  public Game(Stage stage) {
    // TODO:
    //  - Enable `canvas` to resize by following the window size change.
    canvas = new Canvas(800, 600);
    graphicsContext = canvas.getGraphicsContext2D();
    this.scene = new Scene(new StackPane(canvas));

    stage.setScene(this.scene);
    stage.show();

    initGame();
  }

  private void initGame() {
    enemySpawner = new EnemySpawner(canvas.getWidth(), entities);
    initEntities();
    this.keyboardController = new KeyboardController(playerAircraft);
    keyboardController.operation(this.scene, this.canvas);

    initAnimationTimer();
    ScoreManager.getInstance().reset();
  }

  /** Initialize entities and register them into the {@code entites} list. */
  private void initEntities() {
    playerAircraft =
        new PlayerAircraft(
            canvas.getWidth() / 2, canvas.getHeight() - PlayerAircraft.SIZE.getHeight(), 3, 10, 20);
    entities.add(playerAircraft);
  }

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
    generateEnemy();

    if (playerAircraft.wantToShoot()) {
      playerAircraft.shoot(entities);
    }

    Iterator<AbstractEntity> it = entities.iterator();
    while (it.hasNext()) {
      AbstractEntity entity = it.next();
      entity.update();
      if (!entity.isAlive()) {
        it.remove();
      }
    }
  }

  public void draw() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    for (AbstractEntity entity : entities) {
      entity.draw(graphicsContext);
    }
  }

  private void generateEnemy() {
    if (numberOfEnemy >= Constant.ENEMY_NUMBER_LIMIT) {
      return;
    }

    int currentScore = ScoreManager.getInstance().getScore() % Constant.SCORE_PER_ROUND;
    if (currentScore >= Constant.GENERATE_BOSS_SCORE) {
      enemySpawner.spawnBoss();
    } else if (currentScore >= Constant.GENERATE_ELITE_SCORE) {
      enemySpawner.spawnElite();
      enemySpawner.spawnNormal();
    } else {
      enemySpawner.spawnNormal();
    }
  }
}
