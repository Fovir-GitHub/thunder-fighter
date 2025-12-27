package org.thunderfighter.game.bullet;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.StraightTrajectory;

/**
 * NormalEnemyBullet
 *
 * <p>Straight enemy bullet.
 *
 * <p>Features: - Damage: 1 - Dies when out of canvas bounds - Supports small / large size
 *
 * <p>Compatibility: - Provides a 2-parameter constructor to match existing Aircraft.doShoot(x, y) -
 * Also provides full constructor for advanced usage (boss / elite patterns)
 */
public class NormalEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Default downward speed (per tick) for legacy constructor. */
  private static final double DEFAULT_DY = 8.0;

  /** Default canvas size fallback (only used by legacy constructor). */
  private static final double DEFAULT_CANVAS_W = 800.0;

  private static final double DEFAULT_CANVAS_H = 600.0;

  /** Enemy bullet sprite. */
  private static final Image SPRITE =
      new Image(NormalEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  /**
   * Legacy constructor.
   *
   * <p>Used by existing code such as: new NormalEnemyBullet(x, y);
   *
   * <p>Defaults: - Straight downward movement - Small bullet - Default canvas size (800x600)
   */
  public NormalEnemyBullet(double startX, double startY) {
    this(
        startX,
        startY,
        0.0, // dx
        DEFAULT_DY, // dy
        false, // large
        DEFAULT_CANVAS_W,
        DEFAULT_CANVAS_H);
  }

  /**
   * Full constructor.
   *
   * @param startX start x position
   * @param startY start y position
   * @param dx per-tick x velocity
   * @param dy per-tick y velocity
   * @param large whether this is a large bullet (boss / elite)
   * @param canvasW canvas width
   * @param canvasH canvas height
   */
  public NormalEnemyBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      boolean large,
      double canvasW,
      double canvasH) {

    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    // Size based on bullet type
    this.size = large ? new Dimension2D(24, 48) : new Dimension2D(12, 24);

    // Velocity per tick
    this.dx = dx;
    this.dy = dy;

    // Semantic speed value
    this.speed = Math.hypot(dx, dy);

    this.fromPlayer = false;

    // Straight movement
    this.trajectory = new StraightTrajectory();

    // Infinite life; removed when out of bounds
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
    gc.drawImage(SPRITE, x, y, size.getWidth(), size.getHeight());
  }
}
