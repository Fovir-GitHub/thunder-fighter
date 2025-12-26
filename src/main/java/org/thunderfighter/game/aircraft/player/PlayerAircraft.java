package org.thunderfighter.game.aircraft.player;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.game.bullet.PlayerBullet;

public class PlayerAircraft extends AbstractPlayerAircraft {

  public static final Dimension2D SIZE = new Dimension2D(60, 80); // @params

  public PlayerAircraft(
      double x, double y, int hp, double speed, int shootInterval, Canvas canvas) {
    this.x = x;
    this.y = y;
    this.hp = hp;
    this.speed = speed;
    this.shootInterval = shootInterval;
    this.size = SIZE;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/PlayerAircraft.png"));
    this.canvas = canvas;
  }

  @Override
  protected void move() {
    double dx = 0;
    double dy = 0;
    double tempX = this.x;
    double tempY = this.y;

    if (up) {
      dy -= 1;
    }
    if (down) {
      dy += 1;
    }
    if (left) {
      dx -= 1;
    }
    if (right) {
      dx += 1;
    }

    if (dx != 0 || dy != 0) {
      double length = Math.sqrt(dx * dx + dy * dy);
      dx /= length;
      dy /= length;
    }

    tempX += dx * speed;
    tempY += dy * speed;

    if (tempX <= 0
        || tempX + this.size.getWidth() >= canvas.getWidth()
        || tempY <= 0
        || tempY + this.size.getHeight() >= canvas.getHeight()) {
      return;
    }

    this.x = tempX;
    this.y = tempY;
  } // to prevent the aircraft from traveling faster at an angle than straight.

  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    PlayerBullet bullet = new PlayerBullet(x + size.getWidth() / 2 - 4, y - 10); // @params
    worldEntities.add(bullet); // by manager instance to manage it.
  }
}
