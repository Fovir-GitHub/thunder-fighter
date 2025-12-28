package org.thunderfighter.game.trajectory;

import javafx.scene.canvas.Canvas;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * DVD-style bouncing trajectory. Bullet reflects when reaching canvas boundaries.
 *
 * <p>Used by item bullets.
 */
public class BounceTrajectory implements Trajectory {

  @Override
  public void update(AbstractBullet b) {

    Canvas canvas = b.getCanvas();
    if (canvas == null) return; // 防止尚未注入 canvas 时 NPE

    double nextX = b.getX() + b.getDx();
    double nextY = b.getY() + b.getDy();

    double width = b.getSize().getWidth();
    double height = b.getSize().getHeight();

    double canvasW = canvas.getWidth();
    double canvasH = canvas.getHeight();

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
