package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

public class ClearItemBullet extends ItemBullet {

  private static Image sprite;

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
