// src/main/java/org/thunderfighter/ui/UiOverlay.java

package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant;

/**
 *
 *
 * <ol>
 *   Create 3 modes:
 *   <li>PAUSE: resume game
 *   <li>SUCCESS: show victory and offer back to menu button
 *   <li>FAIL: show game over and offer back to menu button
 * </ol>
 *
 * We can use the showPause, showSuccess, showFail methods to show different overlays. When users
 * key in p, we can call showPause to show the pause overlay. When users win the game, we can call
 * showSuccess to show the success overlay. When users lose the game, we can call showFail to show
 * the fail overlay.
 */
public class UiOverlay extends AbstractUiMenu {

  /** Overlay modes, deciding title text and visible buttons. */
  private enum OverlayMode {
    PAUSE,
    SUCCESS,
    FAIL
  }

  private OverlayMode mode = OverlayMode.PAUSE;

  public final Text title = new Text("Paused");

  public final Button continueButton = new Button("Continue");
  public final Button restartButton = new Button("Back to Menu");
  public final Button historyButton = new Button("History Score");
  public final Button aboutButton = new Button("About");
  public final Button ruleButton = new Button("Game Rule");

  public UiOverlay(Game game) {
    // Make overlay cover the whole scene to prevent click-through.
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.setPickOnBounds(true);

    this.setAlignment(Pos.CENTER);
    this.setSpacing(20);

    this.title.setFont(Font.font(30));

    createButton(continueButton);
    createButton(historyButton);
    createButton(restartButton);
    createButton(ruleButton);
    createButton(aboutButton);

    applyMode(OverlayMode.PAUSE);
    hideMenu();

    // Continue: only valid in PAUSE mode
    continueButton.setOnAction(
        e -> {
          game.setGameState(Constant.GAME_STATE.RUNNING);
          hideMenu();
        });

    // Back to menu(valid in SUCCESS and FAIL modes)
    restartButton.setOnAction(
        e -> {
          game.setGameState(Constant.GAME_STATE.MENU);
          hideMenu();
        }); // come back to menu

    historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
    ruleButton.setOnAction(e -> UiDialog.showRuleDialog());
    aboutButton.setOnAction(e -> UiDialog.showAboutDialog());
  }

  private void createButton(Button button) {
    button.setFont(Font.font(15));
    button.setPrefWidth(220);
    button.setPrefHeight(40);
    button.setFocusTraversable(true);
  }

  // Show pause overlay.
  // Called by Game.handlePauseState().
  public void showPause() {
    applyMode(OverlayMode.PAUSE);
    showMenu();
  }

  // Show success overlay.
  // Called by Game.handleSuccessState().
  public void showSuccess() {
    applyMode(OverlayMode.SUCCESS);
    showMenu();
  }

  // Show fail overlay.
  // Called by Game.handleFailState().
  public void showFail() {
    applyMode(OverlayMode.FAIL);
    showMenu();
  }

  // Logic to apply the mode changes to the overlay UI.
  private void applyMode(OverlayMode newMode) {
    this.mode = newMode;

    switch (mode) {
      case PAUSE -> {
        title.setText("Paused");
        continueButton.setVisible(true);
        continueButton.setManaged(true);

        restartButton.setVisible(false);
        restartButton.setManaged(false);
      }
      case SUCCESS -> {
        title.setText("Victory!");
        continueButton.setVisible(false);
        continueButton.setManaged(false);

        restartButton.setText("Back to Menu");
        restartButton.setVisible(true);
        restartButton.setManaged(true);
      }
      case FAIL -> {
        title.setText("Game Over");
        continueButton.setVisible(false);
        continueButton.setManaged(false);

        restartButton.setText("Back to Menu");
        restartButton.setVisible(true);
        restartButton.setManaged(true);
      }
      default -> {}
    } // For different game status, show different overlay

    this.getChildren()
        .setAll(title, continueButton, restartButton, historyButton, ruleButton, aboutButton);
  }

  @Override
  public void showMenu() {
    setVisible(true);
    setMouseTransparent(false);
    toFront();
  } // Show the menu overlay

  @Override
  public void hideMenu() {
    setVisible(false);
    setMouseTransparent(true);
  } // Hide the menu overlay
}
