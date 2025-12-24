package org.thunderfighter.ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.thunderfighter.core.abstractor.AbstractPlayerAircraft;

// Keyboard input controller for player aircraft, enable operations based on key presses

public class KeyboardController {
    private final AbstractPlayerAircraft player;

    public KeyboardController(AbstractPlayerAircraft player) {
        this.player = player;
    }
    // create a keyboard controller for the player aircraft

    public void operation(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                switch (code) {
                    case W, UP -> player.setUp(true);
                    case S, DOWN -> player.setDown(true);
                    case A, LEFT -> player.setLeft(true);
                    case D, RIGHT -> player.setRight(true);
                    case F -> player.setAutoMode(!player.isAutoMode()); // F for mode transition
                    case SPACE -> player.setShooting(true);
                    default -> {}
                }
            }
        });
        //When users press the keyboard, operate the player aircraft

        scene.setOnKeyReleased(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                switch (code) {
                    case W, UP -> player.setUp(false);
                    case S, DOWN -> player.setDown(false);
                    case A, LEFT -> player.setLeft(false);
                    case D, RIGHT -> player.setRight(false);
                    case SPACE -> player.setShooting(false);
                    default -> {}
                }
            }
        });
        // when users release the keyboard, stop the operation of the player aircraft
    }
    // Enable users to operate by the keyboard

    /*
    Notes:
    The reason why I write the same method twice is to realize two operations:
    Control the player aircraft when users press the keyboard
    Stop controlling the player aircraft when users release the keyboard
    */
}
