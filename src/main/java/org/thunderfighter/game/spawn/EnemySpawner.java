package org.thunderfighter.game.spawn;

import java.util.List;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.aircraft.enemy.*;
import org.thunderfighter.utils.Constant;

public class EnemySpawner {

  private final Random rng = new Random();
  private final Canvas canvas;
  private List<AbstractEntity> entities;

  private int normalCd = 0;
  private int eliteCd = 0;
  private BossEnemy boss = null;

  public EnemySpawner(Canvas canvas, List<AbstractEntity> entities) {
    this.canvas = canvas;
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
    if (boss != null && boss.isAlive()) {
      return;
    }

    boss = new BossEnemy(canvas.getWidth() / 2 - 100, 0);
    entities.add(boss);
  }

  private double randomX() {
    return rng.nextDouble() * (canvas.getWidth() - 60);
  }
}
