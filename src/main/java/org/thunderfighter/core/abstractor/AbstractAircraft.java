package org.thunderfighter.core.abstractor;

import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.entity.Aircraft;

public abstract class AbstractAircraft extends AbstractEntity implements Aircraft {

  protected int hp; // current health
  protected Bounds collisionBounds; // current collision boundary
  protected Image sprite; // aircraft textures

  protected void updateCollisionBounds() {
    collisionBounds = new BoundingBox(
      x, y,
      size.getWidth(),
      size.getHeight()
    );
  }
  // Update the collision boundaries based on the current position and size
  // this must be called after each movement.

  @Override
  public int getHp() {
    return hp;
  }

  @Override
  public void takeDamage(int damage) {
    hp -= damage;
    if (hp <= 0) {
      aliveFlag = false;
      onDie();
    }
  }

  @Override
  public Bounds getCollisionBounds() {
    return collisionBounds;
  }

  @Override
  public final void update() {
    if (!aliveFlag) return;
    move();
    updateCollisionBounds();
    onUpdate();
  } // unified update process for fighter jets, but cannot be overridden by subclasses

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag || sprite == null) return;
    gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
  } // default drawing logic

  protected void onDie() {} // aircraft death callback
  protected abstract void move(); // aircraft movement logic
  protected void onUpdate() {} // extra logic per frame, like shooting, AI, etc.
}
