package org.thunderfighter.core.abstractor;

public abstract class AbstractPlayerAircraft extends AbstractAircraft {

  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cooldown
  protected boolean autoMode = true; // default auto shooting

  protected boolean up, down, left, right;

  public void setUp(boolean up) {this.up = up;}
  public void setDown(boolean down) {this.down = down;}
  public void setLeft(boolean left) {this.left = left;}
  public void setRight(boolean right) {this.right = right;}

  @Override
  public boolean isPlayer() {
    return true;
  }

  @Override
  public void shoot() {
    if (shootCooldown <= 0) {
      doShoot();
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate() {
    if (shootCooldown > 0) shootCooldown--;
    if (autoMode) shoot();
  }

  protected abstract void doShoot(); // implemented by subclass
}
