package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * Best first Search algorithm's constructor. Expends Breadth First Search.
     * q is a Queue which is initialize here as priority queue
     * name set to "Best First Search"
     */
    public BestFirstSearch() {
        super();
        setName("Best First Search");
        q = new PriorityQueue<>();
    }

    /**return the name of the Algorithm
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    void setTheNeighbor(AState neighbor, AState curr) {

    }
}
