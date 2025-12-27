package org.thunderfighter.game.item;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.BounceTrajectory;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * ItemBullet (Items are bullets)
 *
 * Rules:
 * - Items belong to Bullet system (managed by BulletManager if you want)
 * - Only interact with player aircraft (pickup)
 * - DVD-style bouncing in canvas (BounceTrajectory)
 * - Lifetime: 3 seconds by default
 * - Visual: bigger than bullets, use sprite if provided
 *
 * Design choice:
 * - draw(...) is FINAL and unified here to avoid "not overriding draw" errors.
 * - Subclasses ONLY provide sprite + effect logic.
 */
public abstract class ItemBullet extends AbstractBullet {

  /** Keep TPS in ONE place for item module. */
  public static final int TPS = 60;

  /** Item lifetime in ticks (3 seconds). */
  public static final int DEFAULT_LIFE_TICKS = 3 * TPS;

  /** Item speed per tick. */
  private static final double DEFAULT_PER_TICK_SPEED = 3.0;

  /** Item size (bigger than normal bullets). */
  protected static final Dimension2D ITEM_SIZE = new Dimension2D(32, 32);

  protected final ItemType type;

  protected ItemBullet(double startX, double startY, ItemType type, double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.type = type;
    this.size = ITEM_SIZE;

    this.lifeTicks = DEFAULT_LIFE_TICKS;

    // Random initial direction
    double angle = Math.random() * Math.PI * 2;
    this.dx = Math.cos(angle) * DEFAULT_PER_TICK_SPEED;
    this.dy = Math.sin(angle) * DEFAULT_PER_TICK_SPEED;
    this.speed = DEFAULT_PER_TICK_SPEED;

    // Items are not fired by the player
    this.fromPlayer = false;

    // DVD bounce
    this.trajectory = new BounceTrajectory();
  }

  public ItemType getType() {
    return type;
  }

  /** Radius used for circular semantic (optional for your collision system). */
  public final double getRadius() {
    return size.getWidth() / 2.0;
  }

  public final double getCenterX() {
    return x + getRadius();
  }

  public final double getCenterY() {
    return y + getRadius();
  }

  @Override
  public final void update() {
    if (!aliveFlag) return;
    moveOnce();
    tickLife();
  }

  @Override
  public final void onHit(Aircraft target) {
    if (!target.isPlayer()) return;
    applyEffect(target);
    aliveFlag = false;
  }

  /** Apply item effect to the player. */
  protected abstract void applyEffect(Aircraft player);

  /** Subclass provides sprite (return null to fallback to simple circle). */
  protected abstract Image getSprite();

  /**
   * Unified render:
   * - If sprite exists, draw it
   * - Otherwise draw a simple circle (debug)
   */
  @Override
  public final void draw(GraphicsContext gc) {
    if (!aliveFlag) return;

    Image sprite = getSprite();
    if (sprite != null) {
      gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
    } else {
      gc.setFill(Color.WHITE);
      gc.fillOval(x, y, size.getWidth(), size.getHeight());
    }
  }
}
