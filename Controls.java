package othello;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


/*
 * Controls sets up the GUI for the game menu, allowing the user to pick the
 * game modes and to start and track games. Controls holds a one-way reference
 * to the HumanPlayer, so it can control the HumanPlayer's player settings.
 */
public class Controls {

  private Board board;
  private VBox controlsPane;
  private Label whiteScore;
  private Label blackScore;
  private Label whosTurn;
  private Pane pane;
  private Referee referee;
  private boolean finishedGame;
  private SetupGame game;
  private ColorPicker borderChanger;

  // Arrays for player buttons. Each button is checked to see if it is
  // selected when the user starts each game.
  private RadioButton[][] playerButtons;

  public Controls(Board othello, Pane pane) {
    this.board = othello;
    this.pane = pane;
    this.game = new SetupGame(this, this.board);
    this.finishedGame = false;
    this.controlsPane = new VBox();
    this.controlsPane.setPadding(new Insets(10));
    this.controlsPane.setSpacing(30);
    this.controlsPane.setAlignment(Pos.CENTER);

    this.setupInstructions();
    this.setupMenu();
    this.setupGameButtons();
  }

  public Pane getPane() {
    return this.controlsPane;
  }

  private void setupInstructions() {
    Label instructionsLabel = new Label(
        "Select options, then press Apply Settings");
    this.controlsPane.getChildren().add(instructionsLabel);
  }

  /*
   * Sets up the two halves of the player mode menu.
   */
  private void setupMenu() {
    HBox scoreLabels = new HBox();
    this.whiteScore = new Label("White: 2");
    this.blackScore = new Label("Black: 2");
    scoreLabels.setSpacing(30);
    scoreLabels.setAlignment(Pos.CENTER);
    scoreLabels.getChildren().addAll(this.whiteScore, this.blackScore);
    this.whosTurn = new Label( "Black's Turn to Move");

    this.playerButtons = new RadioButton[2][4];

    this.borderChanger = new ColorPicker();
    this.borderChanger.setFocusTraversable(false);
    this.borderChanger.setOnAction((ActionEvent e) -> {this.changeColor();});

    HBox playersMenu = new HBox();
    playersMenu.setSpacing(10);
    playersMenu.setAlignment(Pos.CENTER);
    playersMenu.getChildren().addAll(this.playerMenu(Constants.WHITE),
        this.playerMenu(Constants.BLACK));

    this.controlsPane.getChildren().addAll(scoreLabels, this.borderChanger, this.whosTurn, playersMenu);
  }

  /*
   * Provides the menu for each player mode.
   */
  private VBox playerMenu(int player) {

    VBox playerMenu = new VBox();
    playerMenu.setPrefWidth(Constants.CONTROLS_PANE_WIDTH / 2);
    playerMenu.setSpacing(10);
    playerMenu.setAlignment(Pos.CENTER);

    // Player color.
    String playerColor = "White";
    if (player == Constants.BLACK) {
      playerColor = "Black";
    }
    Label playerName = new Label(playerColor);

    // Radio button group for player mode.
    ToggleGroup toggleGroup = new ToggleGroup();

    // Human player.
    RadioButton humanButton = new RadioButton("Human         ");
    humanButton.setToggleGroup(toggleGroup);
    humanButton.setSelected(true);
    this.playerButtons[player][0] = humanButton;

    // Computer Players.
    for (int i = 1; i < 4; i++) {
      RadioButton computerButton = new RadioButton("Computer " + i + "  ");
      computerButton.setToggleGroup(toggleGroup);
      this.playerButtons[player][i] = computerButton;
    }


    // Visually add the player mode menu.
    playerMenu.getChildren().add(playerName);
    for (RadioButton rb : this.playerButtons[player]) {
      playerMenu.getChildren().add(rb);
    }

    return playerMenu;
  }

  private void setupGameButtons() {
    Button applySettingsButton = new Button("Apply Settings");
    applySettingsButton.setOnAction((ActionEvent e)->this.applySettings(e));
    applySettingsButton.setFocusTraversable(false);

    Button resetButton = new Button("Reset");
    resetButton.setOnAction((ActionEvent e)-> this.resetHandler());
    resetButton.setFocusTraversable(false);

    Button quitButton = new Button("Quit");
    quitButton.setOnAction((ActionEvent e)->Platform.exit());
    quitButton.setFocusTraversable(false);

    this.controlsPane.getChildren().addAll(applySettingsButton, resetButton,
        quitButton);
  }

  /*
   * Handler for Apply Settings button.
   */

    public void applySettings(ActionEvent e) {

      // Determine game play mode for each player.
      int whitePlayerMode = 0;
      int blackPlayerMode = 0;
      for (int player = 0; player < 2; player++) {
        for (int mode = 0; mode < 4; mode++) {
          if (this.playerButtons[player][mode].isSelected()) {
            if (player == Constants.WHITE) {
              whitePlayerMode = mode;
            } else {
              blackPlayerMode = mode;
            }
          }
        }
        if (this.game != null) {
        this.game.startGame(whitePlayerMode, blackPlayerMode);
        }
      }
  }

  /*
   * This method resets the game.
   */
  public void resetHandler(){
      if (this.board != null) {
        this.board = null;
      }
      if (this.game != null) {
        this.game = null;
      }
      this.board = new Board(this.pane);
      this.game = new SetupGame(this, this.board);
    this.resetLabels();
  }

  /*
   * This method resets the Labels.
   */
    public void resetLabels() {
      this.whosTurn.setText("Black's Turn to Move");
      this.whiteScore.setText("White: 2");
      this.blackScore.setText("Black: 2");
      if (this.referee != null) {
        this.referee.stopTimeline();
      }
    }

    /*
     * This method updates the score and turn Labels.
     */
    public void updateLabels() {
      if (!this.finishedGame) {
        this.whosTurn.setText(this.referee.getTurn() + "'s Turn to Move");
      }
      this.whiteScore.setText("White: " + this.board.getWhiteScore());
      this.blackScore.setText("Black: " + this.board.getBlackScore());
    }

    /*
     * This method sets the whosTurn Label to display the winner.
     */
    public void setWinner() {
      this.finishedGame = true;
      this.whosTurn.setText(this.board.getWinner() + "!!! GAME OVER!");
    }

  /*
   * This setter method handles two-way association between
   * the controls and the referee.
   */
    public void setReferee(Referee referee) {
      this.referee = referee;
    }

  /**
   * This is the changeColor method. Changes the color of the border.
   */
  public void changeColor() {
    Paint borderColor = this.borderChanger.getValue();
    this.board.changeBorder(borderColor);
  }
}
