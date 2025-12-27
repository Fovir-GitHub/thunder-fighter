package org.thunderfighter.game.spawn;

import java.util.Random;

import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.manager.ItemManager;
import org.thunderfighter.game.bulletfactory.BulletFactory;
import org.thunderfighter.game.item.ClearScreenHandler;
import org.thunderfighter.game.item.ItemType;

import javafx.scene.canvas.Canvas;

public class ItemSpawner {

  private final Random rng = new Random();
  private final Canvas canvas;

  private boolean firstSpawnDone = false;

  public ItemSpawner(Canvas canvas) {
    this.canvas = canvas;
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
    if (canvas == null || player == null) return;

    ItemType type = rollTypeEqual();

    double px = player.getCollisionBounds().getMinX();
    double py = player.getCollisionBounds().getMinY();

    double[] pos = computeSpawnPosition(px, py);
    if (pos == null) return;

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

  private Bullet createItemBullet(
      ItemType type, double x, double y, ClearScreenHandler clearHandler) {

    if (canvas == null) return null;

    return switch (type) {
      case HEAL -> BulletFactory.createHealItem(canvas, x, y);
      case SHIELD -> BulletFactory.createShieldItem(canvas, x, y, 300);
      case POWER -> BulletFactory.createPowerItem(canvas, x, y, 600, 1);
      case CLEAR -> BulletFactory.createClearItem(canvas, x, y, clearHandler, 60);
    };
  }

  private double[] computeSpawnPosition(double playerX, double playerY) {
    if (canvas == null) return null;

    double canvasW = canvas.getWidth();
    double canvasH = canvas.getHeight();

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
