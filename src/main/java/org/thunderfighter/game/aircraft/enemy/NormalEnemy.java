package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;

public class NormalEnemy extends AbstractEnemyAircraft {

  public NormalEnemy(double x, double y) {
    this.x = x;
    this.y = y;
    this.hp = 1;
    this.speed = 2;
    this.score = 10;
    this.canShoot = false;
    this.size = new Dimension2D(50, 60);
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot() {
    // Normal enemy doesn't shoot
  }
}
