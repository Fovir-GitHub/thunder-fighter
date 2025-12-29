// src/main/java/org/thunderfighter/ui/UiMenu.java

package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant;

/*
 * UiMenu class is responsible for the main menu of the Thunder Fighter game.
 * This class extends AbstractUiMenu to implement the main menu UI.
 * In this class, we define buttons for starting the game, viewing history scores, about information, and game rules.
 * Therefore, we can reuse this class to create and manage the main menu of the game
 */
public class UiMenu extends AbstractUiMenu {
  // The element of the main menu
  public final Text title = new Text("Thunder Fighter");
  public final Button startButton = new Button("Start Game");
  public final Button historyButton = new Button("History Score");
  public final Button aboutButton = new Button("About");
  public final Button ruleButton = new Button("Game Rule");

  // Initialize the main menu
  public UiMenu(Game game, UiOverlay overlay) {
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // make the menu cover the whole window
    this.setPickOnBounds(true);

    this.setAlignment(Pos.CENTER);
    this.setSpacing(20); // set the spacing between elements in the menu

    String imagePath = getClass().getResource("/images/Background/log_in_bg.png").toExternalForm();
    this.setStyle(
        "-fx-background-image: url('"
            + imagePath
            + "');"
            + "-fx-background-size: cover;"
            + "-fx-background-position: center;"
            + "-fx-background-repeat: no-repeat;"); // add background image to the menu

    this.title.setFont(Font.font(30));

    createButton(startButton);
    createButton(historyButton);
    createButton(ruleButton);
    createButton(aboutButton);
    // Add all elements to the menu
    this.getChildren().setAll(title, startButton, historyButton, ruleButton, aboutButton);

    showMenu(); // show the menu

    // set the action for each button in the menu
    startButton.setOnAction(
        e -> {
          game.setGameState(Constant.GAME_STATE.RUNNING);
          game.start();
        }); // control the game start, using the start method in the Game class

    historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
    ruleButton.setOnAction(e -> UiDialog.showRuleDialog());
    aboutButton.setOnAction(e -> UiDialog.showAboutDialog());
  }

  private void createButton(Button button) {
    button.setPrefWidth(150);
    button.setPrefHeight(40);
    button.setFont(Font.font(15));
  } // the basic style for buttons

  @Override
  public void showMenu() {
    this.setVisible(true);
    this.setManaged(true);
  } // show the menu

  @Override
  public void hideMenu() {
    this.setVisible(false);
    this.setManaged(false);
  } // hide the menu
}
