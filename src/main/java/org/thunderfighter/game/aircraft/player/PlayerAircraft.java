package org.thunderfighter.game.aircraft.player;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.core.entity.Entity;
import org.thunderfighter.core.manager.BulletManager;
import org.thunderfighter.game.bullet.PlayerBullet;

import java.util.List;

public class PlayerAircraft extends AbstractPlayerAircraft {

  public static final Dimension2D SIZE = new Dimension2D(60, 80); // @params

  public PlayerAircraft(double x, double y, int hp, double speed, int shootInterval) {
    this.x = x;
    this.y = y;
    this.hp = hp;
    this.speed = speed;
    this.shootInterval = shootInterval;
    this.size = SIZE;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/PlayerAircraft.png"));
  }

  @Override
  protected void move() {
    double dx = 0;
    double dy = 0;

    if (up) dy -= 1;
    if (down) dy += 1;
    if (left) dx -= 1;
    if (right) dx += 1;

    if (dx != 0 || dy != 0) {
      double length = Math.sqrt(dx * dx + dy * dy);
      dx /= length;
      dy /= length;
    }

    x += dx * speed;
    y += dy * speed;
  } // to prevent the aircraft from traveling faster at an angle than straight.

  @Override
  protected void doShoot(List<Entity> worldEntities) {
    PlayerBullet bullet = new PlayerBullet(x + size.getWidth() / 2 - 4, y - 10); // @params
    worldEntities.add(bullet); // by manager instance to manage it.
  }
}
