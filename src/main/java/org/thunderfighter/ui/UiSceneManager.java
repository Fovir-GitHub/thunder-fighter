package org.thunderfighter.ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

// Manages the main scene, including menu and overlay
public class UiSceneManager {
  private final StackPane root = new StackPane();
  private final Scene scene;

  private final UiMenu menu;
  private final UiOverlay overlay;

  public UiSceneManager(UiMenu menu, UiOverlay overlay) {
    this.menu = menu;
    this.overlay = overlay;
    root.getChildren().addAll(menu, overlay);
    scene = new Scene(root, 800, 600);
    overlay.hideMenu();
  } // Initialize the scene manager with menu and overlay

  public void togglePause() {
    if (overlay.isVisible()) {
      hideOverlay();
    } else {
      showOverlay();
    }
  } // Toggle the overlay visibility

  public Scene getScene() {
    return scene;
  } // Get the main scene

  public void showOverlay() {
    overlay.showMenu();
  }

  public void hideOverlay() {
    overlay.hideMenu();
  }

  public void showMenu() {
    menu.showMenu();
  }

  public void hideMenu() {
    menu.hideMenu();
  }
}
