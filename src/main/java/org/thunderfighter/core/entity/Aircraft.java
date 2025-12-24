package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;

import java.util.List;

public interface Aircraft extends Entity {
  int getHp(); // current health

  void takeDamage(int damage);

  boolean isPlayer(); // is player aircraft or not

  void shoot(List<Entity> worldEntities);
  // fire bullets, but only declares the "behavior", does not create the bullets
  // all bullet are managed by worldEntity list

  Bounds getCollisionBounds(); // obtain the boundary required for collision detection
}
