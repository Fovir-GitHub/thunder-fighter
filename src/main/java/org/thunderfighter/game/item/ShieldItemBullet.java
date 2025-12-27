package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

/**
 * Shield item:
 * - Grants invincibility for invincibleTicks
 */
public class ShieldItemBullet extends ItemBullet {

  private static final Image SPRITE =
      new Image(ShieldItemBullet.class.getResourceAsStream("/images/Item/shield.png"));

  private final int invincibleTicks;

  public ShieldItemBullet(double x, double y, double canvasW, double canvasH, int invincibleTicks) {
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
    return SPRITE;
  }
}
