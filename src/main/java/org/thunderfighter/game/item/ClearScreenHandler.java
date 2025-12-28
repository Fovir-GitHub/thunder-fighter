package org.thunderfighter.game.item;

/**
 * ClearScreenHandler
 *
 * <p>Clear-screen item needs cooperation with the World / GameController. The world should
 * implement this interface to: - Clear all enemy bullets (including lasers) - Clear normal enemies
 * (keep elite/boss) - Start a 1-second "clear window" where newly spawned enemy bullets are
 * instantly removed
 */
public interface ClearScreenHandler {

  /** Clear all currently existing enemy bullets (including lasers). */
  void clearEnemyBulletsNow();

  /** Clear normal enemies, but keep elite enemies and boss. */
  void clearNormalEnemies();

  /**
   * Start a clear window for the next N ticks. During this window, any newly spawned enemy bullet
   * should be cleared immediately.
   */
  void startClearWindow(int ticks);
}
