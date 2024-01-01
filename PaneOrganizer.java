package othello;

import javafx.scene.layout.BorderPane;

/*
 * This class creates the gui of the game and
 * sets up the controls.
 */
public class PaneOrganizer {
    private final BorderPane organizer;

    /*
     * The constructor creates a new instance of
     * a BorderPane and sets the background color
     * of the scene.
     */
    public PaneOrganizer() {
        this.organizer = new BorderPane();
        this.setControls();
        this.organizer.setStyle("-fx-background-color: #ba9b82");
    }

    /*
     * This method sets the controls of the game.
     */
    private void setControls() {
        Board board = new Board(this.organizer);
        Controls controls = new Controls(board, this.organizer);
        this.organizer.setRight(controls.getPane());
    }

    /*
     * This getter method gets the BorderPane of the game.
     */
    public BorderPane getRoot() {
        return this.organizer;
    }
}
