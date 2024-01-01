package othello;

/*
 * This class just holds onto multiple values so that
 * multiple values can be accessed when an instance of
 * the class is returned.
 */
public class Move {
    private int row;
    private int col;
    private int val;

    /*
     * This constructor sets the instance variables
     * equal to what was passed through.
     */
    public Move(int row, int col, int val) {
        this.row = row;
        this.col = col;
        this.val = val;
    }

    /*
     * This setter method sets the value to negative
     * and returns it.
     */
    public Move setNegativeValue() {
        this.val = -1 * this.val;
        return this;
    }

    /*
     * This setter method sets the coordinates of the move
     * to what was passed in.
     */
    public void setCoords(int i, int j) {
        this.row = i;
        this.col = j;
    }

    /*
     * This getter method gets the move value.
     */
    public int getVal() {
        return this.val;
    }

    /*
     * This getter method gets the move row.
     */
    public int getI() {
        return this.row;
    }

    /*
     * This getter method gets the move column.
     */
    public int getJ() {
        return this.col;
    }

}
