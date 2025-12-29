// src/main/java/org/thunderfighter/core/abstractor/AbstractEntity.java

package org.thunderfighter.core.abstractor;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.entity.Entity;

/**
 * Abstract base class for entities
 *
 * <p>The common parent class for all interactive objects in the game, such as airplanes, bullets,
 * and items. This class defines the basic attributes of entities, including position, speed, size,
 * survival status, and collision boundaries.
 *
 * <p>The specific update logic and drawing methods are implemented by subclasses.
 */
public abstract class AbstractEntity implements Entity {

  /** The x-coordinate of the entity on the canvas */
  protected double x;

  /** The y-coordinate of the entity on the canvas */
  protected double y;

  /** Entity's movement speed */
  protected double speed;

  /** Whether the entity is alive */
  protected boolean aliveFlag = true;

  /** Entity size (usually corresponds to texture size) */
  protected Dimension2D size;

  /** Current collision boundaries of the entity */
  protected Bounds collisionBounds;

  /** The canvas containing the entity */
  protected Canvas canvas;

  /**
   * Sets the canvas containing the entity
   *
   * @param canvas JavaFX Canvas
   */
  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * Get the canvas containing the entity
   *
   * @return Current Canvas
   */
  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * Get the collision boundaries of the entity
   *
   * @return current {@link Bounds}
   */
  @Override
  public Bounds getCollisionBounds() {
    return collisionBounds;
  }

  /**
   * Get entity dimensions
   *
   * @return entity dimensions {@link Dimension2D}
   */
  @Override
  public Dimension2D getSize() {
    return size;
  }

  /**
   * Get the entity's movement speed
   *
   * @return speed
   */
  @Override
  public double getSpeed() {
    return speed;
  }

  /**
   * Determine if the entity is still alive
   *
   * @return true if the entity is alive, false if the entity has been destroyed
   */
  @Override
  public boolean isAlive() {
    return aliveFlag;
  }

  /**
   * Get the current x-coordinate of the entity
   *
   * @return x-coordinate
   */
  public double getX() {
    return this.x;
  }

  /**
   * Get the current y-coordinate of the entity
   *
   * @return y-coordinate
   */
  public double getY() {
    return this.y;
  }

  /**
   * Update logic for each frame
   *
   * <p>Specific behaviors are implemented by subclasses, for example:
   *
   * <ul>
   *   <li>Position update
   *   <li>State change
   *   <li>Collision detection
   * </ul>
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  public abstract void update(List<AbstractEntity> worldEntities);

  /**
   * Draw the entity
   *
   * <p>The specific drawing method is implemented by subclasses.
   *
   * @param gc JavaFX drawing context
   */
  @Override
  public abstract void draw(GraphicsContext gc);
}
