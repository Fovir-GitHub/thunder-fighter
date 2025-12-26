package org.thunderfighter.game.bullet;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;

/**
 * 激光： - 持续 ticks（由 boss 传入） - 斩杀：takeDamage(Integer.MAX_VALUE) - 清屏：clearImmediately() 立刻消失
 *
 * <p>备注：红线预警/扇形扫射标记是 UI/画布层，不在这里做。
 */
public class LaserBullet extends AbstractBullet implements Clearable {

  private int remainTicks;
  private final double thickness;

  public LaserBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      int durationTicks,
      double thickness,
      double canvasW,
      double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.dx = dx; // 很快，但不要瞬移（你们可调到 0.5~0.7s到边界）
    this.dy = dy;
    this.speed = Math.hypot(dx, dy);

    this.thickness = thickness;
    this.size = new Dimension2D(thickness, thickness);

    this.fromPlayer = false;
    this.remainTicks = Math.max(1, durationTicks);
  }

  public double getThickness() {
    return thickness;
  }

  @Override
  public void update() {
    x += dx;
    y += dy;
    remainTicks--;
    if (remainTicks <= 0) aliveFlag = false;
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(Integer.MAX_VALUE); // 斩杀
    // 是否贯穿由机制决定：这里默认不消失
  }

  @Override
  public void clearImmediately() {
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.RED);
    gc.fillOval(x, y, thickness, thickness);
  }
}
