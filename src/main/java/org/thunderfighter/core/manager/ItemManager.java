package org.thunderfighter.core.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.thunderfighter.core.abstractor.AbstractEntity;
import org.thunderfighter.core.entity.Bullet;

/**
 * ItemManager (Singleton)
 *
 * <p>Owns all item-bullets (items are bullets). - update(): moves + removes expired/picked items -
 * render(): draws all items
 */
public class ItemManager {

  private static final ItemManager instance = new ItemManager();

  private final List<Bullet> items = new ArrayList<>();

  private ItemManager() {}

  public static ItemManager getInstance() {
    return instance;
  }

  public void addItem(Bullet item) {
    if (item != null) items.add(item);
  }

  public List<Bullet> getItems() {
    return items;
  }

  public void update(List<AbstractEntity> worldEntities) {
    Iterator<Bullet> it = items.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      b.update(worldEntities);
      if (!b.isAlive()) it.remove();
    }
  }

  public void render(GraphicsContext gc) {
    for (Bullet b : items) {
      b.draw(gc);
    }
  }

  public boolean hasActiveItem() {
    return !items.isEmpty();
  }

  public void clearAll() {
    items.clear();
  }
}
