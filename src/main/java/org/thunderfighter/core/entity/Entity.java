// src/main/java/org/thunderfighter/core/entity/Entity.java

package org.thunderfighter.core.entity;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.abstractor.AbstractEntity;

public interface Entity {
  void update(List<AbstractEntity> worldEntities); // update object status

  void draw(GraphicsContext gc); // used to draw objects

  Dimension2D getSize(); // get the object texture size, for drawing and collision

  double getSpeed(); // the object's base movement speed

  boolean isAlive(); // is alive or not, it will be removed when it is false

  Bounds getCollisionBounds();
}
