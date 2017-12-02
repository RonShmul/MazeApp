package algorithms.search;

import java.io.Serializable;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class MazeState extends AState implements Serializable {

    private int x;
    private int y;
    private int content;


    /**
     * MazeState constructor.
     * @param parent
     * @param cost
     * @param x
     * @param y
     * @param content
     */
    public MazeState(AState parent, double cost, int x, int y , int content) {
        super(parent, cost);
        this.x = x;
        this.y = y;
        this.content = content;
    }

    /**
     * return row index - x
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * return column index - y
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * return the content of a cell in the maze array - 0 or 1.
     * @return int
     */
    public int getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }

    @Override
    public int hashCode() {
        return x+y;
    }

    /**
     * return true if x and y of two MazeStates are equals.
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        MazeState check = (MazeState) obj;
        if(check.getX() == this.x && check.getY() == this.y)
            return true;
        return false;
    }
}
