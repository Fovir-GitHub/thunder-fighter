package org.thunderfighter.game.aircraft.enemy;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.bullet.NormalEnemyBullet;

public class EliteEnemy extends AbstractEnemyAircraft {

  public static final Dimension2D SIZE = new Dimension2D(60, 80); // @params

  public EliteEnemy(double x) {
    this.size = SIZE;

    this.x = x;
    this.y = -size.getHeight();

    this.hp = 3;
    this.speed = 2;
    this.score = 50;
    this.canShoot = true;
    this.shootInterval = 120;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/EliteEnemy.png"));
  }

  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    super.onUpdate(worldEntities);
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    NormalEnemyBullet bullet =
        new NormalEnemyBullet(
            x + size.getWidth() / 2 - 4, y + size.getHeight() + 4, 0, 4, false); // @params
    worldEntities.add(bullet);
  }
}
