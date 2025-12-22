package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;

public class EliteEnemy extends AbstractEnemyAircraft {

  public EliteEnemy(double x) {
    this.x = x;
    this.y = y;
    this.hp = 3;
    this.speed = 2;
    this.score = 50;
    this.canShoot = true;
    this.shootInterval = 60;
    this.size = new Dimension2D(60, 80);
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot() {
    // elite enemy special shoot
  }
}
