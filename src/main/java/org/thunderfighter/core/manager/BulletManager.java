package org.thunderfighter.core.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.entity.ItemLike;

/**
 * BulletManager (Singleton)
 *
 * Owns all bullets in the game and updates/removes them each tick.
 *
 * IMPORTANT:
 * - Items are also bullets (they implement ItemLike).
 * - When an item disappears (expired or picked up), we notify a callback so ItemSpawner can restart timer.
 */
public class BulletManager {

  private static final BulletManager instance = new BulletManager();

  private final List<Bullet> bullets = new ArrayList<>();

  /** Callback invoked when an item-bullet is removed. */
  private Runnable onItemRemoved;

  private BulletManager() {}

  public static BulletManager getInstance() {
    return instance;
  }

  public void addBullet(Bullet bullet) {
    if (bullet != null) bullets.add(bullet);
  }

  public List<Bullet> getBullets() {
    return bullets;
  }

  /** Spawner should pass: spawner::notifyItemRemoved */
  public void setOnItemRemoved(Runnable cb) {
    this.onItemRemoved = cb;
  }

  public void update() {
    boolean removedAnyItemThisTick = false;

    Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      b.update();

      if (!b.isAlive()) {
        if (b instanceof ItemLike) removedAnyItemThisTick = true;
        it.remove();
      }
    }

    if (removedAnyItemThisTick && onItemRemoved != null) {
      onItemRemoved.run();
    }
  }

  public void render(GraphicsContext gc) {
    for (Bullet b : bullets) {
      b.draw(gc);
    }
  }

  /**
   * Clears enemy bullets BUT keeps items.
   * Otherwise items (fromPlayer=false) will be removed wrongly.
   */
  public void clearEnemyBullets() {
    Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      if (!b.isFromPlayer() && !(b instanceof ItemLike)) {
        it.remove();
      }
    }
  }

  public void clearAll() {
    boolean removedAnyItem = false;

    for (Bullet b : bullets) {
      if (b instanceof ItemLike) { removedAnyItem = true; break; }
    }

    bullets.clear();

    if (removedAnyItem && onItemRemoved != null) {
      onItemRemoved.run();
    }
  }
}
