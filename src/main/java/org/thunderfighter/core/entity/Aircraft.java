package org.thunderfighter.core.entity;

import java.util.List;
import org.thunderfighter.core.abstractor.AbstractEntity;

public interface Aircraft extends Entity {
  int getHp(); // current health

  void takeDamage(int damage);

  boolean isPlayer(); // is player aircraft or not

  void shoot(List<AbstractEntity> worldEntities);

  // fire bullets, but only declares the "behavior", does not create the bullets
  // all bullet are managed by worldEntity list
}
