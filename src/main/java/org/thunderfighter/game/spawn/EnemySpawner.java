package org.thunderfighter.game.spawn;

import java.util.List;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.game.Game;
import org.thunderfighter.game.aircraft.enemy.*;
import org.thunderfighter.utils.Constant;

public class EnemySpawner {

  private final Random rng = new Random();
  private final Canvas canvas;
  private List<AbstractEntity> entities;

  private int normalCd = 0;
  private int eliteCd = 0;
  private BossEnemy boss = null;

  public EnemySpawner(Canvas canvas, List<AbstractEntity> entities, Game game) {
    this.canvas = canvas;
    this.entities = entities;
  }

  public void reset() {
    normalCd = eliteCd = 0;
    boss = null;
  }

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

  public boolean spawnBoss(Game game) {
    if (boss != null && boss.isAlive()) {
      return false;
    }

    boss = new BossEnemy(canvas.getWidth() / 2 - 100, 0, game);
    boss.setCanvas(canvas);
    entities.add(boss);

    return true;
  }

  private double randomX() {
    return rng.nextDouble() * (canvas.getWidth() - 60);
  }
}
