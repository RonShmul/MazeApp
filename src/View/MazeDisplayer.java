package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import com.sun.javafx.iio.gif.GIFDescriptor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public class MazeDisplayer extends Canvas {

    private Maze maze;
    private Solution solution;
    private int characterPositionRow ;
    private int characterPositionColumn;
    private MediaPlayer mediaPlayer;


    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private StringProperty ImageFileNameTrace = new SimpleStringProperty();



    public void setMaze(Maze maze) {
        this.maze = maze;
        characterPositionRow = maze.getStartPosition().getRowIndex();
        characterPositionColumn = maze.getStartPosition().getColumnIndex();
        redraw();
    }

    public void setSolution(Solution solution){
        this.solution = solution;
        //drawSolution();
    }

    private void drawSolution() {
        if (solution != null) {
            ArrayList<AState> solutionPath = solution.getSolutionPath();
            for (int i = 0; i < solutionPath.size(); i++) {
                int x = ((MazeState)solutionPath.get(i)).getX();
                int y = ((MazeState)solutionPath.get(i)).getY();
                maze.getMazeArray()[x][y] = 2;
            }
            redraw();

        }

    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void redraw() {


        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.getMazeArray()[0].length;
            double cellWidth = canvasWidth / maze.getMazeArray().length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                Image GoalImage = new Image(new FileInputStream(ImageFileNameGoal.get()));
                Image TraceImage = new Image(new FileInputStream(ImageFileNameTrace.get()));


                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.getMazeArray().length; i++) {
                    for (int j = 0; j < maze.getMazeArray()[i].length; j++) {

                        if (maze.getMazeArray()[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, j*cellWidth, i*cellHeight, cellWidth, cellHeight);
                        }
                        if(maze.getMazeArray()[i][j] == 2){
                            gc.drawImage(TraceImage, j*cellWidth, i*cellHeight, cellWidth, cellHeight);
                        }
                    }
                }

                gc.drawImage(GoalImage, maze.getGoalPosition().getColumnIndex()*cellWidth,maze.getGoalPosition().getRowIndex()*cellHeight, cellWidth, cellHeight);
                gc.drawImage(characterImage, characterPositionColumn * cellWidth,  characterPositionRow * cellHeight,cellWidth, cellHeight);

                if(characterPositionRow == maze.getGoalPosition().getRowIndex() && characterPositionColumn == maze.getGoalPosition().getColumnIndex()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(" ");
                    alert.setTitle("winning message");
                    alert.setContentText("                            You Won!");

                    Image image = new Image(new FileInputStream("resources/Images/fire1.gif"));
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
//                    ImageView image = new ImageView(new Image(new File("Aviad.png").toURI().toString()));
//                   alert.getDialogPane().getChildren().add(image);
                    //(new ImageView(new Image(new File("Aviad.png").toURI().toString())));
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        alert.close();
                    } else {
                        // ... user chose CANCEL or closed the dialog
                        alert.close();

                    }
                }

            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }


    public String getImageFileNameTrace() {
        return ImageFileNameTrace.get();
    }

    public void setImageFileNameTrace(String imageFileNameTrace) {
        this.ImageFileNameTrace.set(imageFileNameTrace);
    }

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }


    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }
    //endregion

}
