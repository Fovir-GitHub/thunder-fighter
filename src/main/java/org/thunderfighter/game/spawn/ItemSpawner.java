package org.thunderfighter.game.spawn;

import java.util.Random;

import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.manager.ItemManager;
import org.thunderfighter.game.bulletfactory.BulletFactory;
import org.thunderfighter.game.item.ClearScreenHandler;
import org.thunderfighter.game.item.ItemType;

public class ItemSpawner {

  private final Random rng = new Random();
  private final double canvasW;
  private final double canvasH;

  private boolean firstSpawnDone = false;

  public ItemSpawner(double canvasW, double canvasH) {
    this.canvasW = canvasW;
    this.canvasH = canvasH;
  }

  public void update(Aircraft player, ClearScreenHandler clearHandler) {

    if (!firstSpawnDone) {
      spawnOne(player, clearHandler);
      firstSpawnDone = true;
      return;
    }

    if (ItemManager.getInstance().hasActiveItem()) return;

    spawnOne(player, clearHandler);
  }

  private void spawnOne(Aircraft player, ClearScreenHandler clearHandler) {
    ItemType type = rollTypeEqual();

    double px = player.getCollisionBounds().getMinX();
    double py = player.getCollisionBounds().getMinY();

    double[] pos = computeSpawnPosition(px, py);
    double x = pos[0];
    double y = pos[1];

    Bullet item = createItemBullet(type, x, y, clearHandler);
    if (item == null) return;

    ItemManager.getInstance().addItem(item);
  }

  private ItemType rollTypeEqual() {
    return switch (rng.nextInt(4)) {
      case 0 -> ItemType.HEAL;
      case 1 -> ItemType.SHIELD;
      case 2 -> ItemType.POWER;
      default -> ItemType.CLEAR;
    };
  }

  private Bullet createItemBullet(ItemType type, double x, double y, ClearScreenHandler clearHandler) {
    return switch (type) {
      case HEAL -> BulletFactory.createHealItem(x, y, canvasW, canvasH);
      case SHIELD -> BulletFactory.createShieldItem(x, y, canvasW, canvasH, 300);
      case POWER -> BulletFactory.createPowerItem(x, y, canvasW, canvasH, 600, 1);
      case CLEAR -> BulletFactory.createClearItem(x, y, canvasW, canvasH, clearHandler, 60);
    };
  }

  private double[] computeSpawnPosition(double playerX, double playerY) {
    double diag = Math.hypot(canvasW, canvasH);
    double minDist = diag / 3.0;

    for (int attempt = 0; attempt < 80; attempt++) {
      double x = rng.nextDouble() * (canvasW - 60);
      double y = rng.nextDouble() * (canvasH - 60);
      if (Math.hypot(x - playerX, y - playerY) >= minDist) return new double[] {x, y};
    }

    return new double[] {canvasW - 80, 80};
  }
}
