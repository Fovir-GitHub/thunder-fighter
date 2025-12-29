// src/main/java/org/thunderfighter/core/abstractor/AbstractBullet.java

package org.thunderfighter.core.abstractor;

import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.entity.Trajectory;

/**
 * AbstractBullet
 *
 * <p>Base class for all bullets (including items). Canvas is inherited from AbstractEntity.
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
  public Bounds getCollisionBounds() {
    // size must be initialized in each concrete bullet constructor
    return new BoundingBox(x, y, size.getWidth(), size.getHeight());
  }

  @Override
  public final boolean isFromPlayer() {
    return fromPlayer;
  }

  // ----------------------------
  // Trajectory-required accessors
  // ----------------------------

  /** Position X (delegated to AbstractEntity state). */
  public double getX() {
    return x;
  }

  /** Position Y (delegated to AbstractEntity state). */
  public double getY() {
    return y;
  }

  /** Set position X. */
  public void setX(double x) {
    this.x = x;
  }

  /** Set position Y. */
  public void setY(double y) {
    this.y = y;
  }

  /** Velocity X per tick. */
  public double getDx() {
    return dx;
  }

  /** Velocity Y per tick. */
  public double getDy() {
    return dy;
  }

  /** Set velocity X per tick. */
  public void setDx(double dx) {
    this.dx = dx;
  }

  /** Set velocity Y per tick. */
  public void setDy(double dy) {
    this.dy = dy;
  }

  /** Allow runtime trajectory swapping (optional but useful). */
  public void setTrajectory(Trajectory trajectory) {
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

  /** Kill bullet when leaving canvas. Canvas comes from AbstractEntity. */
  protected final void killIfOutOfBounds() {
    if (canvas == null) return;

    double w = canvas.getWidth();
    double h = canvas.getHeight();

    if (x + size.getWidth() < 0 || x > w || y + size.getHeight() < 0 || y > h) {
      aliveFlag = false;
    }
  }

  /** One movement step by the current trajectory (if any). */
  protected final void moveOnce() {
    if (trajectory != null) {
      trajectory.update(this);
    }
  }

  public double getOriginY() {
    return originY;
  }

  @Override
  public abstract void update(List<AbstractEntity> worldEntities);

  @Override
  public abstract void onHit(Aircraft target);
}
