package org.thunderfighter.game.spawn;

import java.util.List;
import java.util.Random;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.aircraft.enemy.*;
import org.thunderfighter.utils.Constant;

public class EnemySpawner {

  private final Random rng = new Random();
  private final double canvasW;
  private List<AbstractEntity> entities;

  private int normalCd = 0;
  private int eliteCd = 0;

  public EnemySpawner(double canvasW, List<AbstractEntity> entities) {
    this.canvasW = canvasW;
    this.entities = entities;
  }

  public void spawnNormal() {
    if (normalCd-- > 0) {
      return;
    }
    normalCd = 2 * Constant.TPS;
    entities.add(new NormalEnemy(randomX()));
  }

  public void spawnElite() {
    if (eliteCd-- > 0) {
      return;
    }
    eliteCd = 5 * Constant.TPS;
    entities.add(new EliteEnemy(randomX()));
  }

  public void spawnBoss() {
    entities.add(new BossEnemy(canvasW / 2 - 100, 0));
  }

  private double randomX() {
    return rng.nextDouble() * (canvasW - 60);
  }
}
