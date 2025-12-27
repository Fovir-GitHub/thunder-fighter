package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * StraightTrajectory
 *
 * <p>Each tick moves the bullet by its velocity vector (dx, dy).
 */
public class StraightTrajectory implements Trajectory {

  @Override
  public void update(AbstractBullet b) {
    b.setX(b.getX() + b.getDx());
    b.setY(b.getY() + b.getDy());
  }
}
