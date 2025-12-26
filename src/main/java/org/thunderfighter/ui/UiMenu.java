package org.thunderfighter.ui;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant.GAME_STATE;

public class UiMenu extends AbstractUiMenu {
  public Button startButton = new Button("Start Game");
  public Button historyButton = new Button("History Score");
  public Button aboutButton = new Button("About");

  public UiMenu(final Game game) {
    Text title = new Text("Thunder Fighter");
    this.setSpacing(20);
    this.getChildren().addAll(title, startButton, historyButton, aboutButton);
    this.setVisible(true);

    startButton.setOnAction(
        e -> {
          this.hideMenu();
          game.setGameState(GAME_STATE.RUNNING);
        });

    historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
    aboutButton.setOnAction(e -> UiDialog.showAboutDialog());
  }

  @Override
  public void showMenu() {
    this.setVisible(true);
  }

  @Override
  public void hideMenu() {
    this.setVisible(false);
  }
}
