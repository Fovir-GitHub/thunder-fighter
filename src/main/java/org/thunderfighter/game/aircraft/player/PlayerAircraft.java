package org.thunderfighter.game.aircraft.player;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.core.manager.BulletManager;
import org.thunderfighter.game.bullet.PlayerBullet;

public class PlayerAircraft extends AbstractPlayerAircraft {

  public PlayerAircraft(double x, double y, int hp, double speed, int shootInterval) {
    this.x = x;
    this.y = y;
    this.hp = hp;
    this.speed = speed;
    this.shootInterval = shootInterval;
    this.size = new Dimension2D(60, 80);
  }

  @Override
  protected void move() {
    // TODO: keyboard input handled by controller
  }

  @Override
  protected void doShoot() {
    PlayerBullet bullet = new PlayerBullet(x + size.getWidth() / 2 - 4, y - 10);
    BulletManager.getInstance().addBullet(bullet);
  }
}
