package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;

public class BossEnemy extends AbstractEnemyAircraft {

  public static final Dimension2D SIZE = new Dimension2D(200, 150); // @params

  public BossEnemy(double x, double y) {
    this.x = x;
    this.y = y;
    this.hp = 300;
    this.speed = 1;
    this.score = 500;
    this.canShoot = true;
    this.shootInterval = 40;

    this.size = SIZE;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/BossEnemy.png"));

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
