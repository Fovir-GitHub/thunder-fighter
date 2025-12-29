// src/main/java/org/thunderfighter/game/trajectory/CurveTrajectory.java

package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * CurveTrajectory
 *
 * <p>Implements a curved (arc-like) bullet movement by applying a perpendicular acceleration and
 * gravity-like downward force.
 *
 * <p>This trajectory is commonly used for arcing bullets, falling projectiles, or stylized enemy
 * patterns.
 */
public class CurveTrajectory implements Trajectory {

  private final double factor;
  private final double gravity;
  private final boolean keepSpeed;
  private final double minDownVy;
  private final double maxSpeed;

  /**
   * Convenience constructor with default parameters.
   *
   * @param factor curvature strength
   */
  public CurveTrajectory(double factor) {
    this(factor, 0.65, true, 4.0, 18.0);
  }

  /**
   * Constructor allowing custom gravity while keeping other defaults.
   *
   * @param factor curvature strength
   * @param gravity downward acceleration
   */
  public CurveTrajectory(double factor, double gravity) {
    this(factor, gravity, true, 4.0, 18.0);
  }

  /**
   * Full constructor for advanced control over the trajectory behavior.
   *
   * @param factor curvature strength
   * @param gravity downward acceleration
   * @param keepSpeed whether to normalize speed each frame
   * @param minDownVy minimum downward velocity
   * @param maxSpeed maximum allowed speed
   */
  public CurveTrajectory(
      double factor, double gravity, boolean keepSpeed, double minDownVy, double maxSpeed) {
    this.factor = factor;
    this.gravity = gravity;
    this.keepSpeed = keepSpeed;
    this.minDownVy = Math.max(0.0, minDownVy);
    this.maxSpeed = maxSpeed;
  }

  /**
   * Updates bullet velocity and position for one frame.
   *
   * @param b the bullet to update
   */
  @Override
  public void update(AbstractBullet b) {
    // Current velocity components
    double vx = b.getDx();
    double vy = b.getDy();

    // Compute perpendicular acceleration to create curved motion
    // Rotates velocity slightly each frame
    double axCurve = -vy * factor;
    double ayCurve = vx * factor;

    // Apply curvature acceleration and gravity
    double newVx = vx + axCurve;
    double newVy = vy + ayCurve + gravity;

    // Optionally normalize velocity magnitude to keep speed stable
    // This prevents exponential acceleration and keeps the curve smooth
    if (keepSpeed) {
      double oldSpeed = Math.hypot(vx, vy);
      double newSpeed = Math.hypot(newVx, newVy);
      if (oldSpeed > 1e-6 && newSpeed > 1e-6) {
        double scale = oldSpeed / newSpeed;
        newVx *= scale;
        newVy *= scale;
      }
    }

    // Enforce a minimum downward velocity to ensure falling behavior
    if (minDownVy > 0.0 && newVy < minDownVy) {
      newVy = minDownVy;
    }

    // Clamp velocity magnitude to avoid unrealistic speeds
    if (maxSpeed > 0.0) {
      double s = Math.hypot(newVx, newVy);
      if (s > maxSpeed && s > 1e-6) {
        double scale = maxSpeed / s;
        newVx *= scale;
        newVy *= scale;
      }
    }

    // Apply updated velocity to bullet
    b.setDx(newVx);
    b.setDy(newVy);

    // Move bullet according to updated velocity
    b.setX(b.getX() + b.getDx());
    b.setY(b.getY() + b.getDy());
  }
}
