package org.thunderfighter.core.abstractor;

import java.util.List;
import javafx.scene.canvas.Canvas;
import org.thunderfighter.game.Game;

/**
 * Abstract player aircraft class
 *
 * <p>Inherited from {@link AbstractAircraft}, used for player-controlled aircraft. Provides player
 * input state management, shooting cooldown logic, and shooting behavior templates.
 *
 * <p>Specific shooting methods are implemented by subclasses using {@link #doShoot(List)}.
 */
public abstract class AbstractPlayerAircraft extends AbstractAircraft {

  /** Firing interval (in frames) */
  protected int shootInterval;

  /** Current firing cooldown time */
  protected int shootCooldown;

  /** Whether the player wants to shoot is controlled by input */
  protected boolean wantToShoot = false;

  /** Player direction input status */
  protected boolean up, down, left, right;

  /** The canvas where the player's plane is located (used for rendering or input detection) */
  protected Canvas canvas;

  /** The current game instance, which can be used to obtain game status or manage entities */
  protected Game game;

  /**
   * Sets the upward movement state
   *
   * @param up true indicates upward movement
   */
  public void setUp(boolean up) {
    this.up = up;
  }

  /**
   * Sets the downward movement state
   *
   * @param down true indicates downward movement
   */
  public void setDown(boolean down) {
    this.down = down;
  }

  /**
   * Sets the leftward movement state
   *
   * @param left true indicates leftward movement
   */
  public void setLeft(boolean left) {
    this.left = left;
  }

  /**
   * Sets the rightward movement state
   *
   * @param right true indicates rightward movement
   */
  public void setRight(boolean right) {
    this.right = right;
  }

  /**
   * Sets the player's shooting intention
   *
   * @param shooting true indicates that the player wants to shoot
   */
  public void setShooting(boolean shooting) {
    this.wantToShoot = shooting;
  }

  /**
   * Get whether the player wants to fire
   *
   * @return true indicates that the player currently requests to fire
   */
  public boolean wantToShoot() {
    return wantToShoot;
  } // shooting info

  /**
   * Determine if the aircraft is controlled by a player
   *
   * @return Always returns true
   */
  @Override
  public boolean isPlayer() {
    return true;
  }

  /**
   * Shooting logic
   *
   * <p>When the shooting cooldown ends, trigger the actual shot and reset the cooldown.
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  public void shoot(List<AbstractEntity> worldEntities) {
    if (shootCooldown <= 0) {
      doShoot(worldEntities);
      shootCooldown = shootInterval;
    }
  }

  /**
   * Per-frame update logic
   *
   * <p>Responsible for basic update logic of the player's aircraft, such as firing cooldown
   * reduction. Specific movement logic is implemented by {@link #move()}.
   *
   * @param worldEntities All entities in the current world
   */
  @Override
  protected void onUpdate(List<AbstractEntity> worldEntities) {
    if (shootCooldown > 0) {
      shootCooldown--;
    }
  }

  /**
   * Actual shooting implementation
   *
   * <p>Implemented by a specific player aircraft subclass, used to generate bullets or perform
   * special attacks.
   *
   * @param worldEntities All entities in the current world
   */
  protected abstract void doShoot(List<AbstractEntity> worldEntities); // implemented by subclass
}
