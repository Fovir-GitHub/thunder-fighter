package org.thunderfighter.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Operation of app data directory. */
public class AppDataDirectory {

  private static final String APP_NAME = "thunder-fighter";

  /**
   * Get app data directory according to different operating systems.
   *
   * @return App data directory on different operating systems.
   */
  public static Path getAppDataDirectory() {
    String os = System.getProperty("os.name").toLowerCase();
    Path appDir;

    if (os.contains("win")) {
      appDir = Paths.get(System.getenv("APPDATA"), APP_NAME);
    } else if (os.contains("mac")) {
      appDir =
          Paths.get(System.getProperty("user.home"), "Library", "Application Support", APP_NAME);
    } else {
      String configHome = System.getenv("XDG_CONFIG_HOME");
      if (configHome == null || configHome.isEmpty()) {
        configHome = System.getProperty("user.home") + "/.config";
      }
      appDir = Paths.get(configHome, APP_NAME);
    }

    return appDir;
  }

  /**
   * Ensure the data directory of game exists under the app data directory.
   *
   * @return The path of data directory.
   */
  public static Path ensureAppDataDirectory() {
    Path dir = getAppDataDirectory();
    try {
      Files.createDirectory(dir);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dir;
  }

  /**
   * Create data file under the data file directory.
   *
   * @param fileName The name of data file.
   */
  public static void createFile(String fileName) {
    Path dir = ensureAppDataDirectory();
    Path file = dir.resolve(fileName);
    try {
      Files.writeString(file, "");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
