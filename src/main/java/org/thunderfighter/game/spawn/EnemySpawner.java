package org.thunderfighter.game.spawn;

import java.util.Random;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.aircraft.enemy.*;

public class EnemySpawner {

  @FunctionalInterface
  public interface EnemySpawnListener {
    void onSpawn(Aircraft enemy);
  }

  private enum Phase {
    NORMAL,
    ELITE,
    BOSS
  }

  private static final int TPS = 60;

  private final Random rng = new Random();
  private final double canvasW;
  private final EnemySpawnListener listener;

  private Phase phase = Phase.NORMAL;

  private int normalCd = 0;
  private int eliteCd = 0;
  private boolean bossSpawned = false;

  public EnemySpawner(double canvasW, EnemySpawnListener listener) {
    this.canvasW = canvasW;
    this.listener = listener;
  }

  public void update(int score) {
    updatePhase(score);

    if (phase == Phase.NORMAL || phase == Phase.ELITE) {
      spawnNormal();
    }

    if (phase == Phase.ELITE) {
      spawnElite();
    }

    if (phase == Phase.BOSS && !bossSpawned) {
      spawnBoss();
    }
  }

  private void updatePhase(int score) {
    if (score >= 3000) phase = Phase.BOSS;
    else if (score >= 1000) phase = Phase.ELITE;
    else phase = Phase.NORMAL;
  }

  private void spawnNormal() {
    if (normalCd-- > 0) return;
    normalCd = 2 * TPS;

    listener.onSpawn(new NormalEnemy(randomX()));
  }

  private void spawnElite() {
    if (eliteCd-- > 0) return;
    eliteCd = 5 * TPS;

    listener.onSpawn(new EliteEnemy(randomX()));
  }

  private void spawnBoss() {
    bossSpawned = true;
    listener.onSpawn(new BossEnemy(canvasW / 2 - 100));
  }

  private double randomX() {
    return rng.nextDouble() * (canvasW - 60);
  }
}
