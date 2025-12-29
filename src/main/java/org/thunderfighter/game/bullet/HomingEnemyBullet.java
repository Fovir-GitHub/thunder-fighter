// src/main/java/org/thunderfighter/game/bullet/HomingEnemyBullet.java

package org.thunderfighter.game.bullet;

import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.thunderfighter.core.abstractor.AbstractBullet;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Aircraft;
import org.thunderfighter.game.trajectory.HomingTrajectory;

/**
 * HomingEnemyBullet
 *
 * <p>Tracks player for a limited time (trackingTicks). Slower than normal bullets by design (tune
 * initDx/initDy and homing factor).
 */
public class HomingEnemyBullet extends AbstractBullet {

  private static final int DAMAGE = 1;

  /** Homing bullet sprite. */
  private static final Image SPRITE =
      new Image(HomingEnemyBullet.class.getResourceAsStream("/images/Bullet/enemy_bullet.png"));

  private int trackingTicks;

  public HomingEnemyBullet(
      double startX,
      double startY,
      double initDx,
      double initDy,
      int trackingTicks,
      HomingTrajectory.TargetProvider provider) {

    this.x = startX;
    this.y = startY;
    this.originX = startX;
    this.originY = startY;

    this.size = new Dimension2D(12, 24);

    this.dx = initDx;
    this.dy = initDy;
    this.speed = Math.hypot(initDx, initDy);

    this.fromPlayer = false;

    this.trajectory = new HomingTrajectory(provider, 0.10);

    this.trackingTicks = Math.max(1, trackingTicks);

    this.lifeTicks = -1;
  }

  @Override
  public void update(List<AbstractEntity> worldEntities) {
    if (!aliveFlag) return;

    trackingTicks--;
    if (trackingTicks <= 0) {
      aliveFlag = false;
      return;
    }

    moveOnce();
    tickLife();
    killIfOutOfBounds();
  }

  @Override
  public void onHit(Aircraft target) {
    target.takeDamage(DAMAGE);
    aliveFlag = false;
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (!aliveFlag) return;
    gc.drawImage(SPRITE, x, y, size.getWidth(), size.getHeight());
  }
}
