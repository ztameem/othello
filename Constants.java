package othello;

/*
 * This is the Constants class. It keeps track of all reused values
 */
public class Constants {
    public static final int ARBITRARY = 0;
    public static final int SCENE_WIDTH = 1055;
    public static final int SCENE_HEIGHT = 750;
    public static final int SQUARE_WIDTH = 75;
    public static final int PIECE_RADIUS = 32;
    public static final double PIECE_OFFSET = 5.5;
    public static final int HIGH_VAL = 1000000;
    public static final int LOW_VAL = -1000000;
    public static final int NEUTRAL_VAL = 0;
    public static final int[][] MOVE_WEIGHTS = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 200, -70, 30, 25, 25, 30, -70, 200, 0},
            {0, -70, -100, -10, -10, -10, -10, -100, -70, 0},
            {0, 30, -10, 2, 2, 2, 2, -10, 30, 0},
            {0, 25, -10, 2, 2, 2, 2, -10, 25, 0},
            {0, 25, -10, 2, 2, 2, 2, -10, 25, 0},
            {0, 30, -10, 2, 2, 2, 2, -10, 30, 0},
            {0, -70, -100, -10, -10, -10, -10, -100, -70, 0},
            {0, 200, -70, 30, 25, 25, 30, -70, 200, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int CONTROLS_PANE_WIDTH = 250;
}
