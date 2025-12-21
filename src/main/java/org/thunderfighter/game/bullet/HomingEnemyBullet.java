package org.thunderfighter.game.bullet;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.HomingTrajectory;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HomingEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;
  private int trackingTicks;

  public HomingEnemyBullet(double startX, double startY,
                           double initDx, double initDy,
                           int trackingTicks,
                           HomingTrajectory.TargetProvider provider,
                           double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.size = new Dimension2D(6, 12);
    this.dx = initDx;
    this.dy = initDy;
    this.speed = Math.hypot(initDx, initDy);

    this.fromPlayer = false;
    this.trajectory = new HomingTrajectory(provider, 0.10);
    this.trackingTicks = Math.max(1, trackingTicks);
  }

  @Override
  public void update() {
    if (trackingTicks-- <= 0) {
      aliveFlag = false;
      return;
    }
    trajectory.update(this);
    killIfOutOfBounds();
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.PURPLE);
    gc.fillRect(x, y, size.getWidth(), size.getHeight());
  }
}
