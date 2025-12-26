package org.thunderfighter.game.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.thunderfighter.core.entity.Aircraft;

/**
 * Shield item (invincibility item).
 *
 * <p>Behavior: - This item is still a bullet-like entity (ItemBullet). - It bounces inside the
 * canvas (handled by ItemBullet's trajectory). - When the player collides with it, it grants
 * invincibility for a duration.
 *
 * <p>Notes: - The actual invincibility timer should be maintained inside PlayerAircraft. - We apply
 * the effect through PlayerItemEffect interface (recommended).
 */
public class ShieldItemBullet extends ItemBullet {

  /** Invincibility duration in ticks (e.g., 5s * 60TPS = 300). */
  private final int invincibleTicks;

  public ShieldItemBullet(double x, double y, double canvasW, double canvasH, int invincibleTicks) {
    super(x, y, ItemType.SHIELD, canvasW, canvasH);
    this.invincibleTicks = invincibleTicks;
  }

  @Override
  protected void applyEffect(Aircraft player) {
    // Apply effect only if the player supports item effects
    if (player instanceof PlayerItemEffect) {
      PlayerItemEffect p = (PlayerItemEffect) player;
      p.setInvincibleTicks(invincibleTicks);
    }
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(Color.CYAN);
    gc.fillOval(x, y, size.getWidth(), size.getHeight());
  }
}
