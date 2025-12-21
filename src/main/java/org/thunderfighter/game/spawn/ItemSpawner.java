package org.thunderfighter.game.spawn;

import java.util.Random;

import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.game.factory.BulletFactory;
import org.thunderfighter.game.item.ClearScreenHandler;
import org.thunderfighter.game.item.ItemType;
import org.thunderfighter.game.item.HealItemBullet;
import org.thunderfighter.game.item.ShieldItemBullet;
import org.thunderfighter.game.item.PowerItemBullet;
import org.thunderfighter.game.item.ClearItemBullet;

/**
 * ItemSpawner
 *
 * Spawns item-bullets (items are bullets) based on BR:
 * - Spawn interval: random integer seconds between 15 and 20
 * - Spawn position: distance to player >= 1/3 of canvas max diagonal distance
 * - Prefer the opposite half of the canvas relative to player's region
 * - Boss fight has higher priority than HP rules:
 *   1) In boss fight, reduce SHIELD and CLEAR probability first
 *   2) Then adjust HEAL probability (between high-HP and low-HP non-boss values)
 * - Item exists for 3 seconds; next spawn timer starts only after item disappears or is picked up
 *
 * Integration idea:
 * - World calls spawner.update(...)
 * - When spawner decides to spawn, it sends the item bullet to a listener (world adds into item list)
 */
public class ItemSpawner {

  /** Listener to deliver spawned item bullet to the game world. */
  public interface ItemSpawnListener {
    void onSpawn(Bullet itemBullet);
  }

  private static final int TPS = 60;

  private final Random rng = new Random();

  private final double canvasW;
  private final double canvasH;

  private final ItemSpawnListener listener;

  // Whether an item is currently active on the field
  private boolean hasActiveItem = false;

  // Countdown ticks until next spawn (only runs when no active item)
  private int nextSpawnTicks = 0;

  public ItemSpawner(double canvasW, double canvasH, ItemSpawnListener listener) {
    this.canvasW = canvasW;
    this.canvasH = canvasH;
    this.listener = listener;
    scheduleNextSpawn(); // initial schedule
  }

  /**
   * Notify spawner that the current active item is gone (picked up or expired).
   * This will restart the spawn timer.
   */
  public void notifyItemRemoved() {
    hasActiveItem = false;
    scheduleNextSpawn();
  }

  /** Whether an item is currently present in the field. */
  public boolean hasActiveItem() {
    return hasActiveItem;
  }

  /**
   * Update spawner once per tick.
   *
   * @param player Player aircraft (for position & hp)
   * @param bossFight Whether currently in boss fight
   * @param clearHandler World handler (required only for creating CLEAR item bullet)
   */
  public void update(Aircraft player, boolean bossFight, ClearScreenHandler clearHandler) {
    if (hasActiveItem) return; // do nothing until current item is removed

    if (nextSpawnTicks > 0) {
      nextSpawnTicks--;
      return;
    }

    // Time to spawn a new item
    spawnOne(player, bossFight, clearHandler);
  }

  // ------------------------------------------------------------
  // Internal: scheduling
  // ------------------------------------------------------------

  /** Schedules next spawn after random integer seconds between 15 and 20. */
  private void scheduleNextSpawn() {
    int seconds = 15 + rng.nextInt(6); // 15..20 inclusive
    nextSpawnTicks = seconds * TPS;
  }

  // ------------------------------------------------------------
  // Internal: spawn logic
  // ------------------------------------------------------------

  private void spawnOne(Aircraft player, boolean bossFight, ClearScreenHandler clearHandler) {
    // Choose item type by probability rules
    ItemType type = rollType(player, bossFight);

    // Compute spawn position by distance + opposite-half bias
    double[] pos = computeSpawnPosition(player.getCollisionBounds().getMinX(),
                                        player.getCollisionBounds().getMinY());

    double x = pos[0];
    double y = pos[1];

    Bullet item = createItemBullet(type, x, y, clearHandler);

    if (item != null && listener != null) {
      listener.onSpawn(item);
      hasActiveItem = true;
    } else {
      // If creation fails for some reason, schedule again to avoid stuck
      scheduleNextSpawn();
    }
  }

  private Bullet createItemBullet(ItemType type, double x, double y, ClearScreenHandler clearHandler) {
    // Item lifetime is handled inside ItemBullet (3 seconds) as lifeTicks=180, per your design
    return switch (type) {
      case HEAL -> BulletFactory.createHealItem(x, y, canvasW, canvasH);
      case SHIELD -> BulletFactory.createShieldItem(x, y, canvasW, canvasH, 5 * TPS);
      case POWER -> BulletFactory.createPowerItem(x, y, canvasW, canvasH, 10 * TPS, 1);
      case CLEAR -> BulletFactory.createClearItem(x, y, canvasW, canvasH, clearHandler, 1 * TPS);
    };
  }

  /**
   * Probability rules summary:
   * Non-boss:
   * - If HP == 3 (full), slightly increase HEAL probability
   * - If HP == 1 (critical), slightly decrease HEAL probability
   *
   * Boss fight priority:
   * - Reduce SHIELD and CLEAR first (strong items)
   * - Then set HEAL probability to a middle value (between high-HP and low-HP non-boss)
   */
  private ItemType rollType(Aircraft player, boolean bossFight) {
    int hp = player.getHp();

    // Base weights (sum not required to be 1.0)
    double wPower = 1.0;
    double wHeal  = 1.0;
    double wShield = 1.0;
    double wClear  = 1.0;

    // ---- Non-boss HP rule ----
    if (!bossFight) {
      if (hp >= 3) {
        // Full HP: increase HEAL probability (per your BR)
        wHeal = 1.3;
      } else if (hp <= 1) {
        // Critical: reduce HEAL probability (per your BR)
        wHeal = 0.7;
      }
    }

    // ---- Boss fight priority rule ----
    if (bossFight) {
      // Boss fight: reduce SHIELD and CLEAR first
      wShield *= 0.4;
      wClear  *= 0.4;

      // HEAL in boss fight should be between non-boss high/low extremes
      // We'll set it to a moderate value
      wHeal = 1.0;

      // (Optional) You can also slightly increase POWER during boss fights if desired:
      // wPower *= 1.1;
    }

    // Normalize by roulette wheel selection
    double total = wPower + wHeal + wShield + wClear;
    double r = rng.nextDouble() * total;

    if ((r -= wPower) < 0) return ItemType.POWER;
    if ((r -= wHeal) < 0) return ItemType.HEAL;
    if ((r -= wShield) < 0) return ItemType.SHIELD;
    return ItemType.CLEAR;
  }

  /**
   * Computes a spawn position such that:
   * - Distance to player >= diagonal(canvas)/3
   * - Prefer opposite half relative to player's current region
   *
   * Implementation detail:
   * - Determine player's region by comparing to canvas center
   * - Opposite half bias: if player is left, spawn on right; if top, spawn on bottom, etc.
   * - Then sample random points until the distance rule is satisfied.
   */
  private double[] computeSpawnPosition(double playerX, double playerY) {
    double diag = Math.hypot(canvasW, canvasH);
    double minDist = diag / 3.0;

    boolean playerLeft = playerX < canvasW / 2.0;
    boolean playerTop  = playerY < canvasH / 2.0;

    // Target spawn ranges biased to opposite half
    double minX = playerLeft ? canvasW / 2.0 : 0.0;
    double maxX = playerLeft ? canvasW : canvasW / 2.0;

    double minY = playerTop ? canvasH / 2.0 : 0.0;
    double maxY = playerTop ? canvasH : canvasH / 2.0;

    // Try multiple attempts to find a valid position
    for (int attempt = 0; attempt < 40; attempt++) {
      double x = minX + rng.nextDouble() * (maxX - minX);
      double y = minY + rng.nextDouble() * (maxY - minY);

      double d = Math.hypot(x - playerX, y - playerY);
      if (d >= minDist) {
        return new double[] { x, y };
      }
    }

    // Fallback: if we fail to find a point in biased half, search whole canvas
    for (int attempt = 0; attempt < 80; attempt++) {
      double x = rng.nextDouble() * canvasW;
      double y = rng.nextDouble() * canvasH;

      double d = Math.hypot(x - playerX, y - playerY);
      if (d >= minDist) {
        return new double[] { x, y };
      }
    }

    // Worst-case fallback: return a corner far away from the player
    return new double[] { playerX < canvasW / 2.0 ? canvasW - 20 : 20,
                          playerY < canvasH / 2.0 ? canvasH - 20 : 20 };
  }
}
