package org.thunderfighter.ui;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;

public class UiOverlay extends AbstractUiMenu {
  public Button continueButton = new Button("Continue");
  public Button historyButton = new Button("History Score");
  public Button aboutButton = new Button("About");

  public UiOverlay() {
    Text title = new Text("Game Paused");
    this.setSpacing(20);
    this.getChildren().addAll(title, continueButton, historyButton, aboutButton);
    this.setVisible(false);

    continueButton.setOnAction(e -> this.hideMenu());
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
