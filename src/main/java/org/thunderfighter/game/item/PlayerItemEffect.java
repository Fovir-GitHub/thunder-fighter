package org.thunderfighter.game.item;

/**
 * PlayerItemEffect
 *
 * <p>Capability interface for player aircraft. Any Aircraft that wants to receive item effects
 * should implement this interface.
 */
public interface PlayerItemEffect {

  /** Heal the player by 1 HP. */
  void healOne();

  /** Set invincibility duration in ticks. */
  void setInvincibleTicks(int ticks);

  /** Enable damage boost for a duration. */
  void setPowerBuffTicks(int ticks, int bonusDamage);
}
