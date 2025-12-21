package org.thunderfighter.game.trajectory;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * DVD-style bouncing trajectory.
 * Bullet reflects when reaching canvas boundaries.
 *
 * Used by item bullets.
 */
public class BounceTrajectory implements Trajectory {

  @Override
  public void update(AbstractBullet b) {

    double nextX = b.getX() + b.getDx();
    double nextY = b.getY() + b.getDy();

    double width = b.getSize().getWidth();
    double height = b.getSize().getHeight();

    double canvasW = b.getCanvasW();
    double canvasH = b.getCanvasH();

    // Horizontal reflection
    if (nextX <= 0) {
      nextX = 0;
      b.setDx(-b.getDx());
    } else if (nextX + width >= canvasW) {
      nextX = canvasW - width;
      b.setDx(-b.getDx());
    }

    // Vertical reflection
    if (nextY <= 0) {
      nextY = 0;
      b.setDy(-b.getDy());
    } else if (nextY + height >= canvasH) {
      nextY = canvasH - height;
      b.setDy(-b.getDy());
    }

    b.setX(nextX);
    b.setY(nextY);
  }
}
