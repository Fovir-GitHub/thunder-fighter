package org.thunderfighter.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.thunderfighter.utils.AppDataDirectory;

public class UiScoreStorage {
  private static final List<Integer> scores = new ArrayList<>();
  private static final String fileName = "score.txt";
  private static final Path dataFile =
      Paths.get(AppDataDirectory.getAppDataDirectory().toString(), fileName);

  public static void addScore(int score) {
    scores.add(score);
  } // The method to add a new score to the storage

  public static void sortScoreDescending() {
    scores.sort(
        (a, b) -> {
          return b - a;
        });
  }

  public static List<Integer> getScores() {
    return new ArrayList<>(scores);
  } // The method to get a copy of the score list

  public static String getFormattedScores() {
    if (scores.isEmpty()) {
      return "No scores yet!";
    }

    sortScoreDescending();
    StringBuilder string_builder = new StringBuilder();
    int i = 1;

    for (int score : scores) {
      string_builder.append(i + ". " + score + "\n");
      i++;
    } // Format scores as a numbered list

    return string_builder.toString();
  }

  public static void showScoreDialog() {
    UiDialog.showInfoDialog("History Score", getFormattedScores());
  } // Show the score dialog with formatted scores

  public static void readFromFile() {
    if (!Files.exists(dataFile)) {
      return;
    }

    try {
      Scanner scanner = new Scanner(dataFile);
      int score = 0;
      scores.clear();
      while (scanner.hasNextInt()) {
        score = scanner.nextInt();
        scores.add(score);
      }
      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeToFile() {
    if (!Files.exists(dataFile)) {
      AppDataDirectory.createFile(fileName);
    }

    try {
      PrintWriter printWriter = new PrintWriter(dataFile.toString());
      sortScoreDescending();
      for (int score : scores) {
        printWriter.println(score);
      }
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
