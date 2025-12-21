package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 无敌道具：
 * - 无敌计时建议放在 PlayerAircraft 内维护（5s=300ticks）
 */
public class ShieldItemBullet extends ItemBullet {

  public ShieldItemBullet(double x, double y, double canvasW, double canvasH) {
    super(x, y, ItemType.SHIELD, canvasW, canvasH);
  }

  @Override
  protected void applyEffect(Aircraft player) {
    // 同上：后续你在 PlayerAircraft 加 setInvincibleTicks(int)
    // if (player instanceof PlayerAircraft p) p.setInvincibleTicks(300);
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.CYAN);
    gc.fillOval(x, y, size.getWidth(), size.getHeight());
  }
}
