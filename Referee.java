package othello;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/*
 * This class handles the logic behind the game and
 * turn taking through a Timeline.
 */
public class Referee {
    private Timeline timeline;
    private final Board board;
    private final Controls controls;
    private Player currPlayer;
    private Player nextPlayer;

    /*
     * The constructor sets the instance variables equal to what was passed through.
     * It also calls the timeline starting method.
     */
    public Referee(Controls controls, Player playerWhite, Player playerBlack, Board board) {
        this.currPlayer = playerWhite;
        this.nextPlayer = playerBlack;
        this.board = board;
        this.controls = controls;
        this.controls.setReferee(this);
        this.turnTaking();
    }

    /*
     * This method starts the turn taking for the game by
     * creating a timeline. This helps to delay computer moves.
     */
    public void turnTaking() {
        KeyFrame kf = new KeyFrame(Duration.seconds(.01), (ActionEvent e) -> this.updateTimeline());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /*
     * This method is called on every tick of the timeline.
     * It stops the timeline and calls the next player to
     * make a move.
     */
    public void updateTimeline() {
        this.timeline.stop();
        Player tempPlayer = this.currPlayer;
        this.currPlayer = this.nextPlayer;
        this.nextPlayer = tempPlayer;
        this.currPlayer.makeMove();
    }

    /*
     * This method calls to set scores and checks for game over. Then it
     * starts the timeline/game again.
     */
    public void turnEnd() {
        this.setScores();
        this.board.unHighlightMoves();
        if (this.board.checkGameOver(false, this.currPlayer.getPlayerColor(), this.nextPlayer.getPlayerColor())) {
            this.controls.setWinner();
            this.timeline.stop();
        } else {
            this.timeline.play();
        }
    }

    /*
     * This method sets the scores and calls for the controls to
     * update the labels.
     */
    public void setScores() {
        this.board.setScores();
        this.controls.updateLabels();
    }

    /*
     * This method stops the timeline.
     */
    public void stopTimeline() {
        this.timeline.stop();
    }

    /*
     * This method gets whose current turn it is so that the
     * Label/controls know.
     */
    public String getTurn() {
        if (this.currPlayer.getPlayerColor() == Color.BLACK) {
            return "White";
        } else {
            return "Black";
        }
    }
}
