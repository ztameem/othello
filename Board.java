package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/*
 * This is the Board class. It creates the visual and logical board of the
 * game. It also handles the actions that deal with pieces on the board (Such
 * as counting the pieces/values).
 */
public class Board {
    private Pane pane;
    private final SmartSquare[][] board;
    private ArrayList<SmartSquare> highlightedPieces;
    private int blackScore;
    private int whiteScore;

    /*
     * This is the Board constructor. It instantiates some of the instance variables
     * and calls the starting methods that set up the board.
     */
    public Board(Pane pane){
        this.pane = pane;
        this.board = new SmartSquare[10][10];
        this.highlightedPieces = new ArrayList<>();
        this.setUpPieces();
        this.initialPieces();
    }

    /*
     * This is the copy constructor. It creates a logical copy of the original board.
     */
    public Board(Board gameBoard) {
        this.board = new SmartSquare[10][10];
        this.copyBoard(gameBoard.getBoard());
    }

    /*
     * This is the evaluateBoard method. It calculates the overall board score of
     * the current player using the move weights provided to us.
     */
    public int evaluateBoard(Paint currentPlayer) {
        int boardScore = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (currentPlayer == Color.BLACK) {
                    if (this.board[i][j].getPieceColor() == Color.BLACK) {
                        boardScore += Constants.MOVE_WEIGHTS[i][j];
                    } else if (this.board[i][j].getPieceColor() == Color.WHITE) {
                        boardScore -= Constants.MOVE_WEIGHTS[i][j];
                    }
                } else {
                    if (this.board[i][j].getPieceColor() == Color.WHITE) {
                        boardScore += Constants.MOVE_WEIGHTS[i][j];
                    } else if (this.board[i][j].getPieceColor() == Color.BLACK) {
                        boardScore -= Constants.MOVE_WEIGHTS[i][j];
                    }
                }
            }
        }
        return boardScore;
    }

    /*
     * This method creates an exact copy of the main this.board onto its copy.
     */
    public void copyBoard(SmartSquare[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                 if (i != 0 && i != 9 && j != 0 && j != 9) {
                     this.board[i][j] = new SmartSquare(0, 0, null, true);
                    if (board[i][j].getPieceColor() == Color.WHITE) {
                        this.board[i][j].setWhite();
                    } else if (board[i][j].getPieceColor() == Color.BLACK) {
                        this.board[i][j].setBlack();
                    }
                }
            }
        }
    }

    /*
     * This method counts up all pieces on the board of each color and sets
     * the instance variables equal to that score.
     */
    public void setScores() {
        this.blackScore = 0;
        this.whiteScore = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (this.getPieceColor(i, j) != Color.TRANSPARENT ) {
                    if (this.getPieceColor(i, j) == Color.BLACK) {
                        this.blackScore += 1;
                    } else {
                        this.whiteScore +=1;
                    }
                }
            }
        }
    }

    /*
     * This method creates the board by iterating through a 2d array and
     * finding the x's and y's.
     */
    public void setUpPieces() {
        int y = 0;
        int x = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.board[i][j] = new SmartSquare(x, y, this.pane, false);
                x += Constants.SQUARE_WIDTH;
            }
            x = 0;
            y += Constants.SQUARE_WIDTH;
        }
    }

    /*
     * This method places down the initial pieces of the board. It was made into a method
     * in case future designs don't want the initial pieces set.
     */
    public void initialPieces() {
        this.board[4][4].setBlack();
        this.board[4][5].setWhite();
        this.board[5][5].setBlack();
        this.board[5][4].setWhite();
    }

    /*
     * This method checks for a valid move. It calls upon
     * another method and takes in booleans depending on if
     * the player has made their move or not.
     */
    public boolean checkMoveValidity(int row, int col, Paint playerColor,
                                     boolean hasClicked, boolean justFlip) {
        if (!justFlip) {
            if (row == 0 || row == 9 || col == 0 || col == 9) {
                return false;
            }
            if (this.board[row][col].getPieceColor() != Color.TRANSPARENT) {
                return false;
            }
        }

        boolean hasSandwich = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                boolean sandwichOneDir = this.checkOneDirection(row + i, col + j, i, j,
                        playerColor, false, false);
                if (sandwichOneDir) {
                    hasSandwich = true;
                    if (hasClicked) {
                        this.checkOneDirection(row + i, col + j, i, j, playerColor,
                                false, true);
                    }
                }
            }
        }
        return hasSandwich;
    }

    /*
     * This method checks for all available moves for a
     * specific color and highlights those squares. It also has
     * a purpose of checking if there are any available moves.
     */
    public boolean highlightOptions(int row, int col,
                                    Paint playerColor, boolean highlightsSquares) {
        if (this.board[row][col].getPieceColor() == Color.TRANSPARENT) {
            int availableMoves = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    boolean sandwichOneDir = this.checkOneDirection(row + i, col + j, i, j, playerColor, false, false);
                    if (sandwichOneDir) {
                        availableMoves += 1;
                        if (highlightsSquares) {
                            this.highlightMoves(row, col);
                        }
                    }
                }
            }
            return availableMoves != 0;
        }
        return false;
    }

    /**
     * This is the changeBorder method. It passes in borderColor as a parameter.
     * It is used to change the color of the border.
     */
    public void changeBorder(Paint borderColor) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    this.board[i][j].setSquareFill(borderColor);
                }
            }
        }
    }

    /*
     * This method goes through each available
     * piece and checks if it's an available move.
     */
    public void setHighlightedPieces(Paint playerColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                this.highlightOptions(i, j, playerColor, true);
            }
        }
    }

    /*
     * This method is used recursively to check if a move
     * has a sandwich (is valid).
     */
    public boolean checkOneDirection(int row, int col, int rowDir, int colDir,
                                     Paint playerColor, boolean seenOpponent, boolean inFlipMode) {
        if (this.board[row][col] == null) {
            return false;
        }
        Paint pieceColor = this.board[row][col].getPieceColor();
        if (pieceColor == Color.TRANSPARENT) {
            return false;
        }
        if (pieceColor != playerColor) {
            if (inFlipMode) {
                this.flip(row, col);
            }
            return this.checkOneDirection(row + rowDir, col + colDir, rowDir, colDir,
                    playerColor, true, inFlipMode);
        }
        return seenOpponent;
    }

    /*
     * This method checks if there are available moves
     * for both players. If both players have no available moves,
     * it's game over.
     */
    public boolean checkGameOver(boolean highlightSquares, Paint currentPlayer, Paint oppPlayer) {
        if (this.areNoMoves(oppPlayer, highlightSquares)) {
            return this.areNoMoves(currentPlayer, highlightSquares);
        }
        return false;
    }

    /*
     * This method compares the scores and returns a
     * String of whose score was higher.
     */
    public String getWinner() {
        if (this.getWhiteScore() > this.getBlackScore()) {
            return "WHITE WINS";
        } else if (this.getWhiteScore() < this.getBlackScore()) {
            return "BLACK WINS";
        } else {
            return "TIE GAME";
        }
    }

    /*
     * This method just colors the actual
     * squares of available moves.
     */
    public void highlightMoves(int row, int col) {
        if (this.highlightedPieces != null) {
            this.highlightedPieces.add(this.board[row][col]);
            this.board[row][col].setSquareFill(Color.rgb(159,127,101));
        }
    }

    /*
     * This method removes the highlights on all
     * the moves that were available to the last
     * player.
     */
    public void unHighlightMoves(){
        if (this.highlightedPieces != null) {
            for (SmartSquare piece : this.highlightedPieces) {
                piece.setSquareFill(Color.rgb(72, 93, 63));
            }
        }
    }

    /*
     * This method checks if no moves are available.
     */
    public boolean areNoMoves(Paint pieceColor, boolean highlightsSquares) {
        int count = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (this.highlightOptions(i, j, pieceColor, highlightsSquares)) {
                    count += 1;
                }
            }
        }
        return count == 0;
    }

    /*
     * This method flips the color of the pieces.
     */
    public void flip(int row, int col) {
        Paint pieceColor = this.board[row][col].getPieceColor();
        if (pieceColor == Color.WHITE) {
            this.board[row][col].setPieceFill(Color.BLACK);
        } else {
            this.board[row][col].setPieceFill(Color.WHITE);
        }
    }

    /*
     * This getter method gets the piece color.
     */
    public Paint getPieceColor(int i, int j) {
        return this.board[i][j].getPieceColor();
    }

    /*
     * This getter method gets SmartSquare 2d array.
     */
    public SmartSquare[][] getBoard() {
        return this.board;
    }

    /*
     * This getter method gets the pane.
     */
    public Pane getPane() {
        return this.pane;
    }

    /*
     * This getter method gets the black player's score.
     */
    public int getBlackScore() {
        return this.blackScore;
    }

    /*
     * This getter method gets the white player's score.
     */
    public int getWhiteScore() {
        return this.whiteScore;
    }
}
