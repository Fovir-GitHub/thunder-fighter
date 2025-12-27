package org.thunderfighter.game.bullet;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.StraightTrajectory;

/**
 * NormalEnemyBullet
 *
 * <p>Straight enemy bullet. - Damage: 1 - Dies when out of canvas bounds (uses injected canvas) -
 * Supports small / large size
 *
 * <p>Compatibility: - Provides a 2-parameter constructor to match existing Aircraft.doShoot(x, y) -
 * Also provides full constructor for advanced usage (boss / elite patterns)
 */
public class NormalEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Default downward speed (per tick) for legacy constructor. */
  private static final double DEFAULT_DY = 8.0;

  /** Enemy bullet sprite (red dot). */
  private static final Image SPRITE =
      new Image(NormalEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  /** Legacy constructor: straight downward, small bullet. */
  public NormalEnemyBullet(double startX, double startY) {
    this(startX, startY, 0.0, DEFAULT_DY, false);
  }

  /**
   * Full constructor (recommended when spawning patterns).
   *
   * <p>NOTE: - canvasW/canvasH removed - Out-of-bounds uses injected canvas from AbstractEntity
   */
  public NormalEnemyBullet(double startX, double startY, double dx, double dy, boolean large) {

    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    // Size: round dot style
    this.size = large ? new Dimension2D(28, 28) : new Dimension2D(16, 16);

    // Velocity per tick
    this.dx = dx;
    this.dy = dy;

    // Semantic speed
    this.speed = Math.hypot(dx, dy);

    this.fromPlayer = false;
    this.trajectory = new StraightTrajectory();
    this.lifeTicks = -1;
  }

  @Override
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;
    moveOnce();
    tickLife();
    killIfOutOfBounds(); // now depends on injected canvas size
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(SPRITE, x, y, size.getWidth(), size.getHeight());
  }
}
