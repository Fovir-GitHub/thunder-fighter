package org.thunderfighter.core.abstractor;

import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.entity.Aircraft;

/**
 * Abstract Aircraft Class
 *
 * <p>Common parent class for all aircraft entities (player aircraft, enemy aircraft, etc.),
 * Provides basic functionalities such as health points, drawing, movement updates, and collision
 * boundaries.
 *
 * <p>When inheriting from this class, the `{@link #move()}` method must be implemented, and `{@link
 * #onUpdate(List)}` and `{@link #onDie()}` can be overridden as needed.
 */
public abstract class AbstractAircraft extends AbstractEntity implements Aircraft {

  /** current health */
  protected int hp;

  /** Airplane texture */
  protected Image sprite; // aircraft texture

  /**
   * Update the aircraft's collision boundaries
   *
   * <p>Based on the current coordinates (x, y) and size, Create a new {@link BoundingBox} as the
   * collision area.
   */
  protected void updateCollisionBounds() {
    if (size != null) {
      collisionBounds = new BoundingBox(x, y, size.getWidth(), size.getHeight());
    }
  }

  /**
   * Get current health
   *
   * @return current HP
   */
  @Override
  public int getHp() {
    return hp;
  }

  /**
   * Takes damage
   *
   * <p>Reduces health. When health is less than or equal to 0:
   *
   * <ul>
   *   <li>Sets the survival flag to false
   *   <li>Triggers the death callback {@link #onDie()}
   * </ul>
   *
   * * * @param damage The amount of damage taken
   */
  @Override
  public void takeDamage(int damage) {
    hp -= damage;
    if (hp <= 0) {
      aliveFlag = false;
      onDie();
    }
  }

  /**
   * Get the aircraft's collision boundaries
   *
   * @return the current {@link Bounds}
   */
  @Override
  public Bounds getCollisionBounds() {
    return collisionBounds;
  }

  /**
   * Update logic for each frame (template method)
   *
   * <p>This method is declared as final to ensure consistent update process:
   *
   * <ol>
   *   <li>Check if alive
   *   <li>Execute movement logic {@link #move()}
   *   <li>Update collision boundaries
   *   <li>Execute subclass extension logic {@link #onUpdate(List)}
   * </ol>
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  public final void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;
    move();
    updateCollisionBounds();
    onUpdate(worldEntities);
  }

  /**
   * Draw the aircraft
   *
   * <p>Draw only while the aircraft is alive, Render using sprite at current coordinates and size.
   *
   * @param gc JavaFX drawing context
   */
  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
  }

  /**
   * Callback method when the plane dies
   *
   * <p>Subclasses can override this method to implement explosion effects, dropped items, scoring
   * logic, etc.
   */
  protected void onDie() {} // aircraft death callback

  /**
   * Aircraft movement logic (must be implemented by a subclass)
   *
   * <p>For example:
   *
   * <ul>
   *   <li>Player aircraft: Moves according to keyboard input
   *   <li>Enemy aircraft: Moves according to AI or a preset path
   * </ul>
   */
  protected abstract void move(); // aircraft movement logic

  /**
   * Additional update logic per frame
   *
   * <p>Subclasses can implement this here:
   *
   * <ul>
   *   <li>Shooting
   *   <li>AI Behavior
   *   <li>Skill Cooldown
   * </ul>
   *
   * @param worldEntities All entities in the current world
   */
  protected void onUpdate(
      List<AbstractEntity> worldEntities) {} // extra logic per frame, like shooting, AI, etc.
}
