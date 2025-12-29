// src/main/java/org/thunderfighter/game/aircraft/enemy/BossEnemy.java

package org.thunderfighter.game.aircraft.enemy;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.Game;
import org.thunderfighter.game.bulletfactory.BulletFactory;
import org.thunderfighter.game.trajectory.HomingTrajectory;
import org.thunderfighter.utils.Constant;

/**
 * Boss Enemy
 * Boss-level enemy
 * Has multi-stage behavior; its attack patterns and movement speed change based on its health.
 */
public class BossEnemy extends AbstractEnemyAircraft {

  /** Boss dimensions (width 200, height 150) */
  public static final Dimension2D SIZE = new Dimension2D(200, 150);

  /** Move to the right now or not */
  private boolean movingRight = true;

  /** Screen width, used to limit the Boss's horizontal movement range */
  private static final double SCREEN_WIDTH = 800;

  /** The previous stage, used to detect if the stage has changed */
  private Stage lastStage = null;

  /** The main game object, used to obtain the player's plane and modify the game state */
  private Game game;

  /**
   * Boss's three phases
   * Stage 1: High health
   * Stage 2: Medium health
   * Stage 3: Low health (Enrage)
   */
  private enum Stage {
    stage1,
    stage2,
    stage3
  }

  /** Current stage of the Boss */
  private Stage stage = Stage.stage1;

  /**
   * Constructor
   *
   * @param x Initial X coordinate
   * @param y Initial Y coordinate
   * @param game Game object
   */
  public BossEnemy(double x, double y, Game game) {
    this.x = x;
    this.y = y; // birth coordinates

    this.hp = 30;
    this.speed = 1;
    this.score = 3000; // kill reward

    this.canShoot = true;
    this.shootInterval = 180; // the number of frames are needed between two shots
    this.shootCooldown =
        shootInterval; // the number of frames are left before the next shot can be fired

    this.size = SIZE;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/BossEnemy.png"));
    this.game = game;
  }

  /**
   * Boss's movement logic
   * Moves back and forth between the center of the screen
   */
  @Override
  protected void move() {
    double LEFT_BOUND = SCREEN_WIDTH * 0.25;
    double RIGHT_BOUND = SCREEN_WIDTH * 0.75 - size.getWidth();

    if (movingRight) {
      x += speed;
      if (x >= RIGHT_BOUND) movingRight = false;
    } else {
      x -= speed;
      if (x <= LEFT_BOUND) movingRight = true;
    }
  }

  /**
   * Triggered when the Boss dies
   * Game state switches to victory
   */
  @Override
  protected void onDie() {
    super.onDie();
    game.setGameState(Constant.GAME_STATE.SUCCESS);
  }

  /**
   * Update Boss status every frame
   * Switch phases based on health, and adjust speed and firing rate accordingly
   */
  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    super.onUpdate(worldEntities);

    if (hp >= 20) {
      stage = Stage.stage1;
    } else if (hp >= 10) {
      stage = Stage.stage2;
      speed = 1.2;
    } else if (hp > 0) {
      stage = Stage.stage3;
      speed = 1.8;
    }

    if (stage != lastStage) {
      switch (stage) {
        case stage1 -> shootInterval = 90;
        case stage2 -> shootInterval = 150;
        case stage3 -> shootInterval = 120;
      }

      shootCooldown = shootInterval;
      lastStage = stage;
    }
  }

  /**
   * Boss Shooting Entry
   * Activate different shooting modes based on the current stage
   */
  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    switch (stage) {
      case stage1:
        shootStage1(worldEntities);
        break;
      case stage2:
        shootStage2(worldEntities);
        break;
      case stage3:
        shootStage3(worldEntities);
        break;
    }
  }

  /**
   * First stage firing method
   * - Three straight-line bullets in a scattering pattern
   * - Two curved bullets
   */
  private void shootStage1(List<AbstractEntity> worldEntities) {
    Canvas c = getCanvas();
    if (c == null) return;

    double cx = x + size.getWidth() / 2; // @params
    double by = y + size.getHeight();

    for (int i = -1; i <= 1; i++) {
      worldEntities.add(
          BulletFactory.createEnemyBullet(c, cx + i * 20, by + 4, i * 0.6, 3.5, false));
    }

    worldEntities.add(BulletFactory.createCurvedEnemyBullet(c, cx - 40, by, -1.2, 3.0, 0.05));
    worldEntities.add(BulletFactory.createCurvedEnemyBullet(c, cx - 40, by, -1.2, 3.0, -0.05));
  }

  /**
   * Second stage firing method
   * - 1 homing bullet
   * - 7 fan-shaped straight bullets
   */
  private void shootStage2(List<AbstractEntity> worldEntities) {
    Canvas c = getCanvas();
    if (c == null) return;

    double cx = x + size.getWidth() / 2;
    double by = y + size.getHeight();

    worldEntities.add(
        BulletFactory.createHomingBullet(
            c,
            cx,
            by,
            0,
            2.5,
            150, // Tracking bullet (locks onto player for 2.5 seconds)
            new HomingTrajectory.TargetProvider() {
              @Override
              public double getTargetX() {
                return game.getPlayerAircraft() != null ? game.getPlayerAircraft().getX() : cx;
              }

              @Override
              public double getTargetY() {
                return game.getPlayerAircraft() != null
                    ? game.getPlayerAircraft().getY()
                    : by + 200;
              }
            }));

    for (int i = -3; i <= 3; i++) {
      worldEntities.add(BulletFactory.createEnemyBullet(c, cx, by + 4, i * 0.8, 3.8, i == 0));
    }
  }

  /**
   * Third stage firing mode (Berserk)
   * - Laser
   * - Homing projectile
   * - High-speed linear projectile
   */
  private void shootStage3(List<AbstractEntity> worldEntities) {
    Canvas c = getCanvas();
    if (c == null) return;

    double cx = x + size.getWidth() / 2;
    double by = y + size.getHeight();

    worldEntities.add(
        BulletFactory.createLaserBullet(c, cx - 30, by + 4, 0, 0, 60, 20)); // create Laser bullet

    worldEntities.add(
        BulletFactory.createHomingBullet(
            c,
            cx + 40,
            by + 4,
            0,
            2.5,
            120, // tracking 2 seconds
            new HomingTrajectory.TargetProvider() {
              @Override
              public double getTargetX() {
                return game.getPlayerAircraft() != null ? game.getPlayerAircraft().getX() : cx;
              }

              @Override
              public double getTargetY() {
                return game.getPlayerAircraft() != null
                    ? game.getPlayerAircraft().getY()
                    : by + 200;
              }
            }));

    worldEntities.add(
        BulletFactory.createEnemyBullet(c, cx, by + 4, 0, 4.5, true)); // create straight bullet
  }
}
