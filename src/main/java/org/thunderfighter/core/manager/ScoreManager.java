// src/main/java/org/thunderfighter/core/manager/ScoreManager.java

package org.thunderfighter.core.manager;

public class ScoreManager {
  private static ScoreManager instance = new ScoreManager();
  private static int score = 0;

  private ScoreManager() {}

  public static ScoreManager getInstance() { // call method
    return instance;
  }

  public int getScore() { // get from private
    return score;
  }

  public void addScore(int s) { // accumulator
    score += s;
  }

  public void reset() { // clear the score
    score = 0;
  }
}
