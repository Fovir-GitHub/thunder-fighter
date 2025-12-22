package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;

public class BossEnemy extends AbstractEnemyAircraft {

  public BossEnemy(double x) {
    this.x = x;
    this.y = y;
    this.hp = 300;
    this.speed = 1;
    this.score = 500;
    this.canShoot = true;
    this.shootInterval = 40;
    this.size = new Dimension2D(200, 150);
  }

  @Override
  protected void move() {
    // boss special move
  }

  @Override
  protected void doShoot() {
    // boss enemy special shoot
  }
}
