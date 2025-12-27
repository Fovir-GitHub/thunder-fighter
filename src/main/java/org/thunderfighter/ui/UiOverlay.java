package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;

public class UiOverlay extends AbstractUiMenu {
    public final Text title = new Text("Paused");
    public final Button continueButton = new Button("Continue");
    public final Button historyButton = new Button("History Score");
    public final Button aboutButton = new Button("About");
    public final Button ruleButton = new Button("Game Rule");

    public UiOverlay() {
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setPickOnBounds(true);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        this.title.setFont(Font.font(30));

        createButton(continueButton);
        createButton(historyButton);
        createButton(ruleButton);
        createButton(aboutButton);

        this.getChildren().setAll(title, continueButton, historyButton, ruleButton, aboutButton);

        hideMenu();

        continueButton.setOnAction(e -> this.hideMenu());
        historyButton.setOnAction(e -> UiScoreStorage.showScoreDialog());
        ruleButton.setOnAction(e -> UiDialog.showRuleDialog());
        aboutButton.setOnAction(e -> UiDialog.showAboutDialog());
    }

    private void createButton(Button button) {
        button.setFont(Font.font(20));
        button.setPrefWidth(220);
        button.setPrefHeight(40);
        button.setFocusTraversable(true);
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
