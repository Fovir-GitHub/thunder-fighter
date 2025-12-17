package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;

public interface Aircraft extends Entity {
  int getHp();

  void takeDamage(int damage);

  boolean isPlayer();

  void shoot(); // new bullet here

  Bounds getCollisionBounds();
}
