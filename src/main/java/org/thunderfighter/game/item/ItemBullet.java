package org.thunderfighter.game.item;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.BounceTrajectory;

/**
 * ItemBullet (Items are bullets)
 *
 * <p>Design: - Items belong to Bullet system - Only interact with player aircraft (pickup) -
 * DVD-style bouncing inside the canvas - Lifetime: 3 seconds by default - Visual: sprite-based only
 *
 * <p>Notes: - Item lifecycle is fully handled by BulletManager via aliveFlag - draw() is FINAL - If
 * sprite is missing, item will not be rendered (no fallback graphics)
 */
public abstract class ItemBullet extends AbstractBullet {

  /** Ticks per second (used only inside item module). */
  public static final int TPS = 60;

  /** Default lifetime: 3 seconds. */
  public static final int DEFAULT_LIFE_TICKS = 3 * TPS;

  /** Base speed for DVD-style bouncing. */
  private static final double DEFAULT_PER_TICK_SPEED = 3.0;

  /** Items are clearly larger than bullets. */
  protected static final Dimension2D ITEM_SIZE = new Dimension2D(44, 44);

  protected final ItemType type;

  /**
   * Constructor.
   *
   * <p>Canvas is injected later via AbstractEntity.setCanvas(...)
   */
  protected ItemBullet(double startX, double startY, ItemType type) {

    // position & origin
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    // item metadata
    this.type = type;
    this.size = ITEM_SIZE;

    // lifetime
    this.lifeTicks = DEFAULT_LIFE_TICKS;

    // random initial direction (DVD-style bounce)
    double angle = Math.random() * Math.PI * 2.0;
    this.dx = Math.cos(angle) * DEFAULT_PER_TICK_SPEED;
    this.dy = Math.sin(angle) * DEFAULT_PER_TICK_SPEED;
    this.speed = DEFAULT_PER_TICK_SPEED;

    this.fromPlayer = false;
    this.trajectory = new BounceTrajectory();
  }

  public ItemType getType() {
    return type;
  }

  /** Radius for circular semantic meaning (still useful for logic if needed). */
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
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;
    moveOnce();
    tickLife();
    // No killIfOutOfBounds(): BounceTrajectory keeps it inside canvas
  }

  @Override
  public void onHit(Aircraft target) {
    // Only player can pick up items
    if (!target.isPlayer()) return;

    applyEffect(target);
    aliveFlag = false;
  }

  /** Apply item effect to the player (heal / shield / power / clear). */
  protected abstract void applyEffect(Aircraft player);

  /** Subclass must provide sprite (should not return null in production). */
  protected abstract Image getSprite();

  /** Fallback color so item is ALWAYS visible even if sprite missing. */
  private Color getFallbackColor() {
    return switch (type) {
      case HEAL -> Color.RED;
      case POWER -> Color.GOLD;
      case SHIELD -> Color.CYAN;
      case CLEAR -> Color.WHITE;
    };
  }

  /** FINAL draw: guarantees visibility. */
  @Override
  public final void draw(GraphicsContext gc) {
    if (!aliveFlag) return;

    Image sprite = null;
    try {
      sprite = getSprite();
    } catch (Throwable ignored) {
    }

    if (sprite != null) {
      gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
    } else {
      gc.setFill(getFallbackColor());
      gc.fillOval(x, y, size.getWidth(), size.getHeight());
    }
  }
}
