package othello;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/*
 * This is the ComputerPlayer class. It implements the Player
 * interface. It uses a miniMax algorithm to play the best move,
 * depending on its intelligence.
 */
public class ComputerPlayer implements Player {
    private final Board gameBoard;
    private final SmartSquare[][] board;
    private final Paint playerColor;
    private Referee referee;
    private final int intelligence;
    private final ArrayList<Move> bestMoves;
    private final Move lastMove;

    /*
     * This constructor instantiates the instance variables above and
     * either sets them equal to what was passed through or creates
     * a new instance of itself.
     */
    public ComputerPlayer(Board board, Paint playerColor, int intelligence) {
        this.playerColor = playerColor;
        this.intelligence = intelligence;
        this.gameBoard = board;
        this.bestMoves = new ArrayList<>();
        this.lastMove = new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.ARBITRARY);
        this.board = board.getBoard();
    }

    /*
     * The ComputerPlayer's makeMove method finds the
     * best move using the miniMax algorithm then places
     * a piece at that location and flips necessary pieces.
     */
    @Override
    public void makeMove() {
        if (this.lastMove.getI() != 0) {
            this.board[this.lastMove.getI()][this.lastMove.getJ()].hideLastMove();
        }
        this.gameBoard.unHighlightMoves();
        Move bestMove = this.getBestMove(new Board(this.gameBoard), this.intelligence, this.playerColor);
        int i = bestMove.getI();
        int j = bestMove.getJ();
        if (i > 0 && i < 9 && j > 0 && j < 9) {
            this.gameBoard.checkMoveValidity(i, j, this.playerColor, true, true);
            if (this.board[i][j] != null) {
                if (this.playerColor == Color.WHITE) {
                    this.board[i][j].setWhite();
                    this.board[i][j].showLastMove(Color.DARKGRAY);
                } else {
                    this.board[i][j].setBlack();
                    this.board[i][j].showLastMove(Color.GRAY);
                }
            }
            this.lastMove.setCoords(i, j);
        }
        this.endTurn();
    }

    /*
     * This method is the miniMax algorithm. It returns a Move
     * that is deems as best depending on its intelligence.
     */
    public Move getBestMove(Board board, int intelligence, Paint currentPlayer) {
        // sets opposing player color
        Paint oppPlayer;
        if (currentPlayer == Color.WHITE) {
            oppPlayer = Color.BLACK;
        } else {
            oppPlayer = Color.WHITE;
        }

        // checks for game over and returns high, low, or neutral move depending on outcome
        if (board.checkGameOver(false, currentPlayer, oppPlayer)) {
            if (board.getWhiteScore() == board.getBlackScore()) {
                return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.NEUTRAL_VAL);
            }
            if (currentPlayer == Color.WHITE) {
                if (board.getWhiteScore() > board.getBlackScore()) {
                    return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.HIGH_VAL);
                } else if (board.getBlackScore() > board.getWhiteScore()) {
                    return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.LOW_VAL);
                }
            } else {
                if (board.getWhiteScore() < board.getBlackScore()) {
                    return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.HIGH_VAL);
                } else if (board.getBlackScore() < board.getWhiteScore()) {
                    return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.LOW_VAL);
                }
            }
        }

        // checks if there are no moves and returns either a low value move or
        // does recursion through the algorithm again, depending on intelligence.
        if (board.areNoMoves(currentPlayer, false)) {
            if (intelligence == 1) {
                return new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.LOW_VAL);
            } else {
                return this.getBestMove(board, intelligence - 1, oppPlayer).setNegativeValue();
            }
        }

        // This creates a temporary best move and makes a fake move
        // to see whether or not that move path results in a high
        // overall board value.
        Move bestMove = new Move(Constants.ARBITRARY, Constants.ARBITRARY, Constants.LOW_VAL);
        Move tempMove;
        int tempValue;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (board.checkMoveValidity(i, j, currentPlayer, false, false)) {
                    // creates move on dummy board
                    Board testBoard = this.makeTestMove(board, currentPlayer, i, j);
                    if (intelligence == 1) {
                        tempValue = testBoard.evaluateBoard(currentPlayer);
                        tempMove = new Move(i, j, tempValue);
                    } else {
                        // recursion through opponents perspective
                        tempMove = this.getBestMove(testBoard, intelligence - 1, oppPlayer).setNegativeValue();
                        tempMove.setCoords(i, j);
                    }

                    // non-deterministic move choice.
                    // It chooses at random instead of a fixed outcome.
                    if (tempMove.getVal() > bestMove.getVal()) {
                        bestMove = tempMove;
                        this.bestMoves.clear();
                        this.bestMoves.add(bestMove);
                    } else if (tempMove.getVal() == bestMove.getVal()) {
                        this.bestMoves.add(tempMove);
                    }
                }
            }
        }
        if (intelligence == 1) {
            int randArrayIndex = (int) (Math.random() * this.bestMoves.size());
            return this.bestMoves.get(randArrayIndex);
        } else {
            return bestMove;
        }
    }

    /*
     * This method makes a non-graphical move on the dummy board then
     * returns that board to be evaluated by the miniMax algorithm.
     */
    public Board makeTestMove(Board gameBoard, Paint currentPlayer, int row, int col) {
        Board testGameBoard = new Board(gameBoard);
        SmartSquare[][] testBoard = testGameBoard.getBoard();
        testGameBoard.checkMoveValidity(row, col, currentPlayer, true, true);
        if (testBoard[row][col] != null){
            if (currentPlayer == Color.BLACK) {
                testBoard[row][col].setBlack();
            } else {
                testBoard[row][col].setWhite();
            }
        }
        return testGameBoard;
    }

    /*
     * This method ends the player's turn.
     */
    @Override
    public void endTurn() {
        this.gameBoard.getPane().setOnMouseClicked(null);
        this.referee.turnEnd();
    }

    /*
     * This setter method handles two-way association between
     * the players and the referee.
     */
    @Override
    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    /*
     * This getter method gets the player color.
     */
    @Override
    public Paint getPlayerColor() {
        return this.playerColor;
    }

}
