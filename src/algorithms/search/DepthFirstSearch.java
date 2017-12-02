package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    /**
     * constructor for Depth First Search.
     * set the name to "Depth First Search"
     */
    public DepthFirstSearch() {
        super();
        setName("Depth First Search");

    }

    /**
     * solve the searching problem in a specific domain with depth first search scan.
     * first clear the domain's AStates if exist, get the start AState and start the scan from it using the recursive update method
     *then creates the solution according to the scan.
     * @param domain
     * @return Solution
     */
    @Override
    public Solution solve(ISearchable domain) {
        domain.clear();
        AState start = domain.getStartState();
        start.visited = true;
        start.cost = 0;

        update(start, domain);

        AState goal = domain.getGoalState();
        sol = new Solution();
        sol.setArr(goal, start);

        return sol;
    }

    /**
     * recursive method - set the current AState to visited and get it's neighbors,
     * set a neighbor's cost to the current state cost + 1 and set the parent to the current state.
     * call the update method with the neighbor.
     * @param currentState
     * @param domain
     */
    private void update(AState currentState, ISearchable domain) {
        currentState.setVisited(true);
        ArrayList<AState> neighbors = domain.getAllPossibleStates(currentState);
        for (int i = 0; i < neighbors.size(); i++) {
            AState myNeib = neighbors.get(i);

            if (myNeib.isVisited() == false) {
                numOfStates++;
                myNeib.setCost(currentState.getCost() + 1);
                myNeib.setParent(currentState);
                update(myNeib, domain);
            } else {
                if (myNeib.getCost() > currentState.getCost() + 1) {
                    myNeib.setCost(currentState.getCost() + 1);
                    myNeib.setParent(currentState);
                }
            }
        }
    }
}

