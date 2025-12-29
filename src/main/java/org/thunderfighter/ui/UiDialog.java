// src/main/java/org/thunderfighter/ui/UiDialog.java

package org.thunderfighter.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.thunderfighter.core.abstractor.AbstractUiDialog;

/**
 * UiDialog class for displaying various dialogs in the Thunder Fighter game.
 *
 * <ol>
 *   <li>This class will be used in UiScoreStorage, UiMenu and etc. to show dialogs.
 *   <li>In this class, we define methods to show information dialogs, about dialog, and game rule
 *       dialog.
 *   <li>Therefore, we can reuse this class to show different types of dialogs by using the methods
 *       showInfoDialog, showAboutDialog, and showRuleDialog.
 * </ol>
 */
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
  } // show generic information of dialog

  public static void showAboutDialog() {
    showInfoDialog("About", "Thunder Fighter © 2025\nAll rights reserved.");
  } // show the copyright in dialog

  public static void showRuleDialog() {
    showInfoDialog(
      "Game Rule",
      "1. Use arrow keys or WASD to move the fighter.\n"
          + "2. Press space to shoot.\n"
          + "3. Avoid enemy fire and obstacles.\n"
          + "4. Score points by destroying enemies.\n"
          + "5. When the score comes to 100, you will meet elite enemy.\n"
          + "6. The Boss will appear when the score reaches 500.\n"
          + "7. Boss has three stages:\n"
          + "in first stage, it shoots less frequently;\n"
          + "in second stage, it shoots the homing bullet;\n"
          + "in third stage, it releases fatal laser.\n"
          + "8. Defeat the Boss to win the game!");
  } // show game rule dialog
}
