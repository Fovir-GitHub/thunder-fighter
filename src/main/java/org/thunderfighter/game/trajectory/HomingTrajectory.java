// src/main/java/org/thunderfighter/game/trajectory/HomingTrajectory.java

package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * Homing trajectory. Gradually rotates the velocity vector toward a moving target.
 *
 * <p>turnStrength: 0.05 ~ 0.20 recommended. Higher value = stronger turning.
 */
public class HomingTrajectory implements Trajectory {

  /** Provides real-time target position (usually the player) */
  public interface TargetProvider {
    double getTargetX();

    double getTargetY();
  }

  private final TargetProvider provider;
  private final double turnStrength;

  public HomingTrajectory(TargetProvider provider, double turnStrength) {
    this.provider = provider;
    this.turnStrength = Math.max(0.0, Math.min(1.0, turnStrength));
  }

  @Override
  public void update(AbstractBullet b) {
    double bx = b.getX();
    double by = b.getY();

    double tx = provider.getTargetX() - bx;
    double ty = provider.getTargetY() - by;

    double targetLen = Math.hypot(tx, ty);
    if (targetLen < 1e-6) {
      // Target overlaps bullet; continue straight
      b.setX(bx + b.getDx());
      b.setY(by + b.getDy());
      return;
    }

    // Unit vector toward target
    double ux = tx / targetLen;
    double uy = ty / targetLen;

    // Current velocity direction
    double vx = b.getDx();
    double vy = b.getDy();
    double speed = Math.hypot(vx, vy);
    if (speed < 1e-6) speed = 1.0;

    double vxUnit = vx / speed;
    double vyUnit = vy / speed;

    // Smoothly rotate toward target
    double nx = vxUnit + (ux - vxUnit) * turnStrength;
    double ny = vyUnit + (uy - vyUnit) * turnStrength;

    double nLen = Math.hypot(nx, ny);
    if (nLen < 1e-6) nLen = 1.0;

    b.setDx((nx / nLen) * speed);
    b.setDy((ny / nLen) * speed);

    b.setX(bx + b.getDx());
    b.setY(by + b.getDy());
  }
}
