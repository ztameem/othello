package othello;

import javafx.scene.paint.Paint;

/*
 * This interface declares methods that are shared
 * between the Players of the game. More info
 * about each is found in each class.
 */
public interface Player {
    /*
     * Player makes a move.
     */
    void makeMove();

    /*
     * Two-way association.
     */
    void setReferee(Referee referee);

    /*
     * Gets the player color.
     */
    Paint getPlayerColor();

    /*
     * Ends the Player's turn.
     */
    void endTurn();
}
