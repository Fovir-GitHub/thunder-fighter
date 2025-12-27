package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

/**
 * Clear item:
 *
 * Behavior:
 * - Item is a bullet-like entity (inherits ItemBullet)
 * - Bounces inside the canvas (DVD-style)
 * - Larger than normal bullets (size defined in ItemBullet: 32x32)
 * - Lifetime: 3 seconds (handled by ItemBullet)
 *
 * Effect:
 * - Immediately clears all enemy bullets (including lasers)
 * - Clears normal enemies, but keeps elite enemies and bosses
 * - Opens a short "clear window" during which newly spawned enemy bullets are removed
 *
 * Note:
 * - Actual clear logic is delegated to ClearScreenHandler (world-side implementation)
 */
public class ClearItemBullet extends ItemBullet {

  /** Sprite for clear item. */
  private static final Image SPRITE =
      new Image(ClearItemBullet.class.getResourceAsStream("/images/item/clear.png"));

  private final ClearScreenHandler handler;
  private final int clearWindowTicks;

  public ClearItemBullet(
      double x,
      double y,
      double canvasW,
      double canvasH,
      ClearScreenHandler handler,
      int clearWindowTicks) {

    super(x, y, ItemType.CLEAR, canvasW, canvasH);
    this.handler = handler;
    this.clearWindowTicks = clearWindowTicks;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (handler == null) return;

    // Immediate clear
    handler.clearEnemyBulletsNow();   // includes lasers
    handler.clearNormalEnemies();     // keep elite enemies & boss

    // Clear window (e.g. 1 second @ 60 TPS)
    handler.startClearWindow(clearWindowTicks);
  }

  @Override
  protected Image getSprite() {
    return SPRITE;
  }
}
