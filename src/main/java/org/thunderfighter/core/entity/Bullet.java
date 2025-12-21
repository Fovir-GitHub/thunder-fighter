package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;

/**
 * Bullet interface
 *
 * Represents all flying objects that can collide with aircraft:
 * - Normal bullets
 * - Curved bullets
 * - Homing bullets
 * - Laser bullets
 * - Item bullets (items are also bullets)
 *
 * This interface defines WHAT a bullet is,
 * not HOW it moves or HOW it is created.
 */
public interface Bullet extends Entity {

  /**
   * Returns the collision bounds used by the collision system.
   */
  Bounds getCollisionBounds();

  /**
   * Called when this bullet collides with an aircraft.
   * - Damage bullets reduce HP
   * - Item bullets trigger effects
   */
  void onHit(Aircraft target);

  /**
   * Indicates whether this bullet is fired by the player.
   * Used for rules such as:
   * - Clear screen (keep player bullets)
   * - Friendly fire filtering
   */
  boolean isFromPlayer();
}
