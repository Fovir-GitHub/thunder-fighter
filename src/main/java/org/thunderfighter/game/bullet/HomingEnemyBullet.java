// src/main/java/org/thunderfighter/game/bullet/HomingEnemyBullet.java

package org.thunderfighter.game.bullet;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.HomingTrajectory;

/**
 * HomingEnemyBullet
 *
 * <p>Enemy bullet that tracks the player for a limited duration.
 *
 * <p>The bullet gradually adjusts its direction using a homing trajectory, making it slower and
 * more predictable than instant-lock bullets. This design provides pressure without being unfair to
 * the player.
 */
public class HomingEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  private static final Image SPRITE =
      new Image(HomingEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  private int trackingTicks;

  /**
   * Constructs a homing enemy bullet.
   *
   * @param startX initial x position
   * @param startY initial y position
   * @param initDx initial horizontal velocity
   * @param initDy initial vertical velocity
   * @param trackingTicks number of ticks the bullet will track the target
   * @param provider supplies real-time target position
   */
  public HomingEnemyBullet(
      double startX,
      double startY,
      double initDx,
      double initDy,
      int trackingTicks,
      HomingTrajectory.TargetProvider provider) {

    // Initial position
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    // Bullet dimensions
    this.size = new Dimension2D(12, 24);

    // Initial velocity and speed
    this.dx = initDx;
    this.dy = initDy;
    this.speed = Math.hypot(initDx, initDy);

    // Enemy bullet flag
    this.fromPlayer = false;

    // Assign homing trajectory
    this.trajectory = new HomingTrajectory(provider, 0.10);

    // Ensure tracking duration is at least one tick
    this.trackingTicks = Math.max(1, trackingTicks);

    // Infinite lifetime unless expired or destroyed
    this.lifeTicks = -1;
  }

  /**
   * Updates bullet state for one game tick.
   *
   * <p>Decreases remaining tracking time and removes the bullet once the tracking duration expires.
   */
  @Override
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;

    trackingTicks--;
    if (trackingTicks <= 0) {
      aliveFlag = false;
      return;
    }

    moveOnce();
    tickLife();
    killIfOutOfBounds();
  }

  /**
   * Called when this bullet collides with an aircraft.
   *
   * <p>Applies damage and destroys the bullet.
   */
  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  /** Renders the bullet sprite to the canvas. */
  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(SPRITE, x, y, size.getWidth(), size.getHeight());
  }
}
