package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import Server.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Sivan And Ron .
 */
public class MyModel extends Observable implements IModel {

    public ExecutorService threadPool = Executors.newCachedThreadPool();
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;
    private Stage stage;
    private MediaPlayer mediaPlayer;

    private Maze MyMaze;
    private Solution solution;
    private int characterPositionRow;
    private int characterPositionColumn;

    public void setMyMaze(Maze myMaze) {
        MyMaze = myMaze;
    }

    public MyModel() {
        //Raise the servers
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

    }

    public void startServers() {
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    public void stopServers() {

        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }


    @Override
    public void generateMaze(int width, int height) {

        threadPool.execute(() -> {
            CommunicateWithServer_MazeGenerating(width, height);

            setChanged();
            notifyObservers();
        });
    }

    @Override
    public void SolveMaze() {


        threadPool.execute(() -> {
            CommunicateWithServer_SolveSearchProblem();

            setChanged();
            notifyObservers();
        });

    }

    @Override
    public Solution getSolution() {
        return solution;
    }


    @Override
    public Maze getMaze() {
        return MyMaze;
    }


    @Override
    public void moveCharacter(KeyCode movement) {
        try {
            switch (movement) {
                case UP:
                    if (((MyMaze.getMazeArray()[characterPositionRow - 1][characterPositionColumn] == 0 || MyMaze.getMazeArray()[characterPositionRow - 1][characterPositionColumn] == 2)) && !(characterPositionRow < 0))
                        characterPositionRow--;
                    break;
                case DOWN:
                    if (((MyMaze.getMazeArray()[characterPositionRow + 1][characterPositionColumn] == 0 || MyMaze.getMazeArray()[characterPositionRow + 1][characterPositionColumn] == 2)) && !(characterPositionRow > MyMaze.getMazeArray().length))
                        characterPositionRow++;
                    break;
                case RIGHT:
                    if (((MyMaze.getMazeArray()[characterPositionRow][characterPositionColumn + 1] == 0 || MyMaze.getMazeArray()[characterPositionRow][characterPositionColumn + 1] == 2)) && !(characterPositionColumn > MyMaze.getMazeArray().length))
                        characterPositionColumn++;
                    break;
                case LEFT:
                    if (((MyMaze.getMazeArray()[characterPositionRow][characterPositionColumn - 1] == 0 || MyMaze.getMazeArray()[characterPositionRow][characterPositionColumn - 1] == 2)) && !(characterPositionColumn < 0))
                        characterPositionColumn--;
            }
            setChanged();
            notifyObservers();
        }
        catch (Exception e) {
        }
    }

//    public void moveCharacter(MouseEvent movement) {
//        if(movement.)
//
//    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }


    private void CommunicateWithServer_MazeGenerating(int width, int height) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{width, height};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(width*height)+6]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        MyMaze = maze;
                        characterPositionRow = MyMaze.getStartPosition().getRowIndex();
                        characterPositionColumn = MyMaze.getStartPosition().getColumnIndex();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        toServer.writeObject(MyMaze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        solution = mazeSolution;
                        ArrayList<AState> solutionPath = solution.getSolutionPath();
                        for (int i = 0; i < solutionPath.size(); i++) {
                            int x = ((MazeState)solutionPath.get(i)).getX();
                            int y = ((MazeState)solutionPath.get(i)).getY();
                            MyMaze.getMazeArray()[x][y] = 2;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void clearMaze(){
        if(solution!= null) {
            ArrayList<AState> solutionPath = solution.getSolutionPath();
            for (int i = 0; i < solutionPath.size(); i++) {
                int x = ((MazeState) solutionPath.get(i)).getX();
                int y = ((MazeState) solutionPath.get(i)).getY();
                MyMaze.getMazeArray()[x][y] = 0;
            }
        }
    }

    @Override
    public void LoadMaze() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Maze");
        fc.setInitialDirectory(new File("resources"));
        File file = fc.showOpenDialog(null);
        if(file != null) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                Maze maze = (Maze)objectInputStream.readObject();
                MyMaze = maze;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        characterPositionRow =MyMaze.getStartPosition().getRowIndex();
        characterPositionColumn =MyMaze.getStartPosition().getColumnIndex();


        setChanged();
        notifyObservers();
    }
    @Override
    public void exit(){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.headerTextProperty().setValue("Are You Sure?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    stage.close();
                    stopServers();
                    threadPool.shutdown();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    alert.close();

                }

    }
    @Override
    public void SaveMaze() {
        clearMaze();
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Maze");
        fc.setInitialDirectory(new File("resources"));
        File file = fc.showSaveDialog(null);
        if(file != null) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(MyMaze);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

