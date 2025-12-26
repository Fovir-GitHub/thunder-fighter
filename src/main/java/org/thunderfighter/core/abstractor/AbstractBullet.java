package org.thunderfighter.core.abstractor;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.core.entity.Bullet;
import org.thunderfighter.core.entity.Trajectory;

public abstract class AbstractBullet extends AbstractEntity implements Bullet {
  protected double dx;
  protected double dy;
  protected boolean fromPlayer;
  protected Trajectory trajectory;
  protected int lifeTicks = -1;
  protected double originX;
  protected double originY;
  protected double canvasW;
  protected double canvasH;

  @Override
  public final Bounds getCollisionBounds() {
    return new BoundingBox(x, y, size.getWidth(), size.getHeight());
  }

  @Override
  public final boolean isFromPlayer() {
    return fromPlayer;
  }

  public final double getX() {
    return x;
  }

  public final double getY() {
    return y;
  }

  public final double getDx() {
    return dx;
  }

  public final double getDy() {
    return dy;
  }

  public final double getOriginX() {
    return originX;
  }

  public final double getOriginY() {
    return originY;
  }

  public final double getCanvasW() {
    return canvasW;
  }

  public final double getCanvasH() {
    return canvasH;
  }

  public final void setX(double x) {
    this.x = x;
  }

  public final void setY(double y) {
    this.y = y;
  }

  public final void setDx(double dx) {
    this.dx = dx;
  }

  public final void setDy(double dy) {
    this.dy = dy;
  }

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

  protected final void tickLife() {
    if (lifeTicks > 0) {
      lifeTicks--;
      if (lifeTicks <= 0) aliveFlag = false;
    }
  }

  protected final void killIfOutOfBounds() {
    if (x < 0 || x > canvasW || y < 0 || y > canvasH) aliveFlag = false;
  }

  protected final void moveOnce() {
    if (trajectory != null) trajectory.update(this);
  }

  @Override
  public abstract void update();

  @Override
  public abstract void onHit(Aircraft target);
}
