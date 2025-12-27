package org.thunderfighter.game.item;

import java.util.Optional;

import org.thunderfighter.core.entity.Aircraft;

/**
 * PlayerItemInventory
 *
 * Rules:
 * - Player can hold at most ONE item
 * - Picking up another item replaces the current held one
 * - Use item via key press (E)
 *
 * Note:
 * - This version stores ALL items until used.
 *   If your team decides HEAL should be immediate, special-case it in pickup().
 */
public class PlayerItemInventory {

  private ItemType heldType = null;

  public Optional<ItemType> getHeldType() {
    return Optional.ofNullable(heldType);
  }

  public boolean hasItem() {
    return heldType != null;
  }

  public void clear() {
    heldType = null;
  }

  public void pickup(ItemType type) {
    heldType = type;
  }

  public void useHeld(Aircraft player, ClearScreenHandler world) {
    if (heldType == null) return;

    final int TPS = ItemBullet.TPS;

    switch (heldType) {
      case HEAL -> {
        if (player instanceof PlayerItemEffect p) {
          p.healOne();
        }
      }
      case SHIELD -> {
        if (player instanceof PlayerItemEffect p) {
          p.setInvincibleTicks(5 * TPS);
        }
      }
      case POWER -> {
        if (player instanceof PlayerItemEffect p) {
          p.setPowerBuffTicks(10 * TPS, 1); // bonusDamage=+1
        }
      }
      case CLEAR -> {
        if (world != null) {
          world.clearEnemyBulletsNow();
          world.clearNormalEnemies();
          world.startClearWindow(1 * TPS);
        }
      }
      default -> {}
    }

    heldType = null;
  }
}
