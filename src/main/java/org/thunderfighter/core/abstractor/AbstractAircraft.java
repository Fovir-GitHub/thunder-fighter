package org.thunderfighter.core.abstractor;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.*;
import org.thunderfighter.core.entity.Aircraft;

import java.util.List;

public abstract class AbstractAircraft extends AbstractEntity implements Aircraft {

  protected int hp; // current health
  protected Image sprite; // aircraft texture

  protected void updateCollisionBounds() {
    if (size != null) {
      collisionBounds = new BoundingBox(x, y, size.getWidth(), size.getHeight());
    }
  }

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
  public final void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;
    move();
    updateCollisionBounds();
    onUpdate(worldEntities);
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(sprite, x, y, size.getWidth(), size.getHeight());
  }

  protected void onDie() {} // aircraft death callback

  protected abstract void move(); // aircraft movement logic

  protected void onUpdate(List<AbstractEntity> worldEntities) {} // extra logic per frame, like shooting, AI, etc.
}
