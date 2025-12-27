package org.thunderfighter.core.entity;

import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;

public interface Entity {
  void update(); // update object status

  void draw(GraphicsContext gc); // used to draw objects

  Dimension2D getSize(); // get the object texture size, for drawing and collision

  double getSpeed(); // the object's base movement speed

  boolean isAlive(); // is alive or not, it will be removed when it is false

  Bounds getCollisionBounds();
}
