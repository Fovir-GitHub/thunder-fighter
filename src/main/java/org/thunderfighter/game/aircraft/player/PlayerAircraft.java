package org.thunderfighter.game.aircraft.player;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.core.manager.BulletManager;
import org.thunderfighter.game.bullet.PlayerBullet;

public class PlayerAircraft extends AbstractPlayerAircraft {

  public static final Dimension2D SIZE = new Dimension2D(60, 80);

  public PlayerAircraft(double x, double y, int hp, double speed, int shootInterval) {
      this.x = x;
      this.y = y;
      this.hp = hp;
      this.speed = speed;
      this.shootInterval = shootInterval;

      this.size = SIZE;

      this.image = new Image(getClass().getResourceAsStream("/images/Aircraft/PlayerAircraft.png"));
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
