package org.thunderfighter.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.thunderfighter.core.abstractor.AbstractUiDialog;

public class UiDialog extends AbstractUiDialog {
  private final Alert alert;

  // Create an information dialog with given title and message
  public UiDialog(String title, String message) {
    alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
  }

  @Override
  public void showDialog() {
    alert.showAndWait();
  } // Show the dialog and wait for user to close it

  public static void showInfoDialog(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  } // show generic information dialog

  public static void showAboutDialog() {
    showInfoDialog("About", "Thunder Fighter © 2025\nAll rights reserved.");
  }

  public static void showRuleDialog() {
    showInfoDialog(
        "Game Rule",
        "1. Use arrow keys or WASD to move the fighter.\n"
            + "2. Press space to shoot.\n"
            + "3. Avoid enemy fire and obstacles.\n"
            + "4. Score points by destroying enemies.\n");
  } // show game rule dialog
}
