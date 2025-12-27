package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.image.Image;

/**
 * Heal item:
 * - Item is a bullet-like entity (inherits ItemBullet)
 * - Bounces like DVD logo (handled by ItemBullet/BounceTrajectory)
 * - Larger than normal bullets (size is set in ItemBullet: 32x32)
 * - Lifetime is 3 seconds (handled by ItemBullet)
 *
 * Effect:
 * - Heal +1 (cap logic should be implemented inside PlayerAircraft / PlayerItemEffect)
 * - This item only applies when picked up by the player (handled by ItemBullet.onHit)
 */
public class HealItemBullet extends ItemBullet {

  /** Sprite for heal item (ensure the file exists under resources). */
  private static final Image SPRITE =
      new Image(HealItemBullet.class.getResourceAsStream("/images/item/heal.png"));

  public HealItemBullet(double x, double y, double canvasW, double canvasH) {
    super(x, y, ItemType.HEAL, canvasW, canvasH);
  }

  @Override
  protected void applyEffect(Aircraft player) {
    // Decoupled effect application:
    // - Do NOT hard-code PlayerAircraft class name here.
    // - PlayerItemEffect is the recommended bridge interface implemented by PlayerAircraft.
    if (player instanceof PlayerItemEffect p) {
      p.healOne(); // PlayerAircraft handles HP cap (e.g., max HP = 3)
    }
  }

  @Override
  protected Image getSprite() {
    return SPRITE;
  }
}
