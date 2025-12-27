package org.thunderfighter.game.bullet;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.CurveTrajectory;

/**
 * CurveEnemyBullet
 *
 * <p>A curved bullet used for area control (restrict player movement). Curvature is controlled by
 * curveFactor.
 */
public class CurveEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Curved bullet sprite. */
  private static final Image SPRITE =
      new Image(CurveEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  public CurveEnemyBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      double curveFactor,
      double canvasW,
      double canvasH) {

    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.size = new Dimension2D(6, 12);
    this.dx = dx;
    this.dy = dy;
    this.speed = Math.hypot(dx, dy);

    this.fromPlayer = false;
    this.trajectory = new CurveTrajectory(curveFactor);
    this.lifeTicks = -1;
  }

  @Override
  public void update() {
    if (!aliveFlag) return;
    moveOnce();
    tickLife();
    killIfOutOfBounds();
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(SPRITE, x, y, size.getWidth(), size.getHeight());
  }
}
