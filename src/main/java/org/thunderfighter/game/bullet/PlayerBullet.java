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
 * Straight bullet fired by the player.
 * - Damage: 1
 * - Dies when out of canvas bounds
 *
 * Compatibility:
 * - Provides (startX, startY) constructor to match existing Aircraft.doShoot(...)
 * - Provides full (startX, startY, canvasW, canvasH) constructor for correct canvas bounds
 */
public class PlayerBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Default canvas size fallback for legacy constructor. */
  private static final double DEFAULT_CANVAS_W = 800.0;
  private static final double DEFAULT_CANVAS_H = 600.0;

  /** Default per-tick speed upward. */
  private static final double DEFAULT_DY = -12.0;

  /** Player bullet sprite. Make sure this exists in resources. */
  private static final Image SPRITE =
      new Image(PlayerBullet.class.getResourceAsStream("/images/bullets/player_bullet.png"));

  /**
   * Legacy constructor (compatibility).
   * Matches your current code: new PlayerBullet(x, y)
   */
  public PlayerBullet(double startX, double startY) {
    this(startX, startY, DEFAULT_CANVAS_W, DEFAULT_CANVAS_H);
  }

  /**
   * Full constructor.
   *
   * @param startX start x
   * @param startY start y
   * @param canvasW canvas width
   * @param canvasH canvas height
   */
  public PlayerBullet(double startX, double startY, double canvasW, double canvasH) {
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    // Keep your original bullet size (if your sprite is round, you can make it 12x12)
    this.size = new Dimension2D(6, 12);

    // Straight upward
    this.dx = 0.0;
    this.dy = DEFAULT_DY;

    // Semantic speed
    this.speed = Math.abs(DEFAULT_DY);

    this.fromPlayer = true;
    this.trajectory = new StraightTrajectory();

    // Infinite life; out-of-bounds will remove it
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
