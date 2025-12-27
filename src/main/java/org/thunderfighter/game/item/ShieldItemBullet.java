package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

public class ShieldItemBullet extends ItemBullet {

  private static Image sprite;
  private final int invincibleTicks;

  public ShieldItemBullet(
      double x, double y, double canvasW, double canvasH, int invincibleTicks) {
    super(x, y, ItemType.SHIELD, canvasW, canvasH);
    this.invincibleTicks = invincibleTicks;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (player instanceof PlayerItemEffect p) {
      p.setInvincibleTicks(invincibleTicks);
    }
  }

  @Override
  protected Image getSprite() {
    if (sprite != null) return sprite;
    try {
      var is = ShieldItemBullet.class.getResourceAsStream("/images/Item/shield.png");
      if (is == null) return null;
      sprite = new Image(is);
      return sprite;
    } catch (Throwable ignored) {
      return null;
    }
  }
}
