package org.thunderfighter.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.thunderfighter.core.manager.ScoreManager;
import org.thunderfighter.game.aircraft.player.PlayerAircraft;

/** Board to display score and lives. */
public class ScoreBoard {

  private StackPane pane;
  private Label scoreLabel;
  private Label livesLabel;
  private final PlayerAircraft playerAircraft;

  /**
   * Constructor of {@code ScoreBoard}.
   *
   * @param root The root pane.
   * @param playerAircraft Player's aircraft to display the lives.
   */
  public ScoreBoard(StackPane root, final PlayerAircraft playerAircraft) {
    this.playerAircraft = playerAircraft;
    pane = new StackPane();
    StackPane.setAlignment(pane, Pos.TOP_RIGHT);
    root.getChildren().add(pane);

    initScoreBoard();
  }

  /** Initialize the score board. */
  private void initScoreBoard() {
    pane.getChildren().clear();
    VBox vBox = new VBox(8);
    vBox.setAlignment(Pos.TOP_RIGHT);

    scoreLabel = new Label("Score: " + ScoreManager.getInstance().getScore());
    livesLabel = new Label("Lives: " + playerAircraft.getHp());

    vBox.getChildren().addAll(scoreLabel, livesLabel);
    pane.getChildren().add(vBox);
  }

  public void update() {
    scoreLabel.setText("Score: " + ScoreManager.getInstance().getScore());
    livesLabel.setText("Lives: " + playerAircraft.getHp());
  }
}
