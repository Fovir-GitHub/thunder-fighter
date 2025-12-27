package org.thunderfighter.game.bullet;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.StraightTrajectory;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * PlayerBullet
 *
 * <p>Straight bullet fired by the player.
 * - Damage: 1
 * - Dies when out of canvas bounds
 *
 * <p>Canvas bounds are handled via AbstractEntity.setCanvas(...)
 */
public class PlayerBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Default per-tick speed upward. */
  private static final double DEFAULT_DY = -12.0;

  /** Player bullet sprite. */
  private static final Image SPRITE =
      new Image(PlayerBullet.class.getResourceAsStream(
          "/images/Bullet/player_bullet.png"));

  /**
   * Constructor.
   * Compatible with existing Aircraft.doShoot(x, y)
   */
  public PlayerBullet(double startX, double startY) {
    this.x = startX;
    this.y = startY;

    this.originX = startX;
    this.originY = startY;

    // Bullet size (adjust to sprite if needed)
    this.size = new Dimension2D(12, 24);

    // Straight upward velocity
    this.dx = 0.0;
    this.dy = DEFAULT_DY;

    // Semantic speed (optional, if AbstractEntity uses it)
    this.speed = Math.abs(DEFAULT_DY);

    this.fromPlayer = true;
    this.trajectory = new StraightTrajectory();

    // Infinite life; removed by out-of-bounds
    this.lifeTicks = -1;
  }

  @Override
  public void update() {
    if (!aliveFlag) return;

    moveOnce();
    tickLife();
    killIfOutOfBounds();
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;

    gc.drawImage(
        SPRITE,
        x,
        y,
        size.getWidth(),
        size.getHeight()
    );
  }
}
