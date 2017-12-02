package View;

import Model.*;
import ViewModel.MyViewModel;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        viewModel.setStage(primaryStage);

        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("MazeApp!");


        FXMLLoader fxl = new FXMLLoader();
        BorderPane root = fxl.load(getClass().getResource("MyView.fxml").openStream());


        Scene scene = new Scene(root, 800, 900);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //--------------

        MyViewController view = fxl.getController();
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        SetStageCloseEvent(primaryStage, model);
        primaryStage.show();

    }

    public void SetStageCloseEvent(Stage primaryStage, MyModel model) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.headerTextProperty().setValue("Are You Sure?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program

                    primaryStage.close();
                    model.stopServers();
                    model.threadPool.shutdown();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    alert.close();

                }
                windowEvent.consume();
            }
        });
    }

    public static void main(String[] args) {

        launch(args);
    }
}
