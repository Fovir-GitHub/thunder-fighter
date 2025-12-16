package org.thunderfighter.entity;

import javafx.scene.canvas.GraphicsContext;
import java.awt.geom.Dimension2D;

public interface Entity {
  void update();
  void draw(GraphicsContext gc);
  Dimension2D getSize();
  double getSpeed();
  boolean isAlive();
}
