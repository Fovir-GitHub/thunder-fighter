package org.thunderfighter.game.spawn;

import java.util.List;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.Game;
import org.thunderfighter.game.aircraft.enemy.*;
import org.thunderfighter.utils.Constant;

/**
 * Enemy Generator
 * Responsible for:
 * - Normal Enemy Generator (NormalEnemy)
 * - Elite Enemy Generator (EliteEnemy)
 * - Boss Generator (BossEnemy)
 * Uses cooldown (CD) to control the generation frequency
 */
public class EnemySpawner {

  /** Random number generator used to randomly generate the X coordinate of enemy aircraft */
  private final Random rng = new Random();

  /** Game canvas */
  private final Canvas canvas;

  /** World entity list, used to store generated enemy aircraft */
  private List<AbstractEntity> entities;

  /** Cooldown for normal enemy aircraft spawning (frames) */
  private int normalCd = 0;

  /** Cooldown for elite enemy aircraft spawning (frames) */
  private int eliteCd = 0;

  /** Current Boss instance (guaranteed that only one Boss exists at a time) */
  private BossEnemy boss = null;

  /**
   * Constructor
   *
   * @param canvas Game canvas
   * @param entities List of world entities
   * @param game Game object (used when the boss is generated)
   */
  public EnemySpawner(Canvas canvas, List<AbstractEntity> entities, Game game) {
    this.canvas = canvas;
    this.entities = entities;
  }

  /**
   * Resets the generator state
   * Usually called when restarting the game
   */
  public void reset() {
    normalCd = eliteCd = 0;
    boss = null;
  }

  /**
   * Generate a regular enemy aircraft
   *
   * @return Whether the generation was successful
   */
  public boolean spawnNormal() {
    if (normalCd-- > 0) {
      return false;
    }
    normalCd = Constant.TPS;

    NormalEnemy enemy = new NormalEnemy(randomX());
    enemy.setCanvas(canvas);
    entities.add(enemy);

    return true;
  }

  /**
   * Generate elite enemy aircraft
   *
   * @return Whether generation was successful
   */
  public boolean spawnElite() {
    if (eliteCd-- > 0) {
      return false;
    }
    eliteCd = 3 * Constant.TPS;

    EliteEnemy enemy = new EliteEnemy(randomX());
    enemy.setCanvas(canvas);
    entities.add(enemy);

    return true;
  }

  /**
   * Generate a Boss
   * Only one Boss is allowed at a time
   *
   * @param game Game object (used for Boss state control)
   * @return Whether the Boss was successfully generated
   */
  public boolean spawnBoss(Game game) {
    if (boss != null && boss.isAlive()) {
      return false;
    }

    boss = new BossEnemy(canvas.getWidth() / 2 - 100, 0, game);
    boss.setCanvas(canvas);
    entities.add(boss);

    return true;
  }

  /**
   * Generate random X coordinates
   * Ensure enemy aircraft do not exceed the right side of the canvas
   */
  private double randomX() {
    return rng.nextDouble() * (canvas.getWidth() - 60);
  }
}
