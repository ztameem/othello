package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/*
 * This class holds the squares and pieces that are on the board.
 */
public class SmartSquare {
    private Rectangle square;
    private Circle piece;

    /*
     * The constructor calls the method that starts the board and places the pieces down.
     */
    public SmartSquare(int x, int y, Pane pane, boolean dummyBoard) {
        this.setUpSquares(x, y, pane, dummyBoard);
    }

    /*
     * This method sets up the pieces in the 2d array and on screen.
     */
    public void setUpSquares(int x, int y, Pane pane, boolean dummyBoard) {
        this.square = new Rectangle(x, y, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        if (x == 0 || x == 675 || y == 0 || y == 675) {
            this.square.setFill(Color.rgb(76, 43, 32));
        } else {
            this.square.setFill(Color.rgb(72, 93, 63));

        }
        this.piece = new Circle(x + Constants.PIECE_RADIUS + Constants.PIECE_OFFSET,
                y + Constants.PIECE_RADIUS + Constants.PIECE_OFFSET, Constants.PIECE_RADIUS);
        this.piece.setFill(Color.TRANSPARENT);
        this.piece.setStrokeWidth(5);
        this.square.setStroke(Color.BLACK);
        if (!dummyBoard) {
            pane.getChildren().addAll(this.square, this.piece);
        }
    }

    /*
     * This method hides the highlighted last move made.
     */
    public void hideLastMove() {
        this.piece.setStrokeWidth(0);
    }

    /*
     * This method highlights the last move made.
     */
    public void showLastMove(Paint color) {
        this.piece.setStroke(color);
    }

    /*
     * This method sets the piece to black.
     */
    public void setBlack() {
        this.piece.setFill(Color.BLACK);
    }

    /*
     * This method sets the piece to white.
     */
    public void setWhite() {
        this.piece.setFill(Color.WHITE);
    }

    /*
     * This method gets the piece color.
     */
    public Paint getPieceColor() {
        return this.piece.getFill();
    }

    /*
     * This method sets the piece to the inputted color.
     */
    public void setPieceFill(Paint color) {
        this.piece.setFill(color);
    }

    /*
     * This method sets the square to the inputted color.
     */
    public void setSquareFill(Paint color) {
        this.square.setFill(color);
    }
}
