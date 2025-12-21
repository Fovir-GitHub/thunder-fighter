package org.thunderfighter.core.abstractor;
import org.thunderfighter.core.ui.UiMenu;
/*
   Define the init() workflow for Menu UI components.
   init() = createLayout → bindActions → onReady
*/
public abstract class AbstractUiMenu implements UiMenu {

 //The final init method defines the workflow.
  @Override
  public final void init() {
    createLayout();
    bindActions();
    onReady();
  }

  // The subclass should implement the actual layout creation.
  protected abstract void createLayout();
  // The subclass should implement the actual action binding.
  protected void bindActions() {};
  // The subclass should implement the actual ready hook.
  protected void onReady() {};
}
