package org.thunderfighter.core.abstractor;

public abstract class AbstractEnemyAircraft extends AbstractAircraft {

  protected boolean canShoot; // can shoot or not
  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cd
  protected int score; // destruction score

  @Override
  public boolean isPlayer() {
    return false;
  }

  @Override
  public void shoot() {
    if (!canShoot) return;
    if (shootCooldown <= 0) {
      doShoot();
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate() {
    shootCooldown--;
    shoot();
  }

  protected abstract void doShoot();
  // actual firing behavior varies depending on the enemy aircraft, so there is no default implementation.
}
