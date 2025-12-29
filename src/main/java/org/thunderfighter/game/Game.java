// src/main/java/org/thunderfighter/game/Game.java

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
import org.thunderfighter.game.spawn.EnemySpawner;
import org.thunderfighter.ui.KeyboardController;
import org.thunderfighter.ui.ScoreBoard;
import org.thunderfighter.ui.UiMenu;
import org.thunderfighter.ui.UiOverlay;
import org.thunderfighter.ui.UiScoreStorage;
import org.thunderfighter.utils.Constant;
import org.thunderfighter.utils.Constant.GAME_STATE;
import org.thunderfighter.utils.Constant.PHASE;

/** Control and manage the game. */
public class Game {

  // GUI related.
  private AnimationTimer animationTimer;
  private final Canvas canvas;
  private final GraphicsContext graphicsContext;
  private final Scene scene;
  private final StackPane root;
  private KeyboardController keyboardController;
  private final UiOverlay overlay;
  private final UiMenu menu;
  private ScoreBoard scoreBoard;
  private final Image backgroundImage =
      new Image(getClass().getResourceAsStream("/images/Background/bg.png"));

  private long lastTime = 0;

  // Player.
  private PlayerAircraft playerAircraft;

  // Manage all enetities.
  private final List<AbstractEntity> entities = new ArrayList<>();

  // Enemy related.
  private EnemySpawner enemySpawner;
  private int numberOfEnemy = 0;
  private Constant.PHASE enemyStage = Constant.PHASE.NORMAL;

  // Game related.
  GAME_STATE gameState;
  private boolean fromMenuStart = false;
  private boolean scoreStored = false;
  private boolean scoreRead = false;

  public Game(final Stage stage) {
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

  public void setGameState(final GAME_STATE gameState) {
    this.gameState = gameState;
  }

  public GAME_STATE getGameState() {
    return gameState;
  }

  /**
   * Toggle pause state.
   *
   * <p>If the current state is {@code GAME_STATE.RUNNING}, then the game state should be set to
   * {@code GAME_STATE.PAUSE}.
   *
   * <p>Otherwise, the game should be resume, whose game state should be {@code GAME_STATE.RUNNING}.
   */
  public void togglePause() {
    if (gameState == GAME_STATE.RUNNING) {
      gameState = GAME_STATE.PAUSE;
    } else {
      gameState = GAME_STATE.RUNNING;
    }
  }

  public void start() {
    if (animationTimer != null) {
      animationTimer.start();
    }
  }

  public void stop() {
    animationTimer.stop();
  }

  /**
   * Update frames of the game.
   *
   * <p>The order of updating is:
   *
   * <ol>
   *   <li>Generate enemy.
   *   <li>Determine whether the player want to shoot.
   *   <li>Detect collisions.
   *   <li>Update position and state of each {@link Entity}.
   *   <li>Remove dead objects and update {@code numberOfEnemy}.
   *   <li>Update the score board.
   * </ol>
   */
  public void update() {
    generateEnemy();
    if (playerAircraft.wantToShoot()) {
      playerAircraft.shoot(entities);
    }

    CollisionDetector.detectCollision(entities);

    final Iterator<AbstractEntity> it = entities.iterator();
    final List<AbstractEntity> tempList = new ArrayList<>();
    while (it.hasNext()) {
      final AbstractEntity entity = it.next();
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

  /**
   * Draw each frame.
   *
   * <p>This method will draw the background at first, then it will draw each entity stored in
   * {@code entites}.
   */
  public void draw() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    graphicsContext.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
    for (final AbstractEntity entity : entities) {
      entity.draw(graphicsContext);
    }
  }

  public PlayerAircraft getPlayerAircraft() {
    return playerAircraft;
  }

  /**
   * Core logic of running the game.
   *
   * <p>It offers different methods to run under different game state.
   */
  private void initAnimationTimer() {
    animationTimer =
        new AnimationTimer() {
          @Override
          public void handle(final long now) {
            // If this is the first run, initialize the timestamp.
            if (lastTime == 0) {
              lastTime = now;
              return;
            }

            // Calculate the time interval (nanoseconds) required for each frame.
            // 1seconds = 1,000,000,000 ns
            final long interval = 1_000_000_000 / Constant.TPS;

            // The logic is executed only when the difference
            // between the current time and the previous time is greater than or equal to the
            // interval.
            if (now - lastTime >= interval) {
              switch (gameState) {
                case MENU -> handleMenuState();
                case RUNNING -> handleRunningState();
                case PAUSE -> handlePauseState();
                case SUCCESS -> handleSuccessState();
                case FAIL -> handleFailState();
              }
              // Update last execution time
              lastTime = now;
            }
          }
        };
  }

  /** Initialize the game at first launch. */
  private void initGame() {
    initEntities();
    enemySpawner = new EnemySpawner(canvas, entities, this);
    scoreBoard = new ScoreBoard(root, playerAircraft);
    this.keyboardController = new KeyboardController(playerAircraft, this);
    keyboardController.operation(this.scene);
    ScoreManager.getInstance().reset();
    initAnimationTimer();
  }

  /** Initialize entities and register them into the {@code entites} list. */
  private void initEntities() {
    entities.clear();
    numberOfEnemy = 0;
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

  /** Restart the game when clicking the start button in the main menu. */
  private void restartGame() {
    ScoreManager.getInstance().reset();
    enemyStage = PHASE.NORMAL;
    initEntities();
    keyboardController.setPlayer(this.playerAircraft);
    scoreBoard.setPlayerAircraft(this.playerAircraft);
    enemySpawner.reset();
  }

  /** Actions to be taken when the user is in menu. */
  private void handleMenuState() {
    graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    menu.setVisible(true);
    overlay.setVisible(false);
    scoreBoard.setVisible(false);
    fromMenuStart = true;
    scoreStored = false;
    if (!scoreRead) {
      UiScoreStorage.readFromFile();
      scoreRead = true;
    }
  }

  /** Actions to be taken during running the game. */
  private void handleRunningState() {
    // A new round of game.
    if (fromMenuStart) {
      restartGame();
      fromMenuStart = false;
    }
    menu.setVisible(false);
    overlay.setVisible(false);
    scoreBoard.setVisible(true);
    scoreRead = false;
    update();
    draw();
  }

  /** Actions to be done when the game is paused. */
  private void handlePauseState() {
    overlay.showPause();
  }

  /**
   * Utility function used to store scores to the data file.
   *
   * <p>It avoid adding scores repeatly by using a flag {@code scoreStored}.
   *
   * <p>If the flag is {@code true}, it will skip writing into file. Otherwise, it will do so.
   */
  private void storeScore() {
    if (!scoreStored) {
      UiScoreStorage.addScore(ScoreManager.getInstance().getScore());
      scoreStored = true;
      UiScoreStorage.writeToFile();
    }
  }

  /** Actions to be taken when the player is successed. */
  private void handleSuccessState() {
    overlay.showSuccess();
    storeScore();
  }

  /** Actions to be taken if the player failed in the game. */
  private void handleFailState() {
    overlay.showFail();
    storeScore();
  }

  /**
   * Generate enemies according to different phases and scores.
   *
   * <p>If the number of enemy reaches the limit, this method will do nothing.
   */
  private void generateEnemy() {
    if (numberOfEnemy >= Constant.ENEMY_NUMBER_LIMIT) {
      return;
    }

    // Update the phase before entering switch-case statements.
    final int currentScore = ScoreManager.getInstance().getScore();
    if (enemyStage == PHASE.NORMAL && currentScore >= Constant.GENERATE_ELITE_SCORE) {
      enemyStage = PHASE.ELITE;
    } else if (enemyStage == PHASE.ELITE && currentScore >= Constant.GENERATE_BOSS_SCORE) {
      enemyStage = PHASE.BOSS;
    }

    // | Phase  |     Enemy       |
    // | ------ | --------------- |
    // | NORMAL | Normal          |
    // | ELITE  | Elite + Normal  |
    // | BOSS   | Boss            |
    switch (enemyStage) {
      case NORMAL:
        if (enemySpawner.spawnNormal()) {
          numberOfEnemy++;
        }
        break;
      case ELITE:
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
