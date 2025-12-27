package org.thunderfighter.core.abstractor;

import java.util.List;
import javafx.scene.canvas.Canvas;

public abstract class AbstractPlayerAircraft extends AbstractAircraft {

  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cooldown
  protected boolean autoMode = true; // default auto shooting
  protected boolean wantToShoot = false;
  protected boolean up, down, left, right;
  protected Canvas canvas;

  public void setUp(boolean up) {
    this.up = up;
  }

  public void setDown(boolean down) {
    this.down = down;
  }

  public void setLeft(boolean left) {
    this.left = left;
  }

  public void setRight(boolean right) {
    this.right = right;
  }

  public void setShooting(boolean shooting) {
    this.wantToShoot = shooting;
  }

  public boolean wantToShoot() {
    return wantToShoot;
  } // shooting info

  @Override
  public boolean isPlayer() {
    return true;
  }

  public boolean isAutoMode() {
    return autoMode;
  }

  public void setAutoMode(boolean autoMode) {
    this.autoMode = autoMode;
  } // auto or hand

  @Override
  public void shoot(List<AbstractEntity> worldEntities) {
    if (shootCooldown <= 0) {
      doShoot(worldEntities);
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    if (shootCooldown > 0) {
      shootCooldown--;
    }
  }

  protected abstract void doShoot(List<AbstractEntity> worldEntities); // implemented by subclass
}
