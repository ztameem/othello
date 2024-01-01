package othello;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/*
 * This is the HumanPlayer class. It implements the Player
 * interface. It calls methods to highlight available moves
 * and uses user clicks to place pieces down.
 */
public class HumanPlayer implements Player {
    private final Board gameBoard;
    private final SmartSquare[][] board;
    private final Paint playerColor;
    private Referee referee;
    private final Move lastMove;

    /*
     * This constructor instantiates the instance variables above and
     * either sets them equal to what was passed through or creates
     * a new instance of itself.
     */
    public HumanPlayer(Board board, Paint playerColor) {
        this.playerColor = playerColor;
        this.gameBoard = board;
        this.board = board.getBoard();
        this.lastMove = new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.ARBITRARY);
    }

    /*
     * The HumanPlayer's makeMove method relies on
     * user clicks to place a piece down and checks
     * for if that move is valid. It also calls for
     * highlighting pieces and ending its turn.
     */
    @Override
    public void makeMove() {
        if (this.lastMove.getI() != 0) {
            this.board[this.lastMove.getI()][this.lastMove.getJ()].hideLastMove();
        }
        this.gameBoard.setHighlightedPieces(this.playerColor);
        this.gameBoard.getPane().setOnMouseClicked(ActionEvent -> {
            int i = this.getI(ActionEvent.getY());
            int j = this.getJ(ActionEvent.getX());
            if (j < 10) {
                if (this.gameBoard.checkMoveValidity(i, j, this.playerColor, true, false)) {

                    if (this.playerColor == Color.WHITE) {
                        this.board[i][j].setWhite();
                        this.board[i][j].showLastMove(Color.DARKGRAY);
                    } else {
                        this.board[i][j].setBlack();
                        this.board[i][j].showLastMove(Color.GRAY);
                    }
                    this.lastMove.setCoords(i, j);
                    this.gameBoard.unHighlightMoves();
                    this.endTurn();
                }
            }
        });
    }

    /*
     * This method ends the player's turn.
     */
    public void endTurn() {
        this.gameBoard.getPane().setOnMouseClicked(null);
        this.referee.turnEnd();
    }

    /*
     * This getter method gets the row value of
     * the click.
     */
    public int getI(double y) {
        int yCord = (int) y;
        return yCord / Constants.SQUARE_WIDTH;
    }

    /*
     * This getter method gets the column value of
     * the click.
     */
    public int getJ(double x) {
        int xCord = (int) x;
        return xCord / Constants.SQUARE_WIDTH;
    }

    /*
     * This setter method handles two-way association between
     * the players and the referee.
     */
    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    /*
     * This getter method gets color of the player.
     */
    public Paint getPlayerColor() {
        return this.playerColor;
    }

}
