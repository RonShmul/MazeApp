package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.PointLight;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    Maze getMaze();
    Solution getSolution();
    void SolveMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void LoadMaze();
    void SaveMaze();
    void exit();
    void setStage(Stage stage);
}
