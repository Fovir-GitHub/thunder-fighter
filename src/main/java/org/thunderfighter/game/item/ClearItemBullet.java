package org.thunderfighter.game.item;

import javafx.scene.image.Image;
import org.thunderfighter.core.entity.Aircraft;

/**
 * ClearItemBullet
 *
 * <p>Item bullet that clears enemy bullets and enemies on pickup. Canvas bounds are handled via
 * AbstractEntity.setCanvas(...).
 */
public class ClearItemBullet extends ItemBullet {

  private static Image sprite;

  private final ClearScreenHandler handler;
  private final int clearWindowTicks;

  /**
   * Constructor.
   *
   * @param x spawn x
   * @param y spawn y
   * @param handler clear-screen handler (game-world level)
   * @param clearWindowTicks duration of the clear window
   */
  public ClearItemBullet(double x, double y, ClearScreenHandler handler, int clearWindowTicks) {

    super(x, y, ItemType.CLEAR);
    this.handler = handler;
    this.clearWindowTicks = clearWindowTicks;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (handler != null) {
      handler.clearEnemyBulletsNow();
      handler.clearNormalEnemies();
      handler.startClearWindow(clearWindowTicks);
    }
  }

  @Override
  protected Image getSprite() {
    if (sprite != null) return sprite;

    try {
      var is = ClearItemBullet.class.getResourceAsStream("/images/Item/clear.png");
      if (is == null) return null;
      sprite = new Image(is);
      return sprite;
    } catch (Throwable ignored) {
      return null;
    }
  }
}
