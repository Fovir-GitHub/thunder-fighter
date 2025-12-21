package org.thunderfighter.core.abstractor;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.entity.Entity;

public abstract class AbstractEntity implements Entity {
  protected double x; // x coordinate on the canvas
  protected double y; // y coordinate on the canvas
  protected double speed; // movement speed
  protected boolean aliveFlag = true; // is alive or not
  protected Dimension2D size; // texture size

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

  @Override
  public abstract void update(); // the update logic is implemented by the subclass

  @Override
  public abstract void draw(GraphicsContext gc); // the drawing logic
  // implemented by subclasses
}
