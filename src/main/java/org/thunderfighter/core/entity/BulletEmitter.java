package org.thunderfighter.core.entity;

public interface BulletEmitter {
  void emit(Bullet bullet);
}
// A simple callback interface to decouple Aircraft.shoot() from the world bullet list.
// Aircraft can call emitter.emit(...) without directly accessing collections in the game world.
