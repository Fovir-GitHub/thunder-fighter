package org.thunderfighter.core.ui;

import java.util.List;

//score storage
public abstract class UiScoreStorage {

  public final List<Integer> readScores() {
    return onReadScores();
  }

  public final void addScore(int score) {
    onAddScore(score);
  }

  protected abstract List<Integer> onReadScores();
  protected abstract void onAddScore(int score);
}
