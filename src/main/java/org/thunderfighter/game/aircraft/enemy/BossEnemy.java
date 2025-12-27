package org.thunderfighter.game.aircraft.enemy;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.bulletfactory.BulletFactory;

public class BossEnemy extends AbstractEnemyAircraft {

  public static final Dimension2D SIZE = new Dimension2D(200, 150); // @params

  private boolean movingRight = true;
  private static final double SCREEN_WIDTH = 800; // @params

  private enum Stage {
    stage1,
    stage2,
    stage3
  }

  private Stage stage = Stage.stage1;

  public BossEnemy(double x, double y) {
    this.x = x;
    this.y = y; // birth coordinates

    this.hp = 300;
    this.speed = 1;
    this.score = 3000; // kill reward

    this.canShoot = true;
    this.shootInterval = 60; // the number of frames are needed between two shots
    this.shootCooldown = 0; // the number of frames are left before the next shot can be fired

    this.size = SIZE;

    this.sprite =
        new Image(
            getClass().getResourceAsStream("/images/Aircraft/BossEnemy.png"));
  }

  @Override
  protected void move() {
    double LEFT_BOUND = SCREEN_WIDTH * 0.25;
    double RIGHT_BOUND = SCREEN_WIDTH * 0.75 - size.getWidth();

    if (movingRight) {
      x += speed;
      if (x >= RIGHT_BOUND) movingRight = false;
    } else {
      x -= speed;
      if (x <= LEFT_BOUND) movingRight = true;
    }
  }

  @Override
  protected void onUpdate() {
    super.onUpdate();

    if (hp > 200) {
      stage = Stage.stage1;
      shootInterval = 60;
    } else if (hp > 100) {
      stage = Stage.stage2;
      shootInterval = 40;
    } else if (hp > 0){
      stage = Stage.stage3;
      shootInterval = 25;
      speed = 1.8;
    }
  }

  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    switch (stage) {
      case stage1:
        shootStage1(worldEntities);
        break;
      case stage2:
        shootStage2(worldEntities);
        break;
      case stage3:
        shootStage3(worldEntities);
        break;
    }
  }

  private void shootStage1(List<AbstractEntity> worldEntities) {
    double cx = x + size.getWidth() / 2; // @params
    double by = y + size.getHeight();

    for (int i = -1; i <= 1; i++) {
      worldEntities.add(
          BulletFactory.createEnemyBullet(cx + i * 25, by, i * 0.6, 3.5, false, SCREEN_WIDTH, 600));
    }
  }

  private void shootStage2(List<AbstractEntity> worldEntities) {
    double cx = x + size.getWidth() / 2;
    double by = y + size.getHeight();

    for (int i = -3; i <= 3; i++) {
      worldEntities.add(
          BulletFactory.createEnemyBullet(cx, by, i * 0.8, 4, i == 0, SCREEN_WIDTH, 600));
    }
  }

  private void shootStage3(List<AbstractEntity> worldEntities) {
    double cx = x + size.getWidth() / 2;
    double by = y + size.getHeight();

    worldEntities.add(
        BulletFactory.createCurvedEnemyBullet(cx - 60, by, -1.5, 3.5, 0.06, SCREEN_WIDTH, 600));
    worldEntities.add(
        BulletFactory.createCurvedEnemyBullet(cx - 60, by, -1.5, 3.5, -0.06, SCREEN_WIDTH, 600));
    worldEntities.add(BulletFactory.createEnemyBullet(cx, by, 0, 5, true, SCREEN_WIDTH, 600));
  }
}
