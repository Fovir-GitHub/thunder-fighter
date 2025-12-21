package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;

public interface Bullet extends Entity {

  Bounds getCollisionBounds(); // Returns the collision bounds used by the collision system.

  void onHit(Aircraft target); // Called when this bullet collides with an aircraft.

  boolean isFromPlayer(); // Indicates whether this bullet is fired by the player.
}
