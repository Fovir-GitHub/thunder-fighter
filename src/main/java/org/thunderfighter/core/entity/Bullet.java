package org.thunderfighter.core.entity;

public interface Bullet extends Entity {

  void onHit(Aircraft target); // Called when this bullet collides with an aircraft.

  boolean isFromPlayer(); // Indicates whether this bullet is fired by the player.
}
