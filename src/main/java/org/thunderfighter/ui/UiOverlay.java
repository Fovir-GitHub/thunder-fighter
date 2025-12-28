package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;
import org.thunderfighter.game.Game;
import org.thunderfighter.utils.Constant;

public class UiOverlay extends AbstractUiMenu {
    public final Text title = new Text("Paused");
    public final Button continueButton = new Button("Continue");
    public final Button historyButton = new Button("History Score");
    public final Button aboutButton = new Button("About");
    public final Button ruleButton = new Button("Game Rule");

    private final Game game;

    public UiOverlay(Game game) {
        this.game = game;
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

        // Resume the game instead of only hiding the overlay.
        // If we only hide the overlay, the game state remains PAUSE and the overlay will
        // be shown again on the next frame.
        continueButton.setOnAction(e -> {
            game.setGameState(Constant.GAME_STATE.RUNNING);
            this.hideMenu();
        });
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

    @Override
    public void showMenu() {
        setVisible(true);
        setMouseTransparent(false);;
        toFront();
    }

    @Override
    public void hideMenu() {
        setVisible(false);
        setMouseTransparent(true);
    }
}
