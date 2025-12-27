package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

public class HealItemBullet extends ItemBullet {

  private static Image sprite; // lazy-loaded

  public HealItemBullet(double x, double y) {
    super(x, y, ItemType.HEAL);
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (player instanceof PlayerItemEffect p) {
      p.healOne();
    }
  }

  @Override
  protected Image getSprite() {
    if (sprite != null) return sprite;

    try {
      var is = HealItemBullet.class.getResourceAsStream("/images/Item/heal.png");
      if (is == null) return null;
      sprite = new Image(is);
      return sprite;
    } catch (Throwable ignored) {
      return null;
    }
  }
}
