// src/main/java/org/thunderfighter/game/aircraft/enemy/EliteEnemy.java

package org.thunderfighter.game.aircraft.enemy;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.bullet.NormalEnemyBullet;

/**
 * EliteEnemy
 * Elite Enemy Plane
 * Characteristics:
 * - Low Health
 * - Moves vertically downwards in a straight line
 * - Periodically fires single, linear bullets
 */
public class EliteEnemy extends AbstractEnemyAircraft {

  /** Elite enemy aircraft dimensions (width 60, height 80) */
  public static final Dimension2D SIZE = new Dimension2D(60, 80); // @params

  /**
   * Constructor
   *
   * @param x The X coordinate at birth (Y coordinate is fixed at the top of the screen)
   */
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

  /**
   * Update logic per frame
   * Currently only calling parent class logic
   */
  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    super.onUpdate(worldEntities);
  }

  /**
   * Movement logic
   * Elite enemy aircraft only move vertically downwards
   */
  @Override
  protected void move() {
    y += speed;
  }

  /**
   * Shooting logic
   * Fire a standard enemy bullet directly downwards
   */
  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    NormalEnemyBullet bullet =
        new NormalEnemyBullet(
            x + size.getWidth() / 2 - 4, y + size.getHeight() + 4, 0, 4, false); // @params
    worldEntities.add(bullet);
  }
}
