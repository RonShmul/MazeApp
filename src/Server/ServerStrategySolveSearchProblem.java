package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Ronshmul on 22/05/2017.
 */
public class ServerStrategySolveSearchProblem implements ServerStrategy {
    private static int counter;

    public ServerStrategySolveSearchProblem() {
        counter = 0;
    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            boolean flag = false;
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            String currentDirectoryPath = System.getProperty("user.dir");

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            try {
                Maze maze = ((Maze)fromClient.readObject());
                byte[] mazeToByte = maze.toByteArray();
                File dir = new File(tempDirectoryPath);
                File []tempDirectory = dir.listFiles();
                if(tempDirectory!=null) {
                    for (File inTemp : tempDirectory) {
                        if(!(inTemp.getName().contains("SolutionNumber"))){
                            break;
                        }
                        InputStream tempFile = new FileInputStream(inTemp);
                        ObjectInputStream fromFile = new ObjectInputStream(tempFile);
                        Byte[]tempByteArr = ((Byte[])fromFile.readObject());
                        if(mazeToByte.equals(tempByteArr)){
                            flag = true;
                            try {
                                String Dname = inTemp.getName();
                                File solutionFile = new File(currentDirectoryPath + "\\" + Dname);
                                InputStream fileSolution = new FileInputStream(solutionFile);
                                ObjectInputStream solution = new ObjectInputStream(fileSolution);
                                Solution sol = (Solution) solution.readObject();
                                toClient.writeObject(sol);

                                tempFile.close();
                                fromFile.close();
                                fileSolution.close();
                                solution.close();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                }
                if(!flag){
                    ISearchable searchable = new SearchableMaze(maze);
                    ASearchingAlgorithm search;
                    String algo = Server.properties.prop.getProperty("SearchingAlgorithm");
                    if(algo.equals("BreadthFirstSearch")){
                        search = new BreadthFirstSearch();
                    }
                    else if(algo.equals("BestFirstSearch")){
                        search = new BestFirstSearch();
                    }
                    else{
                        search = new DepthFirstSearch();
                    }
                    Solution solution = search.solve(searchable);
                    toClient.writeObject(solution);

                    FileOutputStream fileSolution = new FileOutputStream(currentDirectoryPath + "\\"+ "SolutionNumber" + counter);
                    ObjectOutputStream solutionFile = new ObjectOutputStream(fileSolution);
                    solutionFile.writeObject(solution);

                    FileOutputStream fileMaze = new FileOutputStream(tempDirectoryPath + "\\"+ "SolutionNumber" + counter);
                    ObjectOutputStream mazeFile = new ObjectOutputStream(fileMaze);
                    mazeFile.writeObject(mazeToByte);
                    counter++;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
        }



    }
}
