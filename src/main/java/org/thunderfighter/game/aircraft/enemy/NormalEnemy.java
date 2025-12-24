package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;

public class NormalEnemy extends AbstractEnemyAircraft {

  public static final Dimension2D SIZE = new Dimension2D(50, 60); // @params

  public NormalEnemy(double x) {
    this.size = SIZE;

    this.x = x;
    this.y = -size.getHeight();

    this.hp = 1;
    this.speed = 2;
    this.score = 10;
    this.canShoot = false;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/NormalEnemy.png"));
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot() {
    // Normal enemy does not shoot
  }
}
