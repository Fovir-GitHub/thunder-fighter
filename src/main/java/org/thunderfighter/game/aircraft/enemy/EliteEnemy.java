package org.thunderfighter.game.aircraft.enemy;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.manager.BulletManager;
import org.thunderfighter.game.bullet.NormalEnemyBullet;

public class EliteEnemy extends AbstractEnemyAircraft {

  public EliteEnemy(double x) {
    this.x = x;
    this.size = new Dimension2D(60, 80);
    this.y = -size.getHeight();

    this.hp = 3;
    this.speed = 2;
    this.score = 50;
    this.canShoot = true;
    this.shootInterval = 60;
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot() {
    NormalEnemyBullet bullet = new NormalEnemyBullet(x + size.getWidth() / 2 - 4, y + size.getHeight());
    BulletManager.getInstance().addBullet(bullet);
  }
}
