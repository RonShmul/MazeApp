package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class SearchableMaze implements ISearchable {
    private Maze searchMaze;
    private HashSet<MazeState> ourPool;

    /**
     * SearchableMaze constructor.
     * @param maze
     */
    public SearchableMaze(Maze maze) {
        ourPool = new HashSet<>();
        searchMaze = maze;
    }

    /**
     * get the pool of AStates evaluated in the system.
     * @return HashSet
     */
    public HashSet<MazeState> getOurPool() {
        return ourPool;
    }

    /**
     * clear the pool from all AStates.
     */
    @Override
    public void clear() {
        ourPool.clear();
    }

    /**
     * create and get the start state in the SearchableMaze domain from the Maze
     * @return Astate
     */
    @Override
    public AState getStartState() {
        int row = searchMaze.getStartPosition().getRowIndex();
        int col = searchMaze.getStartPosition().getColumnIndex();
        int content = searchMaze.getMazeArray()[row][col];

        AState start = new MazeState(null, 0, row, col, content);

        return start;
    }

    /**get the goal state in the SearchableMaze domain from the Maze.
     * create it if not exists yet.
     * @return AState
     */
    @Override
    public AState getGoalState() {

        int row = searchMaze.getGoalPosition().getRowIndex();
        int col = searchMaze.getGoalPosition().getColumnIndex();
        int content = searchMaze.getMazeArray()[row][col];
        AState checkState = new MazeState(null, 0, row, col, content) {
        };
        if (ourPool.contains(checkState)) {
            for (AState state : ourPool) {
                if (state.equals(checkState)) {
                    return state;
                }

            }
        }

        return checkState;
    }

    /**
     * check if AState with the same x and y is exists, if so - return the exist one with the right parameters
     * if not exists - create new one and return it.
     * @param current
     * @param cost
     * @param x
     * @param y
     * @param content
     * @return AState
     */
    private AState checkIfExists(AState current, double cost, int x, int y, int content) {
        AState checkState = new MazeState(current, cost, x, y, content);
        if (ourPool.contains(checkState)) {
            for (AState state : ourPool) {
                if (state.equals(checkState)) {
                    if (cost < state.getCost()) {
                        state.setParent(current);
                        state.setCost(cost);
                    }
                    return state;
                }
            }
        } else
            ourPool.add(((MazeState) checkState));
        return checkState;

    }

    /**
     * Get all the adjacent states of a current state.
     * create new adjacency if not exists yet in the system (in the pool HashSet)
     * @param curr
     * @return Array List of AStates
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState curr) {
        MazeState current = (MazeState) curr;
        int currX = current.getX();
        int currY = current.getY();
        ArrayList<AState> allPossibleState = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((currX + i < searchMaze.getNumOfRows()) && (currX + i >= 0) && (currY + j < searchMaze.getNumOfCols()) && (currY + j >= 0)) {
                    if ((i == 0) && (j == 0))
                        continue;
                    int content = searchMaze.getMazeArray()[currX + i][currY + j];

                    if (i != 0 && j != 0) {
                        if (content == 0) {
                            int diagonFirst = searchMaze.getMazeArray()[currX][currY + j];
                            int diagonSec = searchMaze.getMazeArray()[currX + i][currY];
                            if (diagonFirst == 0 || diagonSec == 0) {
                                AState checkState = checkIfExists(current, current.cost + 1.5, currX + i, currY + j, content);
                                allPossibleState.add(checkState);
                            }
                        }
                    } else {
                        if (content == 0) {
                            AState checkState = checkIfExists(current, current.cost + 1, currX + i, currY + j, content);

                            allPossibleState.add(checkState);
                        }
                    }
                }
            }
        }
        return allPossibleState;
    }
}
