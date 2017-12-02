package algorithms.mazeGenerators;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public abstract class AMazeGenerator implements IMazeGenerator{

    protected Maze maze;


    /** This method is for calculating the time generate method runs.
     * @param rows
     * @param cols
     * @return number of milliseconds the generate function runs
     *
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows , int cols){
        long startTime = System.currentTimeMillis();
        generate(rows, cols);
        long endTime = System.currentTimeMillis();
        return (endTime-startTime);
    }
}
