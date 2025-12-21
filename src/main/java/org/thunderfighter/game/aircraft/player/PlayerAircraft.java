package org.thunderfighter.game.aircraft.player;

import javafx.geometry.Dimension2D;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;

public class PlayerAircraft extends AbstractPlayerAircraft {

  public PlayerAircraft(double x, double y, int hp, double speed, int shootInterval) {
    this.x = x;
    this.y = y;
    this.hp = hp;
    this.speed = speed;
    this.shootInterval = shootInterval;
    this.size = new Dimension2D(60, 80);
    //ImageLoader.load("player.png");
  }

  @Override
  protected void move() {
    //keyboard input
  }

  @Override
  protected void doShoot() {
    // the actual shooting behavior in BulletManager
  }
}
