package org.thunderfighter.core.collision;

import java.util.List;
import javafx.geometry.Bounds;
import org.thunderfighter.core.abstractor.AbstractAircraft;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Bullet;

/** Collisoin Detector */
public class CollisionDetector {

  public static void detectCollision(List<AbstractEntity> entities) {
    int n = entities.size();
    for (int i = 0; i < n; i++) {
      AbstractEntity a = entities.get(i);
      if (!a.isAlive()) {
        continue;
      }

      for (int j = i + 1; j < n; j++) {
        AbstractEntity b = entities.get(j);
        if (!b.isAlive()) {
          continue;
        }
        if (isCollision(a, b)) {
          handleCollision(a, b);
        }
      }
    }
  }

  /**
   * Determine whether collision happends between two entites.
   *
   * <p>Specially, collisions won't happen between bullets or between enemies.
   *
   * @param a An entity
   * @param b Another entity
   * @return Return `true` if collision happens. Otherwise, it will return `false`.
   */
  private static boolean isCollision(AbstractEntity a, AbstractEntity b) {
    // Collision won't happen between bullets and enemies.
    if ((a instanceof Bullet && b instanceof Bullet)
        || (a instanceof AbstractEnemyAircraft && b instanceof AbstractEnemyAircraft)) {
      return false;
    }

    // Avoid `null` value raising exceptions.
    Bounds b1 = a.getCollisionBounds();
    Bounds b2 = b.getCollisionBounds();
    if (b1 != null && b2 != null) {
      return b1.intersects(b2);
    }

    return false;
  }

  /**
   * Actions to be taken when collision happens.
   *
   * <p>This method should be used after `isCollision()`.
   *
   * <p>As `isCollision()` is called before this method, situations to be handled include collisions
   * between player and enemy, and between aircraft and bullet.
   *
   * @param a An entity.
   * @param b Another entity.
   */
  private static void handleCollision(AbstractEntity a, AbstractEntity b) {
    if (a instanceof AbstractAircraft && b instanceof AbstractAircraft) {
      ((AbstractAircraft) a).takeDamage(1);
      ((AbstractAircraft) b).takeDamage(1);
    } else {
      AbstractAircraft aircraft;
      AbstractBullet bullet;
      if (a instanceof AbstractAircraft) {
        aircraft = (AbstractAircraft) a;
        bullet = (AbstractBullet) b;
      } else {
        aircraft = (AbstractAircraft) b;
        bullet = (AbstractBullet) a;
      }

      // Prevent the player from suicide.
      if (aircraft.isPlayer() && bullet.isFromPlayer()) {
        return;
      }

      bullet.onHit(aircraft);
    }
  }
}
