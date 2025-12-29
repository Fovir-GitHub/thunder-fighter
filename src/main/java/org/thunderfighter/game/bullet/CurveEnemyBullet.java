// src/main/java/org/thunderfighter/game/bullet/CurveEnemyBullet.java

package org.thunderfighter.game.bullet;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.CurveTrajectory;

/**
 * CurveEnemyBullet
 *
 * <p>Enemy bullet that follows a curved trajectory.
 *
 * <p>This type of bullet is typically used for area control, forcing the player to reposition
 * rather than dodge straight lines. The curvature behavior is determined by the curveFactor.
 */
public class CurveEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  private static final Image SPRITE =
      new Image(CurveEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  /**
   * Constructs a curved enemy bullet with an initial velocity and curvature.
   *
   * @param startX starting x position
   * @param startY starting y position
   * @param dx initial horizontal velocity
   * @param dy initial vertical velocity
   * @param curveFactor curvature strength for the trajectory
   */
  public CurveEnemyBullet(double startX, double startY, double dx, double dy, double curveFactor) {

    // Initial position
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    // Bullet dimensions
    this.size = new Dimension2D(6, 12);

    // Initial velocity and speed
    this.dx = dx;
    this.dy = dy;
    this.speed = Math.hypot(dx, dy);

    // Enemy bullet flag
    this.fromPlayer = false;

    // Assign curved trajectory
    this.trajectory = new CurveTrajectory(curveFactor);

    // Infinite lifetime unless destroyed or out of bounds
    this.lifeTicks = -1;
  }

  /**
   * Updates bullet state for one game tick.
   *
   * <p>Handles movement, lifetime ticking, and boundary checks.
   */
  @Override
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;

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
