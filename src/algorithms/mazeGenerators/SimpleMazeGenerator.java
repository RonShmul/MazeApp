package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    /**
     * get number of rows and number of columns and generate a Maze accordingly in random.
     * @param rows
     * @param cols
     * @return Maze
     */
    @Override
    public Maze generate(int rows, int cols) {

        maze = new Maze(rows, cols);
        //initiate the maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze.setMaze(i, j, 1);
            }
        }

        //initiallize start position
        Position start = new Position((int) ((Math.random()) * rows), 0, null);
        maze.setMaze(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setStart(start);
        //initiallize goal position
        Position goal = new Position((int) ((Math.random()) * rows), cols-1, null);
        maze.setMaze(goal.getRowIndex(), goal.getColumnIndex(), 0);
        maze.setGoal(goal);

        Position temp = start;
        while (temp.compareTo(goal) != 0) {
                ArrayList<Position> neighbors = new ArrayList<>();
            try {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if ((i == 0 && j == 0) || (i != 0 && j != 0))
                            continue;
                        //add possible neighbors to array list
                        neighbors.add(new Position(temp.getRowIndex() + i, temp.getColumnIndex() + j, temp));
                    }
                }
                Position randomNeighbor = neighbors.remove((int) (Math.random() * neighbors.size()));
                maze.setMaze(randomNeighbor.getRowIndex(), randomNeighbor.getColumnIndex(), 0);

                temp = randomNeighbor;
            }catch(Exception e) {}
    }

        return maze;
    }
}
