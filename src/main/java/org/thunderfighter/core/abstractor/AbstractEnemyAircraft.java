package org.thunderfighter.core.abstractor;

import org.thunderfighter.core.manager.ScoreManager;

import java.util.List;

public abstract class AbstractEnemyAircraft extends AbstractAircraft {

  protected boolean canShoot; // can shoot or not
  protected int shootInterval; // firing interval per frame
  protected int shootCooldown; // shooting cooldown
  protected int score; // destruction score

  @Override
  public boolean isPlayer() {
    return false;
  }

  @Override
  public void shoot(List<AbstractEntity> worldEntities) {
    if (!canShoot) return;
    if (shootCooldown <= 0) {
      doShoot(worldEntities);
      shootCooldown = shootInterval;
    }
  }

  @Override
  protected void onUpdate() {
    if (shootCooldown > 0) shootCooldown--;
  }

  @Override
  protected void onDie() {
    ScoreManager.getInstance().addScore(this.score);
  }

  protected abstract void doShoot(List<AbstractEntity> worldEntities); // implemented by subclass
}
