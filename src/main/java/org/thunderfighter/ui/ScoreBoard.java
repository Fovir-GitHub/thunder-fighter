// src/main/java/org/thunderfighter/ui/ScoreBoard.java

package org.thunderfighter.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.thunderfighter.core.manager.ScoreManager;
import org.thunderfighter.game.aircraft.player.PlayerAircraft;

/** Board to display score and lives. */
public class ScoreBoard {

  private VBox scoreBoardContainer;
  private Label scoreLabel;
  private Label livesLabel;
  private PlayerAircraft playerAircraft;

  /**
   * Constructor of {@code ScoreBoard}.
   *
   * @param root The root pane.
   * @param playerAircraft Player's aircraft to display the lives.
   */
  public ScoreBoard(final StackPane root, final PlayerAircraft playerAircraft) {
    this.playerAircraft = playerAircraft;
    initScoreBoardContainer();

    root.getChildren().add(scoreBoardContainer);
  }

  public void setPlayerAircraft(final PlayerAircraft playerAircraft) {
    this.playerAircraft = playerAircraft;
  }

  public void setVisible(final boolean visible) {
    scoreBoardContainer.setVisible(visible);
  }

  /** Update the score board. */
  public void update() {
    scoreLabel.setText("Score: " + ScoreManager.getInstance().getScore());
    livesLabel.setText("Lives: " + playerAircraft.getHp());
  }

  /** Initialize the score board and set its style. */
  private void initScoreBoardContainer() {
    scoreBoardContainer = new VBox(12);
    scoreBoardContainer.setAlignment(Pos.TOP_RIGHT);
    StackPane.setAlignment(scoreBoardContainer, Pos.TOP_RIGHT);
    StackPane.setMargin(scoreBoardContainer, new Insets(20, 20, 0, 0));
    scoreBoardContainer.setPrefWidth(200);
    scoreBoardContainer.setMaxWidth(200);
    scoreBoardContainer.setMaxHeight(100);
    final Color bgColor = Color.rgb(0, 0, 0, 0.6);
    final CornerRadii radii = new CornerRadii(15);
    scoreBoardContainer.setBackground(
        new Background(new BackgroundFill(bgColor, radii, Insets.EMPTY)));
    scoreBoardContainer.setPadding(new Insets(15));
    scoreBoardContainer.setMouseTransparent(true);
    final String labelStyle =
        "-fx-font-family: 'Arial Black'; -fx-font-size: 20px; -fx-text-fill: white; -fx-effect:"
            + " dropshadow(gaussian, black, 4, 0, 2, 2);";
    scoreLabel = new Label("Score: " + ScoreManager.getInstance().getScore());
    scoreLabel.setStyle(labelStyle);

    livesLabel = new Label("Lives: " + playerAircraft.getHp());
    scoreBoardContainer.getChildren().addAll(scoreLabel, livesLabel);
    livesLabel.setStyle(labelStyle);
  }
}
