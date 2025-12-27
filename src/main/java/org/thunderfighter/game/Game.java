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
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.collision.CollisionDetector;
import org.thunderfighter.core.manager.ScoreManager;
import org.thunderfighter.game.aircraft.player.PlayerAircraft;
import org.thunderfighter.game.item.PlayerItemInventory;
import org.thunderfighter.game.spawn.EnemySpawner;
import org.thunderfighter.ui.KeyboardController;
import org.thunderfighter.ui.ScoreBoard;
import org.thunderfighter.ui.UiMenu;
import org.thunderfighter.ui.UiOverlay;
import org.thunderfighter.utils.Constant;
import org.thunderfighter.utils.Constant.GAME_STATE;

/** Control and manage the game. */
public class Game {

  // GUI related.
  private AnimationTimer animationTimer;
  private Canvas canvas;
  private GraphicsContext graphicsContext;
  private Scene scene;
  private StackPane root;
  private PlayerItemInventory inventory;
  private KeyboardController keyboardController;
  private UiOverlay overlay;
  private UiMenu menu;
  private ScoreBoard scoreBoard;

  // Player.
  private PlayerAircraft playerAircraft;

  // Manage all enetities.
  private List<AbstractEntity> entities = new ArrayList<>();

  // Spawn enemies.
  private EnemySpawner enemySpawner;
  private int numberOfEnemy = 0;

  GAME_STATE gameState;

  public Game(Stage stage) {
    // TODO:
    //  - Enable `canvas` to resize by following the window size change.
    overlay = new UiOverlay();
    menu = new UiMenu(this, overlay);
    canvas = new Canvas(800, 600);
    graphicsContext = canvas.getGraphicsContext2D();
    root = new StackPane(canvas, overlay, menu);
    gameState = GAME_STATE.MENU;

    this.scene = new Scene(root);

    stage.setScene(this.scene);
    stage.show();

    initGame();
  }

  private void initGame() {
    initEntities();
    inventory = new PlayerItemInventory();
    enemySpawner = new EnemySpawner(canvas, entities);
    scoreBoard = new ScoreBoard(root, playerAircraft);

    // TODO: Add `ClearScreenHandler`.
    this.keyboardController = new KeyboardController(playerAircraft, inventory, null);
    keyboardController.operation(this.scene);

    ScoreManager.getInstance().reset();
    initAnimationTimer();
  }

  /** Initialize entities and register them into the {@code entites} list. */
  private void initEntities() {
    playerAircraft =
        new PlayerAircraft(
            canvas.getWidth() / 2,
            canvas.getHeight() - PlayerAircraft.SIZE.getHeight() - 10,
            3,
            10,
            20,
            canvas);
    entities.add(playerAircraft);
  }

  public void setGameState(GAME_STATE gameState) {
    this.gameState = gameState;
  }

  private void initAnimationTimer() {
    // TODO: Implement `OVER` operation.
    animationTimer =
        new AnimationTimer() {
          @Override
          public void handle(long now) {
            switch (gameState) {
              case MENU:
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                menu.setVisible(true);
                overlay.setVisible(false);
                break;
              case RUNNING:
                menu.setVisible(false);
                overlay.setVisible(false);
                update();
                draw();
                break;
              case PAUSE:
                menu.setVisible(false);
                overlay.setVisible(true);
                break;
              case SUCCESS:
                break;
              case FAIL:
                break;
            }
          }
        };
  }

  public void start() {
    if (animationTimer != null) {
      animationTimer.start();
    }
    ;
  }

  public void stop() {
    animationTimer.stop();
  }

  public void update() {
    generateEnemy();

    if (playerAircraft.wantToShoot()) {
      playerAircraft.shoot(entities);
    }

    CollisionDetector.detectCollision(entities);

    Iterator<AbstractEntity> it = entities.iterator();
    while (it.hasNext()) {
      AbstractEntity entity = it.next();
      entity.update(entities);
      if (!entity.isAlive()) {
        it.remove();
        if (entity instanceof AbstractEnemyAircraft) {
          numberOfEnemy--;
        }
      }
    }

    scoreBoard.update();
  }

  public void draw() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    for (AbstractEntity entity : entities) {
      entity.draw(graphicsContext);
    }
  }

  private Constant.PHASE enemyStage = Constant.PHASE.NORMAL;

  private void generateEnemy() {
    if (numberOfEnemy >= Constant.ENEMY_NUMBER_LIMIT) {
      return;
    }

    int currentScore = ScoreManager.getInstance().getScore();

    switch (enemyStage) {
      case NORMAL:
        if (currentScore >= Constant.GENERATE_ELITE_SCORE) {
          enemyStage = Constant.PHASE.ELITE;
          break;
        }
        if (enemySpawner.spawnNormal()) {
          numberOfEnemy++;
        }
        break;

      case ELITE:
        if (currentScore >= Constant.GENERATE_BOSS_SCORE) {
          enemyStage = Constant.PHASE.BOSS;
          break;
        }
        if (enemySpawner.spawnElite()) {
          numberOfEnemy++;
        }
        if (enemySpawner.spawnNormal()) {
          numberOfEnemy++;
        }
        break;

      case BOSS:
        if (enemySpawner.spawnBoss(this)) {
          numberOfEnemy++;
        }
        break;
    }
  }
}
