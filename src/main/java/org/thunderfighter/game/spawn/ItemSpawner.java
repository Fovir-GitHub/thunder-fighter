package org.thunderfighter.game.spawn;

import java.util.Random;

import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.game.bulletfactory.BulletFactory; // ✅ correct package
import org.thunderfighter.game.item.ClearScreenHandler;
import org.thunderfighter.game.item.ItemType;

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

  private boolean hasActiveItem = false;
  private int nextSpawnTicks = 0;

  public ItemSpawner(double canvasW, double canvasH, ItemSpawnListener listener) {
    this.canvasW = canvasW;
    this.canvasH = canvasH;
    this.listener = listener;
    scheduleNextSpawn();
  }

  /** Notify that current item was removed (picked up or expired). */
  public void notifyItemRemoved() {
    hasActiveItem = false;
    scheduleNextSpawn();
  }

  public boolean hasActiveItem() {
    return hasActiveItem;
  }

  public void update(Aircraft player, boolean bossFight, ClearScreenHandler clearHandler) {
    if (hasActiveItem) return;

    if (nextSpawnTicks > 0) {
      nextSpawnTicks--;
      return;
    }

    spawnOne(player, bossFight, clearHandler);
  }

  /** Schedules next spawn after random integer seconds between 15 and 20. */
  private void scheduleNextSpawn() {
    int seconds = 15 + rng.nextInt(6); // 15..20 inclusive
    nextSpawnTicks = seconds * TPS;
  }

  private void spawnOne(Aircraft player, boolean bossFight, ClearScreenHandler clearHandler) {
    ItemType type = rollType(player, bossFight);

    double[] pos =
        computeSpawnPosition(
            player.getCollisionBounds().getMinX(),
            player.getCollisionBounds().getMinY());

    double x = pos[0];
    double y = pos[1];

    Bullet item = createItemBullet(type, x, y, clearHandler);

    if (item != null && listener != null) {
      listener.onSpawn(item);
      hasActiveItem = true;
    } else {
      scheduleNextSpawn();
    }
  }

  /**
   * Create an item bullet via BulletFactory.
   * NOTE: BulletFactory methods are static. Call them by class name.
   */
  private Bullet createItemBullet(ItemType type, double x, double y, ClearScreenHandler clearHandler) {
    return switch (type) {
      case HEAL -> BulletFactory.createHealItem(x, y, canvasW, canvasH);

      case SHIELD -> BulletFactory.createShieldItem(
          x, y, canvasW, canvasH,
          5 * TPS // 5 seconds invincibility
      );

      case POWER -> BulletFactory.createPowerItem(
          x, y, canvasW, canvasH,
          10 * TPS, // 10 seconds buff
          1         // +1 bonus damage (adjust if needed)
      );

      case CLEAR -> BulletFactory.createClearItem(
          x, y, canvasW, canvasH,
          clearHandler,
          1 * TPS // 1 second clear window
      );
    };
  }

  private ItemType rollType(Aircraft player, boolean bossFight) {
    int hp = player.getHp();

    double wPower = 1.0;
    double wHeal = 1.0;
    double wShield = 1.0;
    double wClear = 1.0;

    if (!bossFight) {
      if (hp >= 3) {
        wHeal = 1.3; // full HP -> slightly increase HEAL chance (per your BR)
      } else if (hp <= 1) {
        wHeal = 0.7; // critical -> slightly decrease HEAL chance (per your BR)
      }
    }

    if (bossFight) {
      // Boss priority: reduce SHIELD and CLEAR first
      wShield *= 0.4;
      wClear *= 0.4;

      // HEAL becomes moderate value in boss fight
      wHeal = 1.0;
    }

    double total = wPower + wHeal + wShield + wClear;
    double r = rng.nextDouble() * total;

    if ((r -= wPower) < 0) return ItemType.POWER;
    if ((r -= wHeal) < 0) return ItemType.HEAL;
    if ((r -= wShield) < 0) return ItemType.SHIELD;
    return ItemType.CLEAR;
  }

  private double[] computeSpawnPosition(double playerX, double playerY) {
    double diag = Math.hypot(canvasW, canvasH);
    double minDist = diag / 3.0;

    boolean playerLeft = playerX < canvasW / 2.0;
    boolean playerTop = playerY < canvasH / 2.0;

    double minX = playerLeft ? canvasW / 2.0 : 0.0;
    double maxX = playerLeft ? canvasW : canvasW / 2.0;

    double minY = playerTop ? canvasH / 2.0 : 0.0;
    double maxY = playerTop ? canvasH : canvasH / 2.0;

    for (int attempt = 0; attempt < 40; attempt++) {
      double x = minX + rng.nextDouble() * (maxX - minX);
      double y = minY + rng.nextDouble() * (maxY - minY);
      if (Math.hypot(x - playerX, y - playerY) >= minDist) {
        return new double[] {x, y};
      }
    }

    for (int attempt = 0; attempt < 80; attempt++) {
      double x = rng.nextDouble() * canvasW;
      double y = rng.nextDouble() * canvasH;
      if (Math.hypot(x - playerX, y - playerY) >= minDist) {
        return new double[] {x, y};
      }
    }

    return new double[] {
      playerX < canvasW / 2.0 ? canvasW - 20 : 20,
      playerY < canvasH / 2.0 ? canvasH - 20 : 20
    };
  }
}
