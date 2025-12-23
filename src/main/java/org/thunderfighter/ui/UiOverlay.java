package org.thunderfighter.core.ui;

import javafx.scene.layout.Pane;

/*
 Overlay class is used to generate the Pause / Settings UI
 (which are overlaid on top of the game scene).
 Design notes:
 1. visibleFlag shows the logical visibility state
 2. root.setVisible() shows/hides the actual UI
 3. show/hide should update both states
 4. toggle() switches the visible states
 5. onVisibleChanged() is an optional hook for subclasses
 */
public abstract class UiOverlay {

  protected boolean visibleFlag = false;//the visible state of the Overlay
  protected Pane root;

  //create Overlay root node
  public final Pane build() {
    root = createOverlay();
    root.setVisible(visibleFlag);
    onBuild(root);
    return root;
  }

  // show the Overlay
  public final void show() {
    visibleFlag = true;
    if (root != null) root.setVisible(true);
    onVisibleChanged(true);
  }

  // hide the Overlay
  public final void hide() {
    visibleFlag = false;
    if (root != null) root.setVisible(false);
    onVisibleChanged(false);
  }

  //change the current visible state
  public final void toggle() {
    if (visibleFlag) hide();
    else show();
  }

  //get the current visible state
  public final boolean isVisible() {
    return visibleFlag;
  }

  // the subclass should create and return the Overlay root Pane.
  protected abstract Pane createOverlay();
  protected void onBuild(Pane root) {}
  protected void onVisibleChanged(boolean visible) {}
}
