package org.thunderfighter.core.abstractor;
/*
Define the show() workflow for Dialog UI components.
show() = before → onShow → after
 */

public abstract class AbstractUiDialog implements UiDialog {
  @Override
  public final void show() {
    onBeforeShow();
    onShow();
    onAfterShow();
  }


//The subclass should implement the actual show behavior.
  protected abstract void onShow();
  //before show hook
  protected void onBeforeShow(){};
  //after show hook
  protected void onAfterShow(){};
}
