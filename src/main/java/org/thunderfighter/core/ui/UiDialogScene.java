package org.thunderfighter.core.ui;

// UI: Dialog (Setting)
public abstract class UiDialogScene {

  public final void show() {
    onBeforeShow();
    onShow();
    onAfterShow();
  }
  
  protected abstract void onShow();
  protected void onBeforeShow(){};
  protected void onAfterShow(){};
}