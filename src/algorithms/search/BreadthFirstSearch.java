package algorithms.search;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected Queue<AState> q;

    /**
     * Breadth first Search algorithm's constructor.
     * q is a Queue which is initialize here as Linked List
     * name set to "Breadth First Search"
     */
    public BreadthFirstSearch() {
        super();
        q = new LinkedList<>();
        setName("Breadth First Search");
    }

    /**
     * check if a current AState's neighbors are visited and if not set the parent to the current AState,
     * the cost to the current cost + 1, marked as visited and add the neighbor to the queue.
     * the current AState removed from the queue.
     * @param domain
     */
    public void check(ISearchable domain){
        AState curr = q.peek();
        q.remove(curr);
        ArrayList<AState> neighbors = domain.getAllPossibleStates(curr);
        for(int i=0; i<neighbors.size(); i++){
            AState neighbor = neighbors.get(i);
            if (!neighbor.isVisited()){
                numOfStates++;
                neighbor.setVisited(true);

                setTheNeighbor(neighbor, curr);

                q.add(neighbor);
            }
        }
    }

    /**
     * set parent and cost for a neighbor according to the current AState
     * @param neighbor
     * @param curr
     */
    void setTheNeighbor(AState neighbor, AState curr) {
        neighbor.setParent(curr);
        neighbor.setCost(curr.getCost()+1);
    }

    /**
     * solve the searching problem in a specific domain with breadth first search scan.
     * first clear the domain's AStates if exist, get the start AState and start the scan from it using the check method
     * while the queue is not empty.
     *then creates the solution according to the scan.
     * @param domain
     * @return Solution
     */
    @Override
    public Solution solve(ISearchable domain) {
        domain.clear();
        AState start = domain.getStartState();
        start.setVisited(true);
        q.add(start);

        while(!q.isEmpty()){
            check(domain);
        }

        AState goal = domain.getGoalState();
        sol = new Solution();
        sol.setArr(goal, start);
        return sol;
    }

}
