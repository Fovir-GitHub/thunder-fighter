package org.thunderfighter.game.aircraft.enemy;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEnemyAircraft;
import org.thunderfighter.core.abstractor.AbstractEntity;

/**
 * Normal Enemy
 * Characteristics:
 * - Lowest Health
 * - Only moves vertically downwards
 * - Cannot fire
 */
public class NormalEnemy extends AbstractEnemyAircraft {

  /** Typical enemy aircraft dimensions (width 50, height 60) */
  public static final Dimension2D SIZE = new Dimension2D(50, 60); // @params

  /**
   * Constructor
   *
   * @param x The X coordinate at birth (Y coordinate is fixed at the top of the screen)
   */
  public NormalEnemy(double x) {
    this.size = SIZE;

    this.x = x;
    this.y = -size.getHeight();

    this.hp = 1;
    this.speed = 2;
    this.score = 10;
    this.canShoot = false;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/NormalEnemy.png"));
  }

  /**
   * Update logic per frame
   * Currently only performing basic updates for the parent class
   */
  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    super.onUpdate(worldEntities);
  }

  /**
   * Movement logic
   * Normal enemy aircraft always move vertically downwards
   */
  @Override
  protected void move() {
    y += speed;
  }

  /**
   * Firing logic
   * Ordinary enemy aircraft do not have firing capabilities
   */
  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    // Normal enemy does not shoot
  }
}
