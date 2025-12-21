package org.thunderfighter.core.ui;

/*
 Design notes:
  1. the interface is used to represent a Dialog UI component.
  2. it only requires a show() method to display the dialog.
  3. implementers can define their own dialog behavior.
 */
public interface UiDialog {
//show the dialog
  void show();
}
