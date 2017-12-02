package View;

import Server.Server;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Properties;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class MyViewController implements Observer,IView {

        @FXML
        private MyViewModel viewModel;
        public MazeDisplayer mazeDisplayer;
        public javafx.scene.control.TextField txtfld_rowsNum;
        public javafx.scene.control.TextField txtfld_columnsNum;
        public javafx.scene.control.Button btn_generateMaze;
        public javafx.scene.control.Button btn_Solve;
        public javafx.scene.control.Menu menu_exit;
        public javafx.scene.control.Label generatorName;
        public javafx.scene.control.Label searchName;
        private static MediaPlayer mediaPlayer;



    //region String Property for Binding
    public StringProperty CharacterRow = new SimpleStringProperty();

    public StringProperty CharacterColumn = new SimpleStringProperty();

    public String getCharacterRow() {
        return CharacterRow.get();
    }

    public StringProperty characterRowProperty() {
        return CharacterRow;
    }

    public String getCharacterColumn() {
        return CharacterColumn.get();
    }

    public StringProperty characterColumnProperty() {
        return CharacterColumn;
    }



    public void setViewModel(MyViewModel viewModel) {

        this.viewModel = viewModel;
        java.lang.String ssound = "resources/Images/Jungle.mp3";
        Media sound = new Media(new File(ssound).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

        }

        @Override
        public void update(Observable o, Object arg) {
            if (o == viewModel) {
                btn_generateMaze.setDisable(false);
                btn_Solve.setDisable(false);
                displayMaze(viewModel.getMaze());
                mazeDisplayer.requestFocus();

            }
        }

    @Override
        public void displayMaze(Maze maze) {
            mazeDisplayer.setMaze(maze);
            int characterPositionRow = viewModel.getCharacterPositionRow();
            int characterPositionColumn = viewModel.getCharacterPositionColumn();
            mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    CharacterRow.set(characterPositionRow + "");
                    CharacterColumn.set(characterPositionColumn + "");
                }
            });

        }

        public void generateMaze() {
            btn_generateMaze.setDisable(true);

            if(mediaPlayer!= null) {
                mediaPlayer.stop();
            }

            mediaPlayer.play();

            try {
                int height = Integer.valueOf(txtfld_rowsNum.getText());
                int width = Integer.valueOf(txtfld_columnsNum.getText());
                viewModel.generateMaze(width, height);
                mazeDisplayer.requestFocus();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Please insert a number");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    alert.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    alert.close();

                }
                btn_generateMaze.setDisable(false);


            }



        }

        public void solveMaze() {
            btn_Solve.setDisable(true);
            viewModel.solveMaze();
        }

        public void KeyPressed(KeyEvent keyEvent) {
            viewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
            mazeDisplayer.requestFocus();

        }



    public void setResizeEvent(Scene scene) {


        scene.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                mazeDisplayer.setWidth(mazeDisplayer.getWidth()+(newSceneWidth.longValue()-oldSceneWidth.longValue()));
                mazeDisplayer.redraw();
            }

        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mazeDisplayer.setHeight(mazeDisplayer.getHeight()+(newSceneHeight.longValue()-oldSceneHeight.longValue()));
                mazeDisplayer.redraw();
            }

        });

    }
    //the About button
        public void About() {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("About.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("About");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                stage.show();
            } catch (Exception e) {

            }
        }
        //the properties button
    public void Properties() {
        try {
            Properties prop = new Properties();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Properties.fxml"));

            FileInputStream input = new FileInputStream("config.properties");
            prop.load(input);
            String mazeGen = prop.getProperty("MazeGenerator");
            String searchSol = prop.getProperty("SearchingAlgorithm");


            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Properties");

            Label generator = new Label(mazeGen);
            Label search = new Label(searchSol);

            GridPane root = (GridPane) scene.getRoot();
            root.add(generator, 1, 0);
            root.add(search, 1, 1);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();

        } catch (Exception e) {

        }
    }

    //the help button
    public void Help() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("help.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }
    public void CloseProp() {

    }
        public void onMazePress() {
            mazeDisplayer.requestFocus();
        }

        public void LoadMaze() {
            viewModel.LoadMaze();
        }
        public void SaveMaze() {
            viewModel.SaveMaze();
        }

        //the Exit button
        @FXML
        public void Exit(){
            viewModel.exit();
        }
    }
