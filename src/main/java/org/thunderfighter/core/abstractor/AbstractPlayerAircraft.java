package org.thunderfighter.core.abstractor;

public abstract class AbstractPlayerAircraft extends AbstractAircraft {

  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cd
  protected boolean autoMode = true; // default open auto mode

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
    shootCooldown--;
    if (autoMode) {
      shoot();
    }
  }

  protected abstract void doShoot(); // the actual shooting behavior
  // implemented by the subclass.
}
