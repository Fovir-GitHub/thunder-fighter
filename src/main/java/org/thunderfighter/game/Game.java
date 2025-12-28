package org.thunderfighter.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
import org.thunderfighter.utils.Constant.PHASE;

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
  private Image backgroundImage =
      new Image(getClass().getResourceAsStream("/images/Background/bg.png"));

  // Player.
  private PlayerAircraft playerAircraft;

  // Manage all enetities.
  private List<AbstractEntity> entities = new ArrayList<>();

  // Enemy related.
  private EnemySpawner enemySpawner;
  private int numberOfEnemy = 0;
  private PHASE enemyStage = PHASE.NORMAL;

  // Game related.
  GAME_STATE gameState;
  private boolean fromMenuStart = false;

  public Game(Stage stage) {
    // TODO:
    //  - Enable `canvas` to resize by following the window size change.
    overlay = new UiOverlay(this);
    menu = new UiMenu(this, overlay);
    canvas = new Canvas(800, 900);
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
    enemySpawner = new EnemySpawner(canvas, entities, this);
    scoreBoard = new ScoreBoard(root, playerAircraft);

    // TODO: Add `ClearScreenHandler`.
    this.keyboardController = new KeyboardController(playerAircraft, inventory, null, this);
    keyboardController.operation(this.scene);

    ScoreManager.getInstance().reset();
    initAnimationTimer();
  }

  /** Initialize entities and register them into the {@code entites} list. */
  private void initEntities() {
    entities.clear();
    playerAircraft =
        new PlayerAircraft(
            canvas.getWidth() / 2,
            canvas.getHeight() - PlayerAircraft.SIZE.getHeight() - 10,
            3,
            10,
            20,
            canvas,
            this);
    entities.add(playerAircraft);
  }

  public void setGameState(GAME_STATE gameState) {
    this.gameState = gameState;
  }

  public GAME_STATE getGameState() {
    return gameState;
  }

  public void togglePause() {
    if (gameState == GAME_STATE.RUNNING) {
      gameState = GAME_STATE.PAUSE;
    } else {
      gameState = GAME_STATE.RUNNING;
    }
  }

  private void initAnimationTimer() {
    animationTimer =
        new AnimationTimer() {
          @Override
          public void handle(long now) {
            switch (gameState) {
              case MENU -> handleMenuState();
              case RUNNING -> handleRunningState();
              case PAUSE -> handlePauseState();
              case SUCCESS -> handleSuccessState();
              case FAIL -> handleFailState();
            }
          }
        };
  }

  /** Restart the game when clicking the start button in the main menu. */
  private void restartGame() {
    ScoreManager.getInstance().reset();
    initEntities();
    keyboardController.setPlayer(this.playerAircraft);
    scoreBoard.setPlayerAircraft(this.playerAircraft);
  }

  private void handleMenuState() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    menu.setVisible(true);
    overlay.setVisible(false);
    scoreBoard.setVisible(false);
    fromMenuStart = true;
  }

  private void handleRunningState() {
    // A new round of game.
    if (fromMenuStart) {
      restartGame();
      fromMenuStart = false;
    }
    menu.setVisible(false);
    overlay.setVisible(false);
    scoreBoard.setVisible(true);
    update();
    draw();
  }

  private void handlePauseState() {
    overlay.showPause();
  }

  private void handleSuccessState() {
    overlay.showSuccess();
  }

  private void handleFailState() {
    overlay.showFail();
  }

  public void start() {
    if (animationTimer != null) {
      animationTimer.start();
    }
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
    List<AbstractEntity> tempList = new ArrayList<>();
    while (it.hasNext()) {
      AbstractEntity entity = it.next();
      if (entity instanceof AbstractEnemyAircraft) {
        entity.update(tempList);
      } else {
        entity.update(entities);
      }
      if (!entity.isAlive()) {
        it.remove();
        if (entity instanceof AbstractEnemyAircraft) {
          numberOfEnemy--;
        }
      }
    }
    entities.addAll(tempList);
    scoreBoard.update();
  }

  public void draw() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    graphicsContext.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
    for (AbstractEntity entity : entities) {
      entity.draw(graphicsContext);
    }
  }

  public PlayerAircraft getPlayerAircraft() {
    return playerAircraft;
  }

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
