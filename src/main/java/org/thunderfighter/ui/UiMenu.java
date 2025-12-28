package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant;

public class UiMenu extends AbstractUiMenu {

  public final Text title = new Text("Thunder Fighter");
  public final Button startButton = new Button("Start Game");
  public final Button historyButton = new Button("History Score");
  public final Button aboutButton = new Button("About");
  public final Button ruleButton = new Button("Game Rule");

  public UiMenu(Game game, UiOverlay overlay) {
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.setPickOnBounds(true);

    this.setAlignment(Pos.CENTER);
    this.setSpacing(20);

    this.title.setFont(Font.font(30));

    createButton(startButton);
    createButton(historyButton);
    createButton(ruleButton);
    createButton(aboutButton);

    this.getChildren().setAll(title, startButton, historyButton, ruleButton, aboutButton);

    showMenu();

    startButton.setOnAction(
        e -> {
          game.setGameState(Constant.GAME_STATE.RUNNING);
          game.start();
        });

    historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
    ruleButton.setOnAction(e -> UiDialog.showRuleDialog());
    aboutButton.setOnAction(e -> UiDialog.showAboutDialog());
  }

  private void createButton(Button button) {
    button.setPrefWidth(150);
    button.setPrefHeight(40);
    button.setFont(Font.font(15));
  }

  @Override
  public void showMenu() {
    this.setVisible(true);
    this.setManaged(true);
  }

  @Override
  public void hideMenu() {
    this.setVisible(false);
    this.setManaged(false);
  }
}
