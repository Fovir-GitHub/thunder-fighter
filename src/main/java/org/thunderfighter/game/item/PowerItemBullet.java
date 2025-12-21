package org.thunderfighter.game.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.entity.Aircraft;

public class PowerItemBullet extends ItemBullet {

  // 10s（默认TPS=60 -> 600 ticks）
  private final int buffTicks;
  private final int bonusDamage;

  public PowerItemBullet(double x, double y, double canvasW, double canvasH, int buffTicks, int bonusDamage) {
    super(x, y, ItemType.POWER, canvasW, canvasH);
    this.buffTicks = buffTicks;
    this.bonusDamage = bonusDamage;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    if (player instanceof PlayerItemEffect p) {
      p.setPowerBuffTicks(buffTicks, bonusDamage);
    }
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.GOLD);
    gc.fillOval(x, y, size.getWidth(), size.getHeight());
    gc.setFill(Color.BLACK);
    gc.fillText("P", x + 6, y + 13);
  }
}
