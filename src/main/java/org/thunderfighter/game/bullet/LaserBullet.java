package org.thunderfighter.game.bullet;

import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.entity.Aircraft;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * LaserBullet
 *
 * Design:
 * - Sprite default direction is DOWN (+Y).
 * - Laser is a "beam": it does NOT travel as a small projectile.
 * - It grows to the canvas boundary within 0.5~0.7s (configurable).
 * - It stays for durationTicks, and can be cleared immediately by CLEAR item.
 * - Execute mechanic: takeDamage(Integer.MAX_VALUE).
 *
 * Note:
 * - UI warning lines / fan sweep markers belong to UI/World, not here.
 */
public class LaserBullet extends AbstractBullet implements Clearable {

  // ------------------------------------------------------------
  // Sprite (change path if needed)
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

  /** Beam full length to the canvas boundary (computed once). */
  private final double fullLength;

  /** Beam start anchor (muzzle point). */
  private final double startX;
  private final double startY;

  public LaserBullet(
      double startX,
      double startY,
      double dx,
      double dy,
      int durationTicks,
      double thickness,
      double canvasW,
      double canvasH) {

    // Anchor
    this.startX = startX;
    this.startY = startY;

    // Store in AbstractBullet fields too (for compatibility)
    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.canvasW = canvasW;
    this.canvasH = canvasH;

    this.thickness = thickness;
    this.size = new javafx.geometry.Dimension2D(thickness, thickness);

    this.fromPlayer = false;

    // Lifetime
    this.remainTicks = Math.max(1, durationTicks);

    // Growing time: 0.6s @60TPS ~ 36 ticks (in range 0.5~0.7)
    this.growTicks = 36;

    // Normalize direction
    double len = Math.hypot(dx, dy);
    if (len < 1e-6) {
      // fallback: default shoot down
      this.dirX = 0.0;
      this.dirY = 1.0;
    } else {
      this.dirX = dx / len;
      this.dirY = dy / len;
    }

    // Save direction into AbstractBullet velocity fields (semantic only here)
    this.dx = this.dirX;
    this.dy = this.dirY;
    this.speed = 0; // beam does not "move"; it grows

    // Compute full beam length until it hits canvas boundary
    this.fullLength = computeRayLengthToBounds(this.startX, this.startY, this.dirX, this.dirY, canvasW, canvasH);
  }

  /** Beam thickness in pixels. */
  public double getThickness() {
    return thickness;
  }

  /** Remaining ticks. */
  public int getRemainTicks() {
    return remainTicks;
  }

  /** Current beam length (growing). */
  public double getCurrentLength() {
    double t = Math.min(1.0, (growTicks <= 0) ? 1.0 : (ageTicks / (double) growTicks));
    return fullLength * t;
  }

  @Override
  public void update() {
    if (!aliveFlag) return;

    ageTicks++;
    remainTicks--;
    if (remainTicks <= 0) {
      aliveFlag = false;
    }
  }

  @Override
  public void onHit(Aircraft target) {
    // Execute mechanic
    target.takeDamage(Integer.MAX_VALUE);

    // Laser default behavior: piercing (does NOT disappear on hit)
  }

  @Override
  public void clearImmediately() {
    aliveFlag = false;
  }

  /**
   * Important:
   * Default AbstractBullet collision is a small rectangle at (x,y).
   * For laser beam we override it using an axis-aligned bounding box.
   * (Approximation good enough for most projects.)
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

    // ------------------------------------------------------------
    // Rotation:
    // - atan2 gives angle from +X axis
    // - our sprite default is DOWN (+Y), which corresponds to +90 degrees
    // => rotationDegrees = angleToDir - 90deg
    // ------------------------------------------------------------
    double angleToDir = Math.atan2(dirY, dirX);
    double rotationDegrees = Math.toDegrees(angleToDir - Math.PI / 2.0);

    gc.save();

    // Move origin to laser start point, rotate, then draw a stretched sprite downward
    gc.translate(startX, startY);
    gc.rotate(rotationDegrees);

    // Draw: centered thickness, stretched length
    gc.drawImage(LASER_SPRITE, -thickness / 2.0, 0, thickness, len);

    gc.restore();
  }

  // ------------------------------------------------------------
  // Helper: ray length to bounds
  // ------------------------------------------------------------
  private static double computeRayLengthToBounds(
      double x, double y, double dx, double dy, double w, double h) {

    // We compute t for intersections with x=0, x=w, y=0, y=h, choose the smallest positive t.
    double tMin = Double.POSITIVE_INFINITY;

    // Avoid division by 0
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

    // If for some reason we can't find a positive intersection, fallback
    if (!Double.isFinite(tMin) || tMin <= 0) {
      return Math.hypot(w, h);
    }

    // Clamp to reasonable max (diagonal)
    double diag = Math.hypot(w, h);
    return Math.min(diag, tMin);
  }
}
