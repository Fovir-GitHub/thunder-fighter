package org.thunderfighter.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.thunderfighter.core.abstractor.AbstractUiDialog;

public class UiDialog extends AbstractUiDialog {
    private final Alert alert;

    public UiDialog(String title, String message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

    @Override
    public void showDialog() {
        alert.showAndWait();
    }
        public static void showInfoDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAboutDialog() {
        showInfoDialog("About", "Thunder Fighter © 2025\nAll rights reserved.");
    }

    public static void showRuleDialog() {
        showInfoDialog("Game Rule", "1. Use arrow keys or WASD to move the fighter.\n2. Press space to shoot.\n3. Avoid enemy fire and obstacles.\n4. Score points by destroying enemies.\n5. You can pick up items to gain power-ups.");
    }
}
