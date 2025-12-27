package org.thunderfighter.core.abstractor;

import org.thunderfighter.core.entity.Entity;

import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractEntity implements Entity {

  protected double x; // x coordinate on the canvas
  protected double y; // y coordinate on the canvas
  protected double speed; // movement speed
  protected boolean aliveFlag = true; // is alive or not
  protected Dimension2D size; // texture size
  protected Bounds collisionBounds; // current collision boundary

  protected Canvas canvas;

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  public Canvas getCanvas() {
    return canvas;
  }

  @Override
  public Bounds getCollisionBounds() {
    return collisionBounds;
  }

  @Override
  public Dimension2D getSize() {
    return size;
  }

  @Override
  public double getSpeed() {
    return speed;
  }

  @Override
  public boolean isAlive() {
    return aliveFlag;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  @Override
  public abstract void update(); // the update logic is implemented by the subclass

  @Override
  public abstract void draw(GraphicsContext gc); // the drawing logic
}
