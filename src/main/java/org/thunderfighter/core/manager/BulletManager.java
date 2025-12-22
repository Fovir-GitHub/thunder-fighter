package org.thunderfighter.core.manager;

import org.thunderfighter.core.entity.Bullet;

import java.util.*;

public class BulletManager {
  private static BulletManager instance = new BulletManager();
  private List<Bullet> bullets = new ArrayList<>();

  private BulletManager() {}

  public static BulletManager getInstance() {
    return instance;
  } // api

  public void addBullet(Bullet bullet) {
    bullets.add(bullet);
  } // add a bullet into manager(bullets)

  public List<Bullet> getBullets() {
    return bullets;
  } // all bullets list

  public void update() {
    Iterator<Bullet> it = bullets.iterator(); // iterator to traverse

    while(it.hasNext()) {
      Bullet b = it.next();
      b.update();
      if(!b.isAlive()) it.remove(); // remove from manager(bullets) if it die
    }
  }

  public void clear() {
    bullets.clear();
  } // clear all bullets in manager(bullets)
}
