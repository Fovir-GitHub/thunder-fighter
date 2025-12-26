package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * Lorentz-style curved trajectory. Acceleration is perpendicular to velocity, causing smooth
 * curvature.
 *
 * <p>factor: Controls curvature strength. Recommended range: 0.01 ~ 0.05
 *
 * <p>keepSpeed: Keeps the speed magnitude constant while changing direction.
 */
public class CurveTrajectory implements Trajectory {

  private final double factor;
  private final boolean keepSpeed;

  public CurveTrajectory(double factor) {
    this(factor, true);
  }

  public CurveTrajectory(double factor, boolean keepSpeed) {
    this.factor = factor;
    this.keepSpeed = keepSpeed;
  }

  @Override
  public void update(AbstractBullet b) {
    double vx = b.getDx();
    double vy = b.getDy();

    // Acceleration perpendicular to velocity
    double ax = -vy * factor;
    double ay = vx * factor;

    double newVx = vx + ax;
    double newVy = vy + ay;

    // Maintain speed magnitude if enabled
    if (keepSpeed) {
      double oldSpeed = Math.hypot(vx, vy);
      double newSpeed = Math.hypot(newVx, newVy);
      if (oldSpeed > 1e-6 && newSpeed > 1e-6) {
        double scale = oldSpeed / newSpeed;
        newVx *= scale;
        newVy *= scale;
      }
    }

    b.setDx(newVx);
    b.setDy(newVy);

    b.setX(b.getX() + b.getDx());
    b.setY(b.getY() + b.getDy());
  }
}
