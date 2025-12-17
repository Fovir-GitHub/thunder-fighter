package org.thunderfighter.core.abstractor;

import javafx.geometry.Bounds;
import org.thunderfighter.core.entity.Aircraft;

public abstract class AbstractAircraft extends AbstractEntity implements Aircraft {
  protected int hp;
  protected Bounds collisionBounds;

  @Override
  public int getHp() {
    return hp;
  }

  @Override
  public void takeDamage(int damage) {
    hp -= damage;
    if (hp <= 0) {
      alive_flag = false;
      onDie();
    }
  }

  @Override
  public Bounds getCollisionBounds() {
    return collisionBounds;
  }

  @Override
  public final void update() {
    if (!alive_flag) return;
    move();
    onUpdate();
  }

  protected void onDie() {}
  ;

  protected void move() {}
  ;

  protected void onUpdate() {}
  ;
}
