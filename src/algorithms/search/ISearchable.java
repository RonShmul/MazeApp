package algorithms.search;

import java.util.ArrayList;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public interface ISearchable {
    /**
     * get the start state in the ISearchable domain
     * @return AState
     */
    AState getStartState();

    /**
     * get the goal state in the ISearchable domain
     * @return AState
     */
    AState getGoalState();

    /**
     * get all the adjacent states of a current state.
     * @param curr
     * @return ArrayList<AState>
     */
    ArrayList<AState> getAllPossibleStates(AState curr);

    /**
     * Clear the ISearchable domain
     */
    void clear();


}
