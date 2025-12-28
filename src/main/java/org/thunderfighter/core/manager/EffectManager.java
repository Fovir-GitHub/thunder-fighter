package org.thunderfighter.core.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// same architecture as BulletManager.java

public class EffectManager {

  private static final EffectManager instance = new EffectManager();
  private final List<Runnable> effects = new ArrayList<>();

  private EffectManager() {}

  public static EffectManager getInstance() {
    return instance;
  }

  public void addEffect(Runnable effect) {
    if (effect != null) {
      effects.add(effect);
    }
  }

  public void update() {
    Iterator<Runnable> it = effects.iterator();
    while (it.hasNext()) {
      Runnable e = it.next();
      e.run(); // effect decides when to stop itself
    }
  }

  public void clear() {
    effects.clear();
  }
}
