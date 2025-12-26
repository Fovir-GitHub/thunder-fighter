package org.thunderfighter.core.collision;

import java.util.List;
import javafx.geometry.Bounds;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;

// batch collision detection mode
public class CollisionDetector { // for every frame detection, the passed values should be a List
  public static void detectBulletAircraftCollisions(
      List<Bullet> bullets, List<Aircraft> aircrafts) {
    for (Bullet bullet : bullets) {
      if (!bullet.isAlive()) continue; // bullet condition -> die, then continue

      Bounds bulletBounds = bullet.getCollisionBounds(); // get the range of collision judge

      for (Aircraft aircraft : aircrafts) {
        if (!aircraft.isAlive()) continue; // aircraft condition -> die, then continue
        if ((bullet.isFromPlayer() && !aircraft.isPlayer())
            || (!bullet.isFromPlayer() && aircraft.isPlayer())) {
          Bounds aircraftBounds = aircraft.getCollisionBounds();

          if (bulletBounds.intersects(aircraftBounds)) bullet.onHit(aircraft);
        }
      }
    }
  } // bullet <-> aircraft

  public static void detectAircraftCollisions(List<Aircraft> aircrafts) {
    for (int i = 0; i < aircrafts.size(); i++) {
      Aircraft a1 = aircrafts.get(i);
      if (!a1.isAlive()) continue; // detect alive or not

      Bounds b1 = a1.getCollisionBounds();
      for (int j = i + 1; j < aircrafts.size(); j++) {
        Aircraft a2 = aircrafts.get(j);
        if (!a2.isAlive()) continue; // detect alive or not

        Bounds b2 = a2.getCollisionBounds();
        if (b1.intersects(b2)) {
          a1.takeDamage(1);
          a2.takeDamage(1);
        } // enemy and player all deduct 1 hp, although it's a stupid action lol, because player
          // only have 3 hp.
      }
    }
  } // aircraft <-> aircraft
}
