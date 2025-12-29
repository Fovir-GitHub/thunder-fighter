package org.thunderfighter.core.abstractor;

import java.util.List;
import org.thunderfighter.core.manager.ScoreManager;

/**
 * Abstract Enemy Aircraft Class
 *
 * <p>Inherited from {@link AbstractAircraft}, serving as the common parent class for all enemy
 * aircraft. Provides common shooting controls, cooldown logic, and kill score calculation for all
 * enemy aircraft. *
 *
 * <p>Subclasses only need to focus on the specific movement methods and shooting implementation
 * ({@link #doShoot(List)}).
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {

  /** can shoot or not */
  protected boolean canShoot;

  /** firing interval per frame */
  protected int shootInterval;

  /** shooting cooldown */
  protected int shootCooldown;

  /** destruction score */
  protected int score;

  /**
   * Indicates whether the aircraft is a player aircraft
   *
   * @return always returns false, indicating an enemy unit
   */
  @Override
  public boolean isPlayer() {
    return false;
  }

  /**
   * Attempt to fire
   *
   * <p>Firing logic will only be executed if the enemy aircraft has the ability to fire and the
   * cooldown has ended.
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  public void shoot(List<AbstractEntity> worldEntities) {
    if (!canShoot) return;
    if (shootCooldown <= 0) {
      doShoot(worldEntities);
      shootCooldown = shootInterval;
    }
  }

  /**
   * Check whether the enemy moving out of the map.
   *
   * <p>If it is out of the map, it is not alive.
   *
   * <p>Since the enemy is only generated within the width of {@code canvas}, so the {@code x} is
   * always inside the range.
   *
   * <p>However, the enemy is generated above the {@code canvas}, so the upper boundary can be
   * skipped to check.
   *
   * <p>In this case, this function only checks whether the {@code y} is greater than the height of
   * {@code canvas}.
   */
  private final void checkBoundary() {
    if (this.y >= canvas.getHeight()) {
      this.aliveFlag = false;
    }
  }

  /**
   * Per-frame update logic (enemy aircraft only)
   *
   * <p>Responsible for:
   *
   * <ul>
   *   <li>Decrease firing cooldown
   *   <li>Trigger firing attempt
   * </ul>
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    if (shootCooldown > 0) shootCooldown--;
    shoot(worldEntities);
    checkBoundary();
  }

  /**
   * Callback for enemy aircraft death
   *
   * <p>When an enemy aircraft is destroyed, add the corresponding score to {@link ScoreManager}.
   */
  @Override
  protected void onDie() {
    ScoreManager.getInstance().addScore(this.score);
  }

  /**
   * Actual firing logic
   *
   * <p>Implemented by a specific enemy subclass, used to generate bullets, Set ballistics or
   * special attack behaviors.
   *
   * @param worldEntities All entities in the current world
   */
  protected abstract void doShoot(List<AbstractEntity> worldEntities);
}
