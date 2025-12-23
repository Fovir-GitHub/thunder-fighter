package org.thunderfighter.core.abstractor;

import javafx.scene.image.Image;

public abstract class AbstractEnemyAircraft extends AbstractAircraft {

  protected Image image;
  protected boolean canShoot; // can shoot or not
  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cooldown
  protected int score; // destruction score

  @Override
  public boolean isPlayer() {
    return false;
  }

  @Override
  public void shoot() {
    if(!canShoot) return;
    if(shootCooldown <= 0) {
      doShoot();
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate() {
    if(shootCooldown > 0) shootCooldown--;
    shoot();
  }

  protected abstract void doShoot(); // implemented by subclass
}
