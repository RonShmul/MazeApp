package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
//import com.sun.java.util.jar.pack.Package;

import java.io.*;

/**
 * Created by Ronshmul on 22/05/2017.
 */
public class ServerStrategyGenerateMaze implements ServerStrategy {

//    private int[] sizes;
//    private MyCompressorOutputStream compress;

    public ServerStrategyGenerateMaze() {
    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            int[] sizes= new int[2];
            try{
                sizes = (int[])fromClient.readObject();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            AMazeGenerator generateMaze;
            String s = Server.properties.prop.getProperty("MazeGenerator");
            if(s.equals("MyMazeGenerator")){
                generateMaze = new MyMazeGenerator();
            }
            else
            {
                generateMaze = new SimpleMazeGenerator();
            }

            Maze maze = generateMaze.generate(sizes[0],sizes[1]);
            byte[] byteMaze = maze.toByteArray();

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            byteOut.flush();

            MyCompressorOutputStream compress = new MyCompressorOutputStream(byteOut);
            compress.write(byteMaze);
            toClient.writeObject(byteOut.toByteArray());
            toClient.flush();
        }catch (Exception e){}
    }
}
