package org.thunderfighter.game.bullet;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.StraightTrajectory;

public class PlayerBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  public PlayerBullet(double startX, double startY, double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.size = new Dimension2D(6, 12); // 小号子弹
    this.dx = 0;
    this.dy = -12; // 向上
    this.speed = 12;

    this.fromPlayer = true;
    this.trajectory = new StraightTrajectory();
    this.lifeTicks = -1; // 出界消失
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
    gc.setFill(Color.YELLOW);
    gc.fillRect(x, y, size.getWidth(), size.getHeight());
  }
}
