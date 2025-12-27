package org.thunderfighter.game.item;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.ItemLike;
import org.thunderfighter.game.trajectory.BounceTrajectory;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ItemBullet extends AbstractBullet implements ItemLike {
  public static final int TPS = 60;
  public static final  int DEFAULT_LIFE_TICKS = 3 * TPS;
  private static final double DEFAULT_PER_TICK_SPEED = 3.0;

  protected static final Dimension2D ITEM_SIZE = new Dimension2D(44, 44);

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

  @Override
  public void update() {
    if (!aliveFlag) return;
    moveOnce();
    tickLife();
  }

  @Override
  public void onHit(Aircraft target) {
    if (!target.isPlayer()) return;
    applyEffect(target);
    aliveFlag = false;
  }

  protected abstract void applyEffect(Aircraft player);

  /** Subclass must provide sprite (return null -> won't render). */
  protected abstract Image getSprite();

  @Override
  public final void draw(GraphicsContext gc) {
    if (!aliveFlag) return;

    Image sprite;
    try {
      sprite = getSprite();
    } catch (Throwable ignored) {
      sprite = null;
    }

    if (sprite == null) return; // no fallback
    gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
  }
}
