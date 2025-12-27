package org.thunderfighter.game.aircraft.enemy;

import java.util.List;

import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.manager.ScoreManager;
import org.thunderfighter.game.bullet.NormalEnemyBullet;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;

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
    this.shootInterval = 60;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/EliteEnemy.png"));
  }

  @Override
  protected void onUpdate() {
    super.onUpdate();
    if(hp == 0) ScoreManager.getInstance().addScore(50);
    aliveFlag = false;
  }

  @Override
  protected void move() {
    y += speed;
  }

  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    NormalEnemyBullet bullet =
        new NormalEnemyBullet(x + size.getWidth() / 2 - 4, y + size.getHeight()); // @params
    worldEntities.add(bullet);
  }
}
