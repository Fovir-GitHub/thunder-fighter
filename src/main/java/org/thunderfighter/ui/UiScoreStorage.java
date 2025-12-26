package org.thunderfighter.ui;

import java.util.ArrayList;
import java.util.List;

public class UiScoreStorage {
  private static final List<Integer> scores = new ArrayList<>();

  public static void addScore(int score) {
    scores.add(score);
  }

  public static List<Integer> getScores() {
    return new ArrayList<>(scores);
  }

  public static String getFormattedScores() {
    if (scores.isEmpty()) return "No scores yet!";
    StringBuilder string_builder = new StringBuilder();
    int i = 1;

    for (int score : scores) {
      string_builder.append(i + ". " + score + "\n");
      i++;
    }

    return string_builder.toString();
  }

  public static void showScoreDialog() {
    UiDialog.showInfoDialog("History Score", getFormattedScores());
  }
}
