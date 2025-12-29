package org.thunderfighter.ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/*
Manages the main scene, including menu and overlay
1. UiSceneManager class is responsible for managing the scene of the Thunder Fighter game.
2. This class contains a StackPane as the root layout, which holds both the main menu and the overlay.
3. In this class, we define methods to toggle the overlay visibility, show and hide the menu and overlay.
4. Therefore, we can reuse this class to manage the main scene of the game by using the methods provided.
*/

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
