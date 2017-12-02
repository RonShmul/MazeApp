package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public class Maze implements Serializable{


    private Position start;
    private Position goal;
    private int [][]mazeArray;
    private int numOfCols;
    private int numOfRows;
    private byte []byteArr;

    /**
     * the default constructor of Maze.
     */
    public Maze()  {
        numOfCols = 10;
        numOfRows = 10;
        mazeArray = new int[numOfRows][numOfCols];
        byteArr = null;
    }
    /**
     * the constructor for Maze.
     * @param rows
     * @param cols
     */
    public Maze(int rows, int cols) {

        numOfCols = cols;
        numOfRows = rows;
        mazeArray = new int[rows][cols];
        byteArr = null;
    }

    /**
     * constructor that build the maze from the information in the byte[] array
     * In the first cells of the byte array the information that is saved: number of row, number of columns, row index of start position,
     * column index of start position, row index of goal position, column index of goal position - in this order,
     * and than all the maze content for the int[][] maze array.
     * @param byteArr
     */
    public Maze(byte []byteArr){

        this.byteArr = byteArr;
        numOfRows = byteArr[0]& (0xff);
        numOfCols = byteArr[1]& (0xff);
        Position s = new Position(0,0, null);
        Position g = new Position(0,0, null);
        start = s;
        goal = g;
        start.setRow(byteArr[2]& (0xff));
        start.setCol(byteArr[3]& (0xff));
        goal.setRow(byteArr[4]& (0xff));
        goal.setCol(byteArr[5]& (0xff));

        mazeArray = new int[numOfRows][numOfCols];
        int counter = 6;
        for ( int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols ; j++){
                mazeArray[i][j] = byteArr[counter]& (0xff);
                counter++;
            }
        }

    }

    public Position getStart() {
        return start;
    }

    public Position getGoal() {
        return goal;
    }

    public byte[] getByteArr() {
        return byteArr;
    }

    /**getter for the number of columns.
     * @return int.
     */
    public int getNumOfCols() {
        return numOfCols;
    }

    /**getter for the number of rows
     * @return int.
     */
    public int getNumOfRows() {
        return numOfRows;
    }

    /**get an array of int with 1s and 0s in it.
     * @return int[][]
     */
    public int[][] getMazeArray() {
        return mazeArray;
    }

    /**gets the stating position of the maze.
     * @return Position.
     */
    public Position getStartPosition() {

        return start;
    }

    public void setMazeArray(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public void setNumOfCols(int numOfCols) {
        this.numOfCols = numOfCols;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public void setByteArr(byte[] byteArr) {
        this.byteArr = byteArr;
    }

    /**
     * convert the int[][] maze array to byte[] array.
     * In the first cells in the byte array  will save information: number of row, number of columns, row index of start position,
     * column index of start position, row index of goal position, column index of goal position - in this order,
     * and then all the content of the maze array (0's and 1's)
     * @return byte[]
     */
    public byte[] toByteArray(){
        byte []byteArr = new byte[(numOfRows*numOfCols)+6];

        byteArr[0] = ((Integer)numOfRows).byteValue();
        byteArr[1] = ((Integer)numOfCols).byteValue();
        byteArr[2] = ((Integer)getStartPosition().getRowIndex()).byteValue();
        byteArr[3] = ((Integer)getStartPosition().getColumnIndex()).byteValue();
        byteArr[4] = ((Integer)getGoalPosition().getRowIndex()).byteValue();
        byteArr[5] = ((Integer)getGoalPosition().getColumnIndex()).byteValue();
        int counter = 6;

        for ( int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols ; j++){
                byteArr[counter] =((Integer)mazeArray[i][j]).byteValue();
                counter++;
            }
        }
        //todo:hash code function for every maze
        return byteArr;
    }

    /**gets the goal position of the maze.
     * @return Position
     */
    public Position getGoalPosition() {
        return goal;
    }

    /**
     * prints the maze in 2D array format of '█' and ' '.
     */
    public void print(){
        for(int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                if (i == start.getRowIndex() && j == start.getColumnIndex())
                    System.out.print('S');
                else if (i == goal.getRowIndex() && j == goal.getColumnIndex())
                    System.out.print('E');
                else{
                    if (mazeArray[i][j] == 0) {
                    System.out.print(' ');
                }
                if (mazeArray[i][j] == 1) {
                    System.out.print('X');
                }
            }
            }
            if(i < numOfRows-1)
                System.out.println();
        }
    }

    /**
     * set content: 0 or 1 to a specific position in the maze array.
     * get the row i and the j column and assign the content.
     * @param i
     * @param j
     * @param content
     */
    public void setMaze(int i, int j, int content) {
        mazeArray[i][j] = content;
    }

    /**set the start position to be start.
     * @param start
     */
    public void setStart(Position start) {
        this.start = start;
    }

    /**set the goal position to be goal
     * @param goal
     */
    public void setGoal(Position goal) {
        this.goal = goal;
    }
}
