// src/main/java/org/thunderfighter/game/bulletfactory/BulletFactory.java

package org.thunderfighter.game.bulletfactory;

import javafx.scene.canvas.Canvas;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.game.bullet.CurveEnemyBullet;
import org.thunderfighter.game.bullet.HomingEnemyBullet;
import org.thunderfighter.game.bullet.LaserBullet;
import org.thunderfighter.game.bullet.NormalEnemyBullet;
import org.thunderfighter.game.bullet.PlayerBullet;
import org.thunderfighter.game.trajectory.CurveTrajectory;
import org.thunderfighter.game.trajectory.HomingTrajectory;
import org.thunderfighter.game.trajectory.StraightTrajectory;

/**
 * BulletFactory
 *
 * <p>Centralized factory responsible for creating all bullet instances.
 *
 * <p>Responsibilities: - Encapsulates bullet construction logic - Assigns appropriate trajectories
 * - Injects Canvas dependency into bullets
 *
 * <p>This class belongs to the game layer and prevents bullet creation logic from being scattered
 * across entities.
 */
public final class BulletFactory {

  /** Utility class: prevent instantiation */
  private BulletFactory() {}

  // -------------------------------------------------
  // Internal helper
  // -------------------------------------------------

  /**
   * Injects Canvas into a bullet instance if available.
   *
   * <p>This avoids forcing every bullet constructor to depend on Canvas and keeps rendering
   * concerns centralized.
   */
  private static <T extends AbstractBullet> T injectCanvas(Canvas canvas, T bullet) {
    if (canvas != null) {
      bullet.setCanvas(canvas);
    }
    return bullet;
  }

  // -------------------------------------------------
  // Player Bullets
  // -------------------------------------------------

  /** Creates a standard straight-moving player bullet. */
  public static PlayerBullet createPlayerBullet(Canvas canvas, double x, double y) {
    PlayerBullet bullet = new PlayerBullet(x, y);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  // -------------------------------------------------
  // Enemy Bullets
  // -------------------------------------------------

  /**
   * Creates a normal enemy bullet with fixed initial velocity.
   *
   * @param large whether to use large bullet visuals
   */
  public static NormalEnemyBullet createEnemyBullet(
      Canvas canvas, double x, double y, double dx, double dy, boolean large) {

    NormalEnemyBullet bullet = new NormalEnemyBullet(x, y, dx, dy, large);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  /** Creates an enemy bullet that follows a curved trajectory. */
  public static CurveEnemyBullet createCurvedEnemyBullet(
      Canvas canvas, double x, double y, double dx, double dy, double curveFactor) {

    CurveEnemyBullet bullet = new CurveEnemyBullet(x, y, dx, dy, curveFactor);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new CurveTrajectory(curveFactor));
    return bullet;
  }

  /**
   * Creates a homing enemy bullet.
   *
   * <p>The bullet gradually turns toward the target for a limited number of ticks before expiring.
   */
  public static HomingEnemyBullet createHomingBullet(
      Canvas canvas,
      double x,
      double y,
      double dx,
      double dy,
      int trackingTicks,
      HomingTrajectory.TargetProvider provider) {

    HomingEnemyBullet bullet = new HomingEnemyBullet(x, y, dx, dy, trackingTicks, provider);

    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new HomingTrajectory(provider, 0.12));
    return bullet;
  }

  // -------------------------------------------------
  // Laser Bullets (Boss)
  // -------------------------------------------------

  /**
   * Creates a laser-type bullet, typically used by bosses.
   *
   * <p>Laser bullets usually manage their own lifetime and rendering and therefore may not require
   * a trajectory.
   */
  public static LaserBullet createLaserBullet(
      Canvas canvas,
      double x,
      double y,
      double dx,
      double dy,
      int durationTicks,
      double thickness) {

    LaserBullet bullet = new LaserBullet(x, y, dx, dy, durationTicks, thickness);

    injectCanvas(canvas, bullet);
    // bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }
}
