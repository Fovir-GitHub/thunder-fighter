package org.thunderfighter.core.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.thunderfighter.core.entity.Bullet;

import javafx.scene.canvas.GraphicsContext;

/**
 * BulletManager (Singleton)
 *
 * <p>Owns all bullets in the game and updates/removes them each tick. Rendering can also be handled
 * here to keep bullet logic centralized.
 */
public class BulletManager {

  private static final BulletManager instance = new BulletManager();

  private final List<Bullet> bullets = new ArrayList<>();

  private BulletManager() {}

  public static BulletManager getInstance() {
    return instance;
  }

  /** Adds a bullet into manager list. */
  public void addBullet(Bullet bullet) {
    if (bullet != null) {
      bullets.add(bullet);
    }
  }

  /** Returns the internal bullet list (read-only usage recommended). */
  public List<Bullet> getBullets() {
    return bullets;
  }

  /** Updates all bullets once per tick and removes dead bullets. */
  public void update() {
    Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      b.update();
      if (!b.isAlive()) {
        it.remove();
      }
    }
  }

  /** Renders all bullets on the given GraphicsContext. */
  public void render(GraphicsContext gc) {
    for (Bullet b : bullets) {
      b.draw(gc);
    }
  }

  /** Clears all enemy bullets (keeps player bullets). */
  public void clearEnemyBullets() {
    bullets.removeIf(b -> !b.isFromPlayer());
  }

  /** Clears all bullets. */
  public void clearAll() {
    bullets.clear();
  }
}