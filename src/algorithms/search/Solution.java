package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Ron Shmulinson and Sivan Rejensky on 25/04/2017.
 */
public class Solution implements Serializable{
    private ArrayList<AState> path;
    private Stack<AState> stack;

    /**
     * Solution constructor initiate the path array list and a helper stack.
     */
    public Solution(){
        path = new ArrayList<>();
        stack = new Stack<>();
    }

    /**
     * push AState that a part of the solution path to the helper stack
     * @param state
     */
    public void setAstate(AState state) {
        if(state != null)
            stack.push(state);

    }

    /**
     * push AStates to the stack while temp is not the start State.
     * push the parents AStates from temp AState to the start State.
     * @param temp
     * @param start
     */
    public void setArr(AState temp , AState start){
        while (temp != start) {
            setAstate(temp);
            temp = temp.getParent();
        }
        setAstate(start);
    }

    /**
     * pop AStates from the helper stack to the array list for the Solution Path to be in the correct order.
     * @return ArrayList of AStates.
     */
    public ArrayList<AState> getSolutionPath() {
        while(!stack.isEmpty())
            path.add(stack.pop());
        return path;
    }
}
