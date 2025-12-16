package org.thunderfighter.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.yaml.snakeyaml.Yaml;

/**
 * Load configuration files of the game.
 *
 * <p>All configuration files are under {@code resources/config}.
 *
 * <p>This class initializes all {@code .yaml} files at the start edge, and uses the filename
 * without extension as the key stored in {@code Map}. For example, the key of {@code bullet.yaml}
 * is {@code bullet}.
 *
 * <p><b>Usage:</b>
 *
 * <pre>
 * int speed =
 * (int)
 * ((Map)
 * ((Map) Config.getInstance().getConfigMapByKey("bullet").get("bullets"))
 * .get("player"))
 * .get("speed");
 * </pre>
 */
public final class Config {

  private static final Config INSTANCE = new Config();

  private Map<String, Map<String, Object>> config = new HashMap<>();

  private Config() {
    loadAllConfigs();
  }

  public static Config getInstance() {
    return INSTANCE;
  }

  private void loadAllConfigs() {
    final String[] configFiles = {
      "bullet.yaml", "enemy.yaml", "entity.yaml", "game.yaml", "prop.yaml", "ui.yaml",
    };
    Yaml yaml = new Yaml();
    Map<String, Object> data;
    InputStream is;
    for (String file : configFiles) {
      is = getClass().getClassLoader().getResourceAsStream("config/" + file);
      data = yaml.load(is);
      config.put(file.substring(0, file.lastIndexOf('.')), data);
    }
  }

  /**
   * Get the configuration map.
   *
   * @param key the filename without extension.
   * @return corresponding {@code Map}
   * @throws NoSuchElementException if key does not exist
   */
  public Map<String, Object> getConfigMapByKey(String key) {
    if (!config.containsKey(key)) {
      throw new NoSuchElementException("Config key not found: " + key);
    }
    return config.get(key);
  }
}
