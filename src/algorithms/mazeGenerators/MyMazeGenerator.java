package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 09/04/2017.
 */
public class MyMazeGenerator extends AMazeGenerator {
    /**
     * get number of rows and number of columns and generate a Maze accordingly using Prim's algorithm.
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

        Position start = new Position((int) ((Math.random()) * rows), (int) ((Math.random()) * cols), null);
        //initiallize start position
        maze.setMaze(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setStart(start);

        ArrayList<Position> Neighbors = new ArrayList<>();
        //checking boundaries
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || (i != 0 && j != 0))
                    continue;
                try {
                    if (maze.getMazeArray()[start.getRowIndex() + i][start.getColumnIndex() + j] == 0)
                        continue;
                } catch (Exception e) {
                    continue;
                }
                //add possible neighbors to array list
                Neighbors.add(new Position(start.getRowIndex() + i, start.getColumnIndex() + j, start));
            }
        }
        Position goal = null;

        while (!Neighbors.isEmpty()) {
            //pick random neighbor
            Position theChosenOne = Neighbors.remove((int) (Math.random() * Neighbors.size()));
            Position Opposite = theChosenOne.checkPosition();
            try {
                //if both walls, turn them into a pass
                if (maze.getMazeArray()[theChosenOne.getRowIndex()][theChosenOne.getColumnIndex()] == 1) {
                    if (maze.getMazeArray()[Opposite.getRowIndex()][Opposite.getColumnIndex()] == 1) {
                        maze.setMaze(theChosenOne.getRowIndex(), theChosenOne.getColumnIndex(), 0);
                        maze.setMaze(Opposite.getRowIndex(), Opposite.getColumnIndex(), 0);

                        goal = Opposite;

                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if ((i == 0 && j == 0) || (i != 0 && j != 0))
                                    continue;
                                try {
                                    if (maze.getMazeArray()[Opposite.getRowIndex() + i][Opposite.getColumnIndex() + j] == 0)
                                        continue;
                                } catch (Exception e) {
                                    continue;
                                }
                                //add possible neighbors to array list
                                Neighbors.add(new Position(Opposite.getRowIndex() + i, Opposite.getColumnIndex() + j, Opposite));
                            }
                        }
                    }
                }

            } catch (Exception e) {
            }

            if(Neighbors.isEmpty()) {
                maze.setMaze(goal.getRowIndex(), goal.getColumnIndex(), 0);
                maze.setGoal(goal);
            }

        }
        return maze;
    }
}
