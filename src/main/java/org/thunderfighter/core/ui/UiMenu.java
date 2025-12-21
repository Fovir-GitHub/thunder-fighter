package org.thunderfighter.core.ui;

// UI: Menu (Start/Pause)
public abstract class UiMenu {

  public final void init() {
    createLayout();
    bindActions();
    onReady();
  }

  protected abstract void createLayout();
  protected void bindActions(){};
  protected void onReady(){};
}
