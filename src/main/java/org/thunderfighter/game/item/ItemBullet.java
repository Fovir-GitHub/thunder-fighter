package org.thunderfighter.game.item;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.BounceTrajectory;

/**
 * 道具 = 子弹（重要：归属于 Bullet 体系）
 * - 只与玩家飞机发生碰撞
 * - DVD 反弹（BounceTrajectory）
 * - 存活 3 秒：默认 180 ticks（按 60 TPS）
 */
public abstract class ItemBullet extends AbstractBullet {

  protected final ItemType type;

  protected ItemBullet(double startX, double startY, ItemType type, double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.type = type;
    this.size = new Dimension2D(18, 18);

    // 3秒（如果你们 TPS 不是 60，后面统一改成 TPS*3）
    this.lifeTicks = 180;

    // 随机方向
    double angle = Math.random() * Math.PI * 2;
    double perTick = 3.0;
    this.dx = Math.cos(angle) * perTick;
    this.dy = Math.sin(angle) * perTick;
    this.speed = perTick;

    this.fromPlayer = false;
    this.trajectory = new BounceTrajectory();
  }

  public ItemType getType() { return type; }

  @Override
  public void update() {
    trajectory.update(this);
    tickLife();
  }

  @Override
  public void onHit(Aircraft target) {
    if (!target.isPlayer()) return; // 只允许玩家拾取
    applyEffect(target);
    alive_flag = false;
  }

  protected abstract void applyEffect(Aircraft player);

  @Override
  public abstract void draw(GraphicsContext gc);
}
