package org.thunderfighter.game.bullet;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.StraightTrajectory;

public class NormalEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  public NormalEnemyBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      boolean large,
      double canvasW,
      double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.size = large ? new Dimension2D(12, 24) : new Dimension2D(6, 12);
    this.dx = dx;
    this.dy = dy;
    this.speed = Math.hypot(dx, dy);

    this.fromPlayer = false;
    this.trajectory = new StraightTrajectory();
    this.lifeTicks = -1;
  }

  public NormalEnemyBullet(double startX, double startY) {
    super();
  }

  @Override
  public void update() {
    trajectory.update(this);
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
    gc.setFill(Color.ORANGE);
    gc.fillRect(x, y, size.getWidth(), size.getHeight());
  }
}
