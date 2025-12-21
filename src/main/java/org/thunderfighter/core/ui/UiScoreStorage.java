package org.thunderfighter.core.ui;

import java.util.List;
/*
 The interface for score storage management.
 Design notes:
  1. readScores() reads the historical scores.
  2. addScore(int score) adds a new score.
  3. Implementers can define their own storage mechanism (e.g., file, database, in-memory).
  4. Scores are represented as a list of integers.
 */
public interface UiScoreStorage {

  // read historical scores
  List<Integer> readScores();

  // add a new score
  void addScore(int score);
}
