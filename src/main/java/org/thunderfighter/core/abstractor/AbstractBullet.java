package org.thunderfighter.core.abstractor;

import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.entity.Trajectory;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * AbstractBullet
 *
 * Base class for all bullets (including items if you treat them as bullets).
 * Provides:
 * - Position / velocity (x, y, dx, dy)
 * - Trajectory hook (moveOnce)
 * - Lifetime countdown (lifeTicks)
 * - Out-of-bounds kill logic (requires canvasW/canvasH to be set!)
 * - Collision bounds
 */
public abstract class AbstractBullet extends AbstractEntity implements Bullet {

  protected double dx;
  protected double dy;

  protected boolean fromPlayer;
  protected Trajectory trajectory;

  /** Lifetime in ticks; -1 means infinite until out-of-bounds. */
  protected int lifeTicks = -1;

  /** Spawn origin (semantic info for trajectories). */
  protected double originX;
  protected double originY;

  /** Canvas bounds (must be set by bullet constructor). */
  protected double canvasW;
  protected double canvasH;

  @Override
  public  Bounds getCollisionBounds() {
    // size must be initialized in each concrete bullet constructor
    return new BoundingBox(x, y, size.getWidth(), size.getHeight());
  }

  @Override
  public final boolean isFromPlayer() {
    return fromPlayer;
  }

  // ----------------------------
  // Getters
  // ----------------------------
  public final double getX() { return x; }
  public final double getY() { return y; }
  public final double getDx() { return dx; }
  public final double getDy() { return dy; }
  public final double getOriginX() { return originX; }
  public final double getOriginY() { return originY; }
  public final double getCanvasW() { return canvasW; }
  public final double getCanvasH() { return canvasH; }

  // ----------------------------
  // Setters (for trajectories/factory)
  // ----------------------------
  public final void setX(double x) { this.x = x; }
  public final void setY(double y) { this.y = y; }
  public final void setDx(double dx) { this.dx = dx; }
  public final void setDy(double dy) { this.dy = dy; }

  public final void setCanvasSize(double canvasW, double canvasH) {
    this.canvasW = canvasW;
    this.canvasH = canvasH;
  }

  public final void setTrajectory(Trajectory trajectory) {
    this.trajectory = trajectory;
  }

  public final void setOrigin(double ox, double oy) {
    this.originX = ox;
    this.originY = oy;
  }

  public final void setLifeTicks(int ticks) {
    this.lifeTicks = ticks;
  }

  /** Decrements lifetime if enabled. When lifeTicks reaches 0, bullet dies. */
  protected final void tickLife() {
    if (lifeTicks > 0) {
      lifeTicks--;
      if (lifeTicks <= 0) aliveFlag = false;
    }
  }

  /** Kills bullet when leaving the canvas. Requires canvasW/canvasH > 0. */
  protected final void killIfOutOfBounds() {
    if (x < 0 || x > canvasW || y < 0 || y > canvasH) {
      aliveFlag = false;
    }
  }

  /** One movement step by the current trajectory (if any). */
  protected final void moveOnce() {
    if (trajectory != null) {
      trajectory.update(this);
    }
  }

  @Override
  public abstract void update();

  @Override
  public abstract void onHit(Aircraft target);
}
