// src/main/java/org/thunderfighter/game/bullet/BulletType.java

package org.thunderfighter.game.bullet;

/**
 * BulletType
 *
 * <p>Useful for boss/elite pattern selection and readable configs. This enum does not create
 * bullets; BulletFactory does.
 */
public enum BulletType {
  PLAYER_NORMAL,
  ENEMY_NORMAL,
  ENEMY_CURVE,
  ENEMY_HOMING,
  ENEMY_LASER
}
