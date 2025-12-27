package org.thunderfighter.ui;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant.GAME_STATE;

public class UiMenu extends AbstractUiMenu {
  public Text title = new Text("Thunder Fighter");
  public Button startButton = new Button("Start Game");
  public Button historyButton = new Button("History Score");
  public Button aboutButton = new Button("About");
  public Button ruleButton = new Button("Game Rule");

  public UiMenu(Game game) {
    this.title.setFont(javafx.scene.text.Font.font(30));
    this.title.setTranslateX(320);
    this.title.setTranslateY(100);

    this.startButton.setPrefWidth(100);
    this.startButton.setPrefHeight(30);
    this.startButton.setTranslateX(350);
    this.startButton.setTranslateY(200);
    this.startButton.setFont(javafx.scene.text.Font.font(20));

    this.historyButton.setPrefWidth(100);
    this.historyButton.setPrefHeight(30);
    this.historyButton.setTranslateX(350);
    this.historyButton.setTranslateY(250);
    this.historyButton.setFont(javafx.scene.text.Font.font(20));

    this.ruleButton.setPrefWidth(100);
    this.ruleButton.setPrefHeight(30);
    this.ruleButton.setTranslateX(350);
    this.ruleButton.setTranslateY(300);
    this.ruleButton.setFont(javafx.scene.text.Font.font(20));

    this.aboutButton.setPrefWidth(100);
    this.aboutButton.setPrefHeight(30);
    this.aboutButton.setTranslateX(350);
    this.aboutButton.setTranslateY(350);
    this.aboutButton.setFont(javafx.scene.text.Font.font(20));

    this.setSpacing(20);
    this.getChildren().addAll(title, startButton, historyButton, ruleButton, aboutButton);
    this.setVisible(true);

    startButton.setOnAction(
        e -> {
          this.hideMenu();
          game.setGameState(GAME_STATE.RUNNING);
        });

    historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
    ruleButton.setOnAction(e -> UiDialog.showRuleDialog());
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
