import algorithms.search.BestFirstSearch;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ronshmul on 29/04/2017.
 */


public class UnitTestingBestFirstSearch {
    /**
     * test for checking if the name sets correctly to Best First Search
     * @throws Exception
     */
    @Test
    public void getName() throws Exception {
        BestFirstSearch testBest = new BestFirstSearch();
        assertEquals("Best First Search" , testBest.getName());
    }

    /**
     * tests if the object is null
     * @throws Exception
     */
    public void bestFirstSearch() throws Exception{
        BestFirstSearch testBest = null;
        assertNull(testBest);
    }
}