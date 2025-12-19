package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;

public interface Aircraft extends Entity {
  int getHp(); // current health

  void takeDamage(int damage);

  boolean isPlayer(); // is player aircraft or not

  void shoot(); // fire bullets, but only declares the "behavior", does not create the bullets

  Bounds getCollisionBounds(); // obtain the boundary required for collision detection
}
