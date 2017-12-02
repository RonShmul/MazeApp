package algorithms.search;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public abstract class AState implements Comparator, Comparable, Serializable {
    protected AState parent;
    protected double cost;
    protected boolean visited;


    /**
     * constructor for AState get parent Astate and double cost. boolean visited is false by default.
     * @param parent
     * @param cost
     */
    public AState(AState parent, double cost) {
        this.parent = parent;
        this.cost = cost;
        this.visited = false;
    }

    /**get the paren state for this state.
     * @return AState
     */
    public AState getParent() {
        return parent;
    }

    /**
     * get cost for this state
     * @return double
     */
    public double getCost() {
        return cost;
    }

    /**
     * set visited boolean true or false.
     * @param visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * set a cost as double for this state
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * get if the state is visited or not
     * @return boolean
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * set parent AState.
     * @param parent
     */
    public void setParent(AState parent) {
        this.parent = parent;
    }

    /**
     * hashcode override of hashset use
     * @return int
     */
    @Override
    public int hashCode() {
        return (int)cost;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }

    /**
     * override compareTo which compare 2 AState by cost.
     * @param o
     * @return int. -1 if this's cost is lower,  if this cost is higher and 0 if equals in cost.
     */
    @Override
    public int compareTo(Object o) {
        AState state = (AState) o;
        if(this.getCost() < state.getCost())
            return -1;
        if(this.getCost() > state.getCost())
            return 1;
        return 0;
    }

    /**
     * verride compare which compare 2 AState by cost.
     * @param o1
     * @param o2
     * @return int. -1 if o1's cost is lower,  if o2's cost is lower and 0 if equals in cost.
     */
    @Override
    public int compare(Object o1, Object o2) {
        AState s1 = (AState)o1;
        AState s2 = (AState)o2;

        if(s1.getCost() < s2.getCost())
            return -1;
        if(s1.getCost() > s2.getCost())
            return 1;
        return 0;
    }
}
