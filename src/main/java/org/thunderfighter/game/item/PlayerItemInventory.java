package org.thunderfighter.game.item;

import java.util.Optional;
import org.thunderfighter.core.entity.Aircraft;

/**
 * PlayerItemInventory
 *
 * <p>Rule: - Player can hold at most ONE item at a time. - Picking up another item replaces the
 * currently held item. - Item can be used via a key press (world/input system triggers useHeld()).
 *
 * <p>Notes: - You can choose whether HEAL triggers immediately or is stored. Here we implement
 * "store then use", because your BR explicitly asked for inventory + release key. If your team
 * decides HEAL should be immediate, you can special-case it in pickup().
 */
public class PlayerItemInventory {

  private ItemType heldType = null;

  /** Returns currently held item type, or empty if none. */
  public Optional<ItemType> getHeldType() {
    return Optional.ofNullable(heldType);
  }

  /** Whether the player is currently holding an item. */
  public boolean hasItem() {
    return heldType != null;
  }

  /** Clears held item without using it. */
  public void clear() {
    heldType = null;
  }

  /** Pick up an item. Rule: replace existing held item if any. */
  public void pickup(ItemType type) {
    heldType = type;
  }

  /**
   * Use currently held item (trigger effect) and clear inventory.
   *
   * <p>Requirements: - Player can still pick up and use items during invincibility. - Clear-screen
   * requires world handler cooperation.
   */
  public void useHeld(Aircraft player, ClearScreenHandler world) {
    if (heldType == null) return;

    // Apply effect based on item type
    switch (heldType) {
      case HEAL -> {
        if (player instanceof PlayerItemEffect p) {
          p.healOne();
        }
      }
      case SHIELD -> {
        if (player instanceof PlayerItemEffect p) {
          // 5 seconds invincibility @ 60 TPS
          p.setInvincibleTicks(5 * 60);
        }
      }
      case POWER -> {
        if (player instanceof PlayerItemEffect p) {
          // 10 seconds damage boost @ 60 TPS, bonusDamage=+1 (adjust as needed)
          p.setPowerBuffTicks(10 * 60, 1);
        }
      }
      case CLEAR -> {
        if (world != null) {
          world.clearEnemyBulletsNow();
          world.clearNormalEnemies();
          // 1 second clear window @ 60 TPS
          world.startClearWindow(1 * 60);
        }
      }
      default -> {
        // no-op
      }
    }

    // After usage, clear inventory
    heldType = null;
  }
}
