// src/main/java/org/thunderfighter/ui/KeyboardController.java

package org.thunderfighter.ui;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;
import org.thunderfighter.game.Game;

// Keyboard input controller for player aircraft, enable operations based on key presses
public class KeyboardController {

  private AbstractPlayerAircraft player;
  private final Game game;

  public KeyboardController(AbstractPlayerAircraft player, Game game) {
    this.player = player;
    this.game = game;
  } // create a keyboard controller for the player aircraft

  public void setPlayer(AbstractPlayerAircraft player) {
    this.player = player;
  }

  public void operation(Scene scene) {

    // Handle key pressed events
    scene.setOnKeyPressed(
        event -> {
          KeyCode code = event.getCode();
          switch (code) {
            case W, UP -> player.setUp(true);
            case S, DOWN -> player.setDown(true);
            case A, LEFT -> player.setLeft(true);
            case D, RIGHT -> player.setRight(true);
            case SPACE -> {
              player.setShooting(true);
              event.consume();
            }
            case P -> {
              // Pause/resume is driven by the global game state.
              game.togglePause();
              event.consume();
            }
            default -> {}
          }
        });
    // When users press the key board, operate the player aircraft
    // W, A, S, D or Arrow Keys to move the aircraft
    // Space to shoot

    scene.setOnKeyReleased(
        event -> {
          KeyCode code = event.getCode();
          switch (code) {
            case W, UP -> player.setUp(false);
            case S, DOWN -> player.setDown(false);
            case A, LEFT -> player.setLeft(false);
            case D, RIGHT -> player.setRight(false);
            case SPACE -> {
              player.setShooting(false);
              event.consume();
            }
            default -> {}
          }
        });
    // when users release the key board, stop the operation of the player aircraft
  }
  // Enable users to operate by the keyboard

  /* Notes:
  The reason why I write the same method twice is to realize two operations:
  Control the player aircraft when users press the key board
  Stop controlling the player aircraft when users release the key board
  */
}
