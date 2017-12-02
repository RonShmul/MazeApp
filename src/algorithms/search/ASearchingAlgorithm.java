package algorithms.search;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    //protected int numOfNodesEvaluated;
    protected String name;
    protected int numOfStates;
    protected Solution sol;

    /**
     * abstract class for searching algorithms.
     */
    public ASearchingAlgorithm() {
        name = null;
        numOfStates = 0;
        sol = null;
    }

    /**
     * get the name of the algorithm
     * @return string
     */
/*   Getters   */
    @Override
    public String getName() {
        return name;
    }

    /**
     * get the number of states the searching algorithm evaluated
     * @return int
     */
    public int getNumOfStates() {
        return numOfStates;
    }

    public Solution getSol() {
        return sol;
    }

    /*  setters   */

    /**
     * set the name of the algorithm String
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the number of states evaluated
     * @param numOfStates
     */
    public void setNumOfStates(int numOfStates) {
        this.numOfStates = numOfStates;
    }

    /**
     * set a specific solution.
     * @param sol
     */
    public void setSol(Solution sol) {
        this.sol = sol;
    }

    /**
     * get the number of states the searching algorithm evaluated
     * @return String
     */
    public String getNumberOfNodesEvaluated(){
        return ""+numOfStates;
    }
}
