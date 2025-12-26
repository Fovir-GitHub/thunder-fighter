package org.thunderfighter.game.item;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.BounceTrajectory;

/**
 * ItemBullet (Items are bullets)
 *
 * <p>Requirements: - Items belong to the Bullet system. - Only collide with the player aircraft
 * (pickup). - Bounce within canvas like a DVD logo (BounceTrajectory). - Lifetime: 3 seconds
 * (default 180 ticks @ 60 TPS). - When picked up or expired, the item becomes not alive (removed by
 * world cleanup).
 */
public abstract class ItemBullet extends AbstractBullet {

  /** Default ticks per second used by this module. */
  private static final int TPS = 60;

  /** Item lifetime in ticks: 3 seconds. */
  private static final int DEFAULT_LIFE_TICKS = 3 * TPS;

  /** Item base speed per tick (DVD-style). */
  private static final double DEFAULT_PER_TICK_SPEED = 3.0;

  protected final ItemType type;

  protected ItemBullet(
      double startX, double startY, ItemType type, double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.type = type;
    this.size = new Dimension2D(18, 18);

    // Item lifetime (3 seconds by default)
    this.lifeTicks = DEFAULT_LIFE_TICKS;

    // Random initial direction
    double angle = Math.random() * Math.PI * 2;
    this.dx = Math.cos(angle) * DEFAULT_PER_TICK_SPEED;
    this.dy = Math.sin(angle) * DEFAULT_PER_TICK_SPEED;
    this.speed = DEFAULT_PER_TICK_SPEED;

    // Items are not fired by the player
    this.fromPlayer = false;

    // DVD-style bouncing
    this.trajectory = new BounceTrajectory();
  }

  public ItemType getType() {
    return type;
  }

  @Override
  public void update() {
    trajectory.update(this);
    tickLife();
  }

  @Override
  public void onHit(Aircraft target) {
    // Items can only be picked up by the player
    if (!target.isPlayer()) return;

    applyEffect(target);

    // Mark as dead so the world removes it
    aliveFlag = false;
  }

  /**
   * Apply the item effect to the player. Implementation depends on item type
   * (heal/shield/power/clear).
   */
  protected abstract void applyEffect(Aircraft player);

  @Override
  public abstract void draw(GraphicsContext gc);
}
