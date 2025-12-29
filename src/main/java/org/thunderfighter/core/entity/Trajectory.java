// src/main/java/org/thunderfighter/core/entity/Trajectory.java

package org.thunderfighter.core.entity;

import org.thunderfighter.core.abstractor.AbstractBullet;

public interface Trajectory {
  void update(AbstractBullet bullet);
}
