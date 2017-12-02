package algorithms.mazeGenerators;

import algorithms.search.*;

import java.util.ArrayList;

/**
 * Created by Ronshmul on 09/04/2017.
 */
/*public class Main {

    public static void main(String[] args) {
        // write your code here
        IMazeGenerator shush = new MyMazeGenerator();
        long time = shush.measureAlgorithmTimeMillis(30,30);
        //System.out.println(time);
        Maze mush = shush.generate(12,12);
        System.out.println(mush.getStartPosition());
        System.out.println(mush.getGoalPosition());
        mush.print();
        System.out.println();
        System.out.println();

        SearchableMaze searchableMaze = new SearchableMaze(mush);
        DepthFirstSearch dfs = new DepthFirstSearch();

        Solution solution = dfs.solve(searchableMaze);
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
        System.out.print(dfs.getNumberOfNodesEvaluated());
        //System.out.println(String.format("'%s' algorithm - nodes evaluated:%s" , dfs.getName(), dfs.getNumberOfNodesEvaluated()));
    }
}*/

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

//public class RunCompressDecompressMaze {
public class Main {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(150, 200); //Generate new maze
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new
                    FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new
                    FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals =
                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
//maze should be equal to loadedMaze
    }
}
