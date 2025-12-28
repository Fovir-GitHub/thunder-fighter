package org.thunderfighter.core.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Bullet;

// same architecture as BulletManager.java

public class PropManager {

  private static final PropManager instance = new PropManager();
  private final List<Bullet> props = new ArrayList<>();

  private PropManager() {}

  public static PropManager getInstance() {
    return instance;
  } // api

  public void addProp(Bullet prop) {
    if (prop != null) {
      props.add(prop);
    }
  }

  public List<Bullet> getProps() {
    return props;
  } // get the list of props

  public void update(List<AbstractEntity> worldEntities) {
    Iterator<Bullet> it = props.iterator();
    while (it.hasNext()) {
      Bullet p = it.next();
      p.update(worldEntities);
      if (!p.isAlive()) {
        it.remove();
      }
    }
  } // update all the props

  public void clear() {
    props.clear();
  } // clear all props
}
