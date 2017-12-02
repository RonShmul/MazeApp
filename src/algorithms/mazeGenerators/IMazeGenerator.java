package algorithms.mazeGenerators;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public interface IMazeGenerator {
    /**
     * get number of rows and number of columns and generate a maze accordingly.
     * @param rows
     * @param cols
     * @return Maze
     */
    Maze generate(int rows , int cols);

    /**
     * measuring the time the generate function takes.
     * @param rows
     * @param cols
     * @return long ( milliseconds.)
     */
    long measureAlgorithmTimeMillis(int rows , int cols);

}
