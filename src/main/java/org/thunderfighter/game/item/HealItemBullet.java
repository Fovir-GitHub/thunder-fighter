package org.thunderfighter.game.item;

import org.thunderfighter.core.entity.Aircraft;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 回血道具：
 * - 真正的“加血上限=3”逻辑建议放在 PlayerAircraft 里实现（更干净）
 */
public class HealItemBullet extends ItemBullet {

  public HealItemBullet(double x, double y, double canvasW, double canvasH) {
    super(x, y, ItemType.HEAL, canvasW, canvasH);
  }

  @Override
  protected void applyEffect(Aircraft player) {
    // 这里不强行写死玩家实现类名
    // 你后续可以在 PlayerAircraft 提供 healOne()，然后在这里：
    // if (player instanceof PlayerAircraft p) p.healOne();
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.LIMEGREEN);
    gc.fillOval(x, y, size.getWidth(), size.getHeight());
  }
}
