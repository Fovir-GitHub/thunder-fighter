package org.thunderfighter.ui;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.thunderfighter.core.abstractor.AbstractUiMenu;

public class UiMenu extends AbstractUiMenu {
    public Button startButton = new Button("Start Game");
    public Button historyButton = new Button("History Score");
    public Button aboutButton = new Button("About");

    private final UiOverlay overlay;

    public UiMenu(UiOverlay overlay) {
        this.overlay = overlay;

        Text title = new Text("Thunder Fighter");
        this.setSpacing(20);
        this.getChildren().addAll(title, startButton, historyButton, aboutButton);
        this.setVisible(true);

        startButton.setOnAction(e -> {
            this.hideMenu();
            overlay.showMenu();
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
