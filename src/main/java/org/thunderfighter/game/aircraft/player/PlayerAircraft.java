// src/main/java/org/thunderfighter/game/aircraft/player/PlayerAircraft.java

package org.thunderfighter.game.aircraft.player;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.game.Game;
import org.thunderfighter.game.bullet.PlayerBullet;
import org.thunderfighter.utils.Constant.GAME_STATE;

/**
 * Player-controlled aircraft
 * Features:
 * - Supports movement up, down, left, and right
 * - Diagonal movement undergoes vector normalization to ensure consistent speed
 * - Can fire at player targets
 * - Has damage cooldown
 */
public class PlayerAircraft extends AbstractPlayerAircraft {

  /** Player aircraft dimensions (width 60, height 80) */
  public static final Dimension2D SIZE = new Dimension2D(60, 80); // @params

  /** Timestamp of the last time damage was taken (milliseconds) */
  private long lastDamageTime = 0; // last damage

  /** Injury cooldown (milliseconds), used to prevent continuous health loss */
  private final long damageCooldown = 1000; // damage cd

  /**
   * Constructor
   *
   * @param x Initial X coordinate
   * @param y Initial Y coordinate
   * @param hp Initial health
   * @param speed Movement speed
   * @param shootInterval Shooting interval (frames)
   * @param canvas Game canvas
   * @param game Main game object
   */
  public PlayerAircraft(
      double x, double y, int hp, double speed, int shootInterval, Canvas canvas, Game game) {
    this.x = x;
    this.y = y;
    this.hp = hp;
    this.speed = speed;
    this.shootInterval = shootInterval;
    this.size = SIZE;

    this.sprite = new Image(getClass().getResourceAsStream("/images/Aircraft/PlayerAircraft.png"));
    this.canvas = canvas;
    this.game = game;
  }

  /**
   * Player movement logic
   * - Calculate the movement vector based on the arrow key state
   * - Normalize the vector for diagonal movement to prevent it from being faster
   * - Detect boundaries to prevent players from flying off-screen
   */
  @Override
  protected void move() {
    double dx = 0;
    double dy = 0;
    double tempX = this.x;
    double tempY = this.y;

    if (up) {
      dy -= 1;
    }
    if (down) {
      dy += 1;
    }
    if (left) {
      dx -= 1;
    }
    if (right) {
      dx += 1;
    }

    if (dx != 0 || dy != 0) {
      double length = Math.sqrt(dx * dx + dy * dy);
      dx /= length;
      dy /= length;
    }

    tempX += dx * speed;
    tempY += dy * speed;

    if (tempX <= 0
        || tempX + this.size.getWidth() >= canvas.getWidth()
        || tempY <= 0
        || tempY + this.size.getHeight() >= canvas.getHeight()) {
      return;
    }

    this.x = tempX;
    this.y = tempY;
  } // to prevent the aircraft from traveling faster at an angle than straight.

  /**
   * Player shooting logic
   * Fire a player bullet directly above the plane
   */
  @Override
  protected void doShoot(List<AbstractEntity> worldEntities) {
    PlayerBullet bullet = new PlayerBullet(x + size.getWidth() / 2 - 4, y - 10); // @params
    bullet.setCanvas(canvas);
    worldEntities.add(bullet); // by manager instance to manage it.
  }

  /**
   * Player damage logic
   * - There is a cooldown period for taking damage (invincibility frames)
   * - Game ends when health reaches zero
   */
  @Override
  public void takeDamage(int amount) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastDamageTime >= damageCooldown) {
      this.hp -= amount;
      lastDamageTime = currentTime; // update damage cd time
    }
    if (hp <= 0) {
      aliveFlag = false;
      game.setGameState(GAME_STATE.FAIL);
    }
  }
}
