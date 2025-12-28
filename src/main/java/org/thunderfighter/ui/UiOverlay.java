package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant;

/*
 Create 3 modes:
1. PAUSE: resume game
2. SUCCESS: show victory and offer back to menu button
3. FAIL: show game over and offer back to menu button
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

    // Back to menu
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
    }

    // Keep the same ordering; only toggle visibility
    this.getChildren()
        .setAll(title, continueButton, restartButton, historyButton, ruleButton, aboutButton);
  }

  @Override
  public void showMenu() {
    setVisible(true);
    setMouseTransparent(false);
    toFront();
  }

  @Override
  public void hideMenu() {
    setVisible(false);
    setMouseTransparent(true);
  }
}
