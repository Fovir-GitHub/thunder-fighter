package org.thunderfighter.core.abstractor;

import org.thunderfighter.core.entity.Entity;

import java.util.List;

public abstract class AbstractPlayerAircraft extends AbstractAircraft {

  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cooldown
  protected boolean autoMode = true; // default auto shooting
  protected boolean wantToShoot = false;
  protected boolean up, down, left, right;

  public void setUp(boolean up) {this.up = up;}
  public void setDown(boolean down) {this.down = down;}
  public void setLeft(boolean left) {this.left = left;}
  public void setRight(boolean right) {this.right = right;}

  public void setShooting(boolean shooting) {
    this.wantToShoot = shooting;
  }

  public boolean wantToShoot() {
    return wantToShoot;
  }

  @Override
  public boolean isPlayer() {
    return true;
  }

  @Override
  public void shoot(List<Entity> worldEntities) {
    if (shootCooldown <= 0) {
      doShoot(worldEntities);
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate() {
    if (shootCooldown > 0) shootCooldown--;
  }

  protected abstract void doShoot(List<Entity> worldEntities); // implemented by subclass
}
