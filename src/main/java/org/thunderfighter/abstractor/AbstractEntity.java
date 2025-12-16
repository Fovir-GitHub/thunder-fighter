package org.thunderfighter.abstractor;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.entity.Entity;

public abstract class AbstractEntity implements Entity {
  protected double x;
  protected double y;
  protected double speed;
  protected boolean alive_flag = true;
  protected Dimension2D size;

  @Override
  public Dimension2D getSize() {
    return size;
  }

  @Override
  public double getSpeed() {
    return speed;
  }


  @Override
  public boolean isAlive_flag() {
    return alive_flag;
  }

  @Override
  public abstract void update();

  @Override
  public abstract void draw(GraphicsContext gc);
}
