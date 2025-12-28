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
 * <p>Centralized factory responsible for creating all bullets and item-bullets.
 *
 * <p>Game-layer factory: - Hides construction details - Assigns trajectories - Injects Canvas into
 * bullets (via AbstractEntity.setCanvas)
 */
public final class BulletFactory {

  private BulletFactory() {}

  // -------------------------------------------------
  // Internal helper
  // -------------------------------------------------
  private static <T extends AbstractBullet> T injectCanvas(Canvas canvas, T bullet) {
    if (canvas != null) {
      bullet.setCanvas(canvas);
    }
    return bullet;
  }

  // -------------------------------------------------
  // Player Bullets
  // -------------------------------------------------
  public static PlayerBullet createPlayerBullet(Canvas canvas, double x, double y) {
    PlayerBullet bullet = new PlayerBullet(x, y);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  // -------------------------------------------------
  // Enemy Bullets
  // -------------------------------------------------
  public static NormalEnemyBullet createEnemyBullet(
      Canvas canvas, double x, double y, double dx, double dy, boolean large) {

    NormalEnemyBullet bullet = new NormalEnemyBullet(x, y, dx, dy, large);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  public static CurveEnemyBullet createCurvedEnemyBullet(
      Canvas canvas, double x, double y, double dx, double dy, double curveFactor) {

    CurveEnemyBullet bullet = new CurveEnemyBullet(x, y, dx, dy, curveFactor);
    injectCanvas(canvas, bullet);
    bullet.setTrajectory(new CurveTrajectory(curveFactor));
    return bullet;
  }

  /**
   * Homing enemy bullet.
   *
   * <p>Tracks target for limited ticks, then disappears.
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
