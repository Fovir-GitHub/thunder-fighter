package org.thunderfighter.game.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.entity.Aircraft;

public class ClearItemBullet extends ItemBullet {

  private final ClearScreenHandler handler;
  private final int clearWindowTicks;

  public ClearItemBullet(
      double x,
      double y,
      double canvasW,
      double canvasH,
      ClearScreenHandler handler,
      int clearWindowTicks) {
    super(x, y, ItemType.CLEAR, canvasW, canvasH);
    this.handler = handler;
    this.clearWindowTicks = clearWindowTicks;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (handler != null) {
      handler.clearEnemyBulletsNow(); // 立刻清当前敌弹（含激光）
      handler.clearNormalEnemies(); // 清普通怪，保留精英/BOSS
      handler.startClearWindow(clearWindowTicks); // 开1秒窗口
    }
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.WHITE);
    gc.fillOval(x, y, size.getWidth(), size.getHeight());
    gc.setFill(Color.BLACK);
    gc.fillText("C", x + 6, y + 13);
  }
}
