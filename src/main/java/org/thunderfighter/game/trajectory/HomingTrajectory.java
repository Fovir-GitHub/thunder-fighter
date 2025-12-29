// src/main/java/org/thunderfighter/game/trajectory/HomingTrajectory.java

package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * HomingTrajectory
 *
 * Implements a homing movement that gradually rotates the bullet's
 * velocity vector toward a moving target.
 *
 * This produces smooth tracking behavior instead of instant turning,
 * which feels more natural and fair in gameplay.
 *
 * turnStrength recommended range: 0.05 ~ 0.20
 */
public class HomingTrajectory implements Trajectory {

  /**
   * TargetProvider
   *
   * Supplies real-time target coordinates, typically the player.
   * This abstraction avoids direct dependency on player classes.
   */
  public interface TargetProvider {
    double getTargetX();
    double getTargetY();
  }

  private final TargetProvider provider;
  private final double turnStrength;

  /**
   * Constructs a homing trajectory.
   *
   * @param provider supplies target position
   * @param turnStrength interpolation factor for turning (clamped to [0,1])
   */
  public HomingTrajectory(TargetProvider provider, double turnStrength) {
    this.provider = provider;
    this.turnStrength = Math.max(0.0, Math.min(1.0, turnStrength));
  }

  /**
   * Updates bullet direction and position for one frame.
   *
   * @param b bullet instance to update
   */
  @Override
  public void update(AbstractBullet b) {
    // Current bullet position
    double bx = b.getX();
    double by = b.getY();

    // Vector from bullet to target
    double tx = provider.getTargetX() - bx;
    double ty = provider.getTargetY() - by;

    double targetLen = Math.hypot(tx, ty);
    if (targetLen < 1e-6) {
      // Target overlaps bullet; continue straight without turning
      b.setX(bx + b.getDx());
      b.setY(by + b.getDy());
      return;
    }

    // Normalized direction toward target
    double ux = tx / targetLen;
    double uy = ty / targetLen;

    // Current velocity and speed
    double vx = b.getDx();
    double vy = b.getDy();
    double speed = Math.hypot(vx, vy);
    if (speed < 1e-6) speed = 1.0;

    // Current velocity unit vector
    double vxUnit = vx / speed;
    double vyUnit = vy / speed;

    // Interpolate direction toward target (smooth turning)
    double nx = vxUnit + (ux - vxUnit) * turnStrength;
    double ny = vyUnit + (uy - vyUnit) * turnStrength;

    double nLen = Math.hypot(nx, ny);
    if (nLen < 1e-6) nLen = 1.0;

    // Restore original speed after direction change
    b.setDx((nx / nLen) * speed);
    b.setDy((ny / nLen) * speed);

    // Move bullet using updated velocity
    b.setX(bx + b.getDx());
    b.setY(by + b.getDy());
  }
}
