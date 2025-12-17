package org.thunderfighter.core.entity;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;

public interface Entity {
  void update();

  void draw(GraphicsContext gc);

  Dimension2D getSize();

  double getSpeed();

  boolean isAlive();
}
