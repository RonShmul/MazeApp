package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public class Position implements Comparable, Serializable{

    private int row;
    private int col;
    private Position parent;

    public Position() {
        row = 0;
        col = 0;
        parent = null;
    }

    public Position(int row, int col, Position parent) { //todo: check if need to remove parent from constructor's args
        this.row = row;
        this.col = col;
        this.parent = parent;
    }
    /*   Getters   */

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position getParent() {
        return parent;
    }

    /**
     * get the row index of the Position
     * @return int
     */

    public int getRowIndex() {
        return row;
    }

    /**
     * get the column index of the Position
     * @return
     */
    public int getColumnIndex() {
        return col;
    }
    
    /*   Setters   */

    /**
     * set row index for a Position.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * set row index for a Position.
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Set parent Position to this Position.
     * @param parent
     */
    public void setParent(Position parent) {
        this.parent = parent;
    }

    /**
     * print the Position to the screen
     */

    public void print() {
        System.out.print(this);
    }

    /**
     * create and return the next Position - the opposite from the parent Position.
     * @return Position
     */
    public Position checkPosition() {
        int resRow = this.row - parent.row;
        int resCol = this.col - parent.col;

        if(resRow != 0) {
            return new Position(this.row + resRow, this.col, this);
        }
        if(resCol != 0) {
            return new Position(this.row, this.col + resCol, this);
        }
        return null;

    }


    /**
     * compare by row and column. binary - equals or not.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if((this.row == ((Position)o).row) &&(this.col ==((Position)o).col) )
            return 0;
        return 1;
    }

    @Override
    public String toString() {
        return "{" + row + "," + col + "}";
    }
}

