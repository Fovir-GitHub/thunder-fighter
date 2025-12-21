package org.thunderfighter.game.factory;

import org.thunderfighter.game.bullet.*;
import org.thunderfighter.game.item.*;
import org.thunderfighter.game.trajectory.*;
import org.thunderfighter.core.entity.Aircraft;

/**
 * BulletFactory
 *
 * Centralized factory responsible for creating all bullets and item-bullets.
 *
 * Responsibilities:
 * - Hide construction details of bullets
 * - Assign trajectories
 * - Set canvas size, speed, lifetime, and flags
 *
 * This class belongs to the game layer (NOT core),
 * because it contains gameplay-specific creation logic.
 */
public final class BulletFactory {

  // Prevent instantiation
  private BulletFactory() {}

  // -------------------------------------------------
  // Player Bullets
  // -------------------------------------------------

  /**
   * Creates a standard player bullet (straight line, damage = 1).
   */
  public static PlayerBullet createPlayerBullet(
      double x, double y,
      double canvasW, double canvasH
  ) {
    PlayerBullet bullet = new PlayerBullet(x, y, canvasW, canvasH);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  // -------------------------------------------------
  // Enemy Bullets
  // -------------------------------------------------

  /**
   * Creates a normal enemy bullet with straight trajectory.
   */
  public static NormalEnemyBullet createEnemyBullet(
      double x, double y,
      double dx, double dy,
      boolean large,
      double canvasW, double canvasH
  ) {
    NormalEnemyBullet bullet =
        new NormalEnemyBullet(x, y, dx, dy, large, canvasW, canvasH);
    bullet.setTrajectory(new StraightTrajectory());
    return bullet;
  }

  /**
   * Creates a curved enemy bullet using Lorentz-style trajectory.
   * Used for area control and movement restriction.
   */
  public static CurveEnemyBullet createCurvedEnemyBullet(
      double x, double y,
      double dx, double dy,
      double curveFactor,
      double canvasW, double canvasH
  ) {
    CurveEnemyBullet bullet =
        new CurveEnemyBullet(x, y, dx, dy, curveFactor, canvasW, canvasH);
    bullet.setTrajectory(new LorentzCurveTrajectory(curveFactor));
    return bullet;
  }

  /**
   * Creates a homing enemy bullet that tracks the player for a limited time.
   */
  public static HomingEnemyBullet createHomingBullet(
      double x, double y,
      double dx, double dy,
      int trackingTicks,
      HomingTrajectory.TargetProvider provider,
      double canvasW, double canvasH
  ) {
    HomingEnemyBullet bullet =
        new HomingEnemyBullet(x, y, dx, dy, trackingTicks, provider, canvasW, canvasH);
    bullet.setTrajectory(new HomingTrajectory(provider, 0.12));
    return bullet;
  }

  // -------------------------------------------------
  // Laser Bullets (Boss)
  // -------------------------------------------------

  /**
   * Creates a laser bullet.
   *
   * Laser bullets:
   * - Move extremely fast
   * - Have a limited duration
   * - Can be cleared immediately by clear-screen items
   */
  public static LaserBullet createLaserBullet(
      double x, double y,
      double dx, double dy,
      int durationTicks,
      double thickness,
      double canvasW, double canvasH
  ) {
    return new LaserBullet(
        x, y, dx, dy,
        durationTicks,
        thickness,
        canvasW, canvasH
    );
  }

  // -------------------------------------------------
  // Item Bullets
  // -------------------------------------------------

  /**
   * Creates a heal item bullet (+1 HP).
   */
  public static HealItemBullet createHealItem(
      double x, double y,
      double canvasW, double canvasH
  ) {
    return new HealItemBullet(x, y, canvasW, canvasH);
  }

  /**
   * Creates a shield item bullet (invincibility).
   */
  public static ShieldItemBullet createShieldItem(
      double x, double y,
      double canvasW, double canvasH,
      int invincibleTicks
  ) {
    return new ShieldItemBullet(x, y, canvasW, canvasH, invincibleTicks);
  }

  /**
   * Creates a power item bullet (damage boost).
   */
  public static PowerItemBullet createPowerItem(
      double x, double y,
      double canvasW, double canvasH,
      int buffTicks,
      int bonusDamage
  ) {
    return new PowerItemBullet(x, y, canvasW, canvasH, buffTicks, bonusDamage);
  }

  /**
   * Creates a clear-screen item bullet.
   *
   * This item requires cooperation with the game world to clear bullets/enemies.
   */
  public static ClearItemBullet createClearItem(
      double x, double y,
      double canvasW, double canvasH,
      ClearScreenHandler handler,
      int clearWindowTicks
  ) {
    return new ClearItemBullet(
        x, y, canvasW, canvasH,
        handler, clearWindowTicks
    );
  }
}
