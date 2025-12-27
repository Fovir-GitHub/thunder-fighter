package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

public class PowerItemBullet extends ItemBullet {

  private static Image sprite;
  private final int buffTicks;
  private final int bonusDamage;

  public PowerItemBullet(
      double x, double y, double canvasW, double canvasH, int buffTicks, int bonusDamage) {
    super(x, y, ItemType.POWER, canvasW, canvasH);
    this.buffTicks = buffTicks;
    this.bonusDamage = bonusDamage;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (player instanceof PlayerItemEffect p) {
      p.setPowerBuffTicks(buffTicks, bonusDamage);
    }
  }

  @Override
  protected Image getSprite() {
    if (sprite != null) return sprite;
    try {
      var is = PowerItemBullet.class.getResourceAsStream("/images/Item/power.png");
      if (is == null) return null;
      sprite = new Image(is);
      return sprite;
    } catch (Throwable ignored) {
      return null;
    }
  }
}
