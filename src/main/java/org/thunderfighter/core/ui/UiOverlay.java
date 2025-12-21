package org.thunderfighter.core.ui;

import javafx.scene.layout.Pane;

// Pause Overlay
public abstract class UiOverlay{
  protected boolean visible_flag = false;
  protected Pane root;

  public final Pane build(){
    root = createOverlay();
    root.setVisible(visible_flag);
    onBuild(root);
    return root;
  }

  public final void show(){
    visible_flag = true;
    if (root != null) root.setVisible(true);
    onVisibleChanged(true);
  }

  public final void hide(){
    visible_flag = false;
    if (root != null) root.setVisible(false);
    onVisibleChanged(false);
  }

  public final void toggle(){
    if (visible_flag) hide();
    else show();
  }

  public final boolean isVisible(){
    return visible_flag;
  }

  protected abstract Pane createOverlay();
  protected void onBuild(Pane root){};
  protected void onVisibleChanged(boolean visible){};
}
