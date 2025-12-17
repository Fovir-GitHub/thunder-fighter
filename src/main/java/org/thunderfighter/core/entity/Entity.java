package org.thunderfighter.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Dimension2D;

public interface Entity {
  void update();
  void draw(GraphicsContext gc);
  Dimension2D getSize();
  double getSpeed();
  boolean isAlive();
}

