package algorithms.search;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public interface ISearchingAlgorithm {
    /**
     * solve a searching problem in a searchable domain according to the searching algorithm.
     * @param domain
     * @return Solution
     */
    Solution solve(ISearchable domain);

    /**
     * get the name of the searching algorithm
     * @return String
     */
    String getName();

    /**
     * get the number of nodes the searching algorithm evaluated while solving.
     * @return String
     */
    String getNumberOfNodesEvaluated();
}
