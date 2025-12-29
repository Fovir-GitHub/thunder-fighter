// src/main/java/org/thunderfighter/game/trajectory/CurveTrajectory.java

package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

public class CurveTrajectory implements Trajectory {

  private final double factor;
  private final double gravity;
  private final boolean keepSpeed;
  private final double minDownVy;
  private final double maxSpeed;

  public CurveTrajectory(double factor) {

    this(factor, 0.65, true, 4.0, 18.0);
  }

  public CurveTrajectory(double factor, double gravity) {
    this(factor, gravity, true, 4.0, 18.0);
  }

  public CurveTrajectory(
      double factor, double gravity, boolean keepSpeed, double minDownVy, double maxSpeed) {
    this.factor = factor;
    this.gravity = gravity;
    this.keepSpeed = keepSpeed;
    this.minDownVy = Math.max(0.0, minDownVy);
    this.maxSpeed = maxSpeed;
  }

  @Override
  public void update(AbstractBullet b) {
    double vx = b.getDx();
    double vy = b.getDy();

    // Perpendicular accel -> curve
    double axCurve = -vy * factor;
    double ayCurve = vx * factor;

    // Add strong downward pull (JavaFX: +y is down)
    double newVx = vx + axCurve;
    double newVy = vy + ayCurve + gravity;

    // Optionally keep magnitude stable (prevents runaway, keeps arc smooth)
    if (keepSpeed) {
      double oldSpeed = Math.hypot(vx, vy);
      double newSpeed = Math.hypot(newVx, newVy);
      if (oldSpeed > 1e-6 && newSpeed > 1e-6) {
        double scale = oldSpeed / newSpeed;
        newVx *= scale;
        newVy *= scale;
      }
    }

    // Guarantee downward trend
    if (minDownVy > 0.0 && newVy < minDownVy) {
      newVy = minDownVy;
    }

    // Cap speed so it doesn't become absurd
    if (maxSpeed > 0.0) {
      double s = Math.hypot(newVx, newVy);
      if (s > maxSpeed && s > 1e-6) {
        double scale = maxSpeed / s;
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
