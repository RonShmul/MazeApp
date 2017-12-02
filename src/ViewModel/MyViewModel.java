package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private int characterPositionRow; //For Binding
    private int characterPositionColumn; //For Binding


    public MyViewModel(IModel model){
        this.model = model;
    }

    public void setStage(Stage primaryStage) {
        model.setStage(primaryStage);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            characterPositionRow = model.getCharacterPositionRow();
            characterPositionColumn = model.getCharacterPositionColumn();

            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    public void solveMaze() {
        model.SolveMaze();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
    public void LoadMaze() {
        model.LoadMaze();
    }

    public void SaveMaze() {
        model.SaveMaze();
    }

    public void exit() {
        model.exit();
    }
}
