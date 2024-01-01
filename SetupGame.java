package othello;

import javafx.scene.paint.Color;

/*
 * This class creates new players and referee for the game
 * in response to apply settings being pressed.
 */
public class SetupGame {
    private final Board boardClass;
    private final Controls controls;

    /*
     * The constructor sets the controls and board equal to what
     * was passed through.
     */
    public SetupGame(Controls controls, Board board) {
        this.controls = controls;
        this.boardClass = board;
    }

    /*
     * This method starts the actual game by creating the new Players
     * and the new Referee.
     */
    public void startGame(int whitePlayerMode, int blackPlayerMode) {

        Player whitePlayer;
        if (whitePlayerMode == 0) {
            whitePlayer = new HumanPlayer(this.boardClass, Color.WHITE);
        } else {
            whitePlayer = new ComputerPlayer(this.boardClass, Color.WHITE, whitePlayerMode);
        }

        Player blackPlayer;
        if (blackPlayerMode == 0) {
            blackPlayer = new HumanPlayer(this.boardClass, Color.BLACK);
        } else {
            blackPlayer = new ComputerPlayer(this.boardClass, Color.BLACK, blackPlayerMode);
        }
        Referee referee = new Referee(this.controls, whitePlayer, blackPlayer, this.boardClass);
        whitePlayer.setReferee(referee);
        blackPlayer.setReferee(referee);

    }
}