package org.thunderfighter.game.bullet;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

/**
 * LaserBullet
 *
 * Design:
 * - Sprite default direction is DOWN (+Y).
 * - Laser is a beam (not a moving projectile).
 * - It grows to the canvas boundary within growTicks.
 * - Stays for durationTicks, can be cleared immediately by CLEAR item.
 * - Execute mechanic: takeDamage(Integer.MAX_VALUE).
 */
public class LaserBullet extends AbstractBullet implements Clearable {

  // ------------------------------------------------------------
  // Sprite (default direction: DOWN)
  // ------------------------------------------------------------
  private static final Image LASER_SPRITE =
      new Image(LaserBullet.class.getResourceAsStream("/images/Bullet/laser.png"));

  // ------------------------------------------------------------
  // Parameters
  // ------------------------------------------------------------
  private final double thickness;
  private int remainTicks;

  /** How many ticks for the beam to fully reach the boundary (0.5~0.7s @60TPS -> 30~42). */
  private final int growTicks;

  /** Current age in ticks (used for growing). */
  private int ageTicks = 0;

  /** Normalized direction (unit vector). */
  private final double dirX;
  private final double dirY;

  /** Beam start anchor (muzzle point). */
  private final double startX;
  private final double startY;

  /** Cached full beam length to boundary. */
  private double fullLength = -1; // lazy compute after canvas available

  /**
   * Recommended ctor:
   * - dx/dy defines direction (laser points to that direction)
   * - durationTicks: how long laser exists
   * - thickness: beam width (pixels)
   * - growTicks: how long it takes to grow to boundary (e.g., 36)
   */
  public LaserBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      int durationTicks,
      double thickness,
      int growTicks) {

    this.startX = startX;
    this.startY = startY;

    // Keep AbstractBullet fields consistent
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.thickness = thickness;
    this.size = new Dimension2D(thickness, thickness);

    this.fromPlayer = false;

    this.remainTicks = Math.max(1, durationTicks);
    this.growTicks = Math.max(1, growTicks);

    // Normalize direction
    double len = Math.hypot(dx, dy);
    if (len < 1e-6) {
      this.dirX = 0.0;
      this.dirY = 1.0; // default DOWN
    } else {
      this.dirX = dx / len;
      this.dirY = dy / len;
    }

    // semantic only
    this.dx = this.dirX;
    this.dy = this.dirY;
    this.speed = 0; // beam does not move
    this.lifeTicks = -1; // we use remainTicks
  }

  /** Convenience ctor with default growTicks = 36 (~0.6s @60TPS). */
  public LaserBullet(double startX, double startY, double dx, double dy, int durationTicks, double thickness) {
    this(startX, startY, dx, dy, durationTicks, thickness, 36);
  }

  /** Beam thickness in pixels. */
  public double getThickness() {
    return thickness;
  }

  /** Current beam length (growing). */
  public double getCurrentLength() {
    ensureFullLengthComputed();
    if (fullLength <= 0) return 0;

    double t = Math.min(1.0, ageTicks / (double) growTicks);
    return fullLength * t;
  }

  @Override
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;

    ageTicks++;
    remainTicks--;
    if (remainTicks <= 0) aliveFlag = false;
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(Integer.MAX_VALUE); // execute
    // piercing by default
  }

  @Override
  public void clearImmediately() {
    aliveFlag = false;
  }

  /**
   * Laser collision:
   * Use an axis-aligned bounding box covering the current beam segment (AABB approximation).
   */
  @Override
  public Bounds getCollisionBounds() {
    double len = getCurrentLength();
    double endX = startX + dirX * len;
    double endY = startY + dirY * len;

    double minX = Math.min(startX, endX) - thickness / 2.0;
    double minY = Math.min(startY, endY) - thickness / 2.0;
    double maxX = Math.max(startX, endX) + thickness / 2.0;
    double maxY = Math.max(startY, endY) + thickness / 2.0;

    return new BoundingBox(minX, minY, Math.max(1, maxX - minX), Math.max(1, maxY - minY));
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;

    double len = getCurrentLength();
    if (len <= 1) return;

    // rotation: sprite default DOWN (+Y)
    // angleToDir is angle from +X; DOWN is +90deg => subtract 90deg
    double angleToDir = Math.atan2(dirY, dirX);
    double rotationDegrees = Math.toDegrees(angleToDir - Math.PI / 2.0);

    gc.save();
    gc.translate(startX, startY);
    gc.rotate(rotationDegrees);

    // draw stretched laser downward (y+)
    gc.drawImage(LASER_SPRITE, -thickness / 2.0, 0, thickness, len);

    gc.restore();
  }

  // ------------------------------------------------------------
  // Internal
  // ------------------------------------------------------------
  private void ensureFullLengthComputed() {
    if (fullLength > 0) return;

    // canvas comes from AbstractEntity; must be injected like aircraft
    if (this.canvas == null) {
      // no canvas yet, keep safe
      fullLength = 0;
      return;
    }

    double w = canvas.getWidth();
    double h = canvas.getHeight();
    if (w <= 1 || h <= 1) {
      fullLength = 0;
      return;
    }

    fullLength = computeRayLengthToBounds(startX, startY, dirX, dirY, w, h);
  }

  private static double computeRayLengthToBounds(
      double x, double y, double dx, double dy, double w, double h) {

    double tMin = Double.POSITIVE_INFINITY;

    if (Math.abs(dx) > 1e-9) {
      double tx1 = (0 - x) / dx;
      double tx2 = (w - x) / dx;
      if (tx1 > 0) tMin = Math.min(tMin, tx1);
      if (tx2 > 0) tMin = Math.min(tMin, tx2);
    }

    if (Math.abs(dy) > 1e-9) {
      double ty1 = (0 - y) / dy;
      double ty2 = (h - y) / dy;
      if (ty1 > 0) tMin = Math.min(tMin, ty1);
      if (ty2 > 0) tMin = Math.min(tMin, ty2);
    }

    if (!Double.isFinite(tMin) || tMin <= 0) {
      return Math.hypot(w, h);
    }

    return Math.min(Math.hypot(w, h), tMin);
  }
}
