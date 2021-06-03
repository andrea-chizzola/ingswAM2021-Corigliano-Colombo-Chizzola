package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.ViewControllers.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.*;

import java.io.IOException;
public class GUIHandler extends Application {

    private static GUI gui;
    private static ReducedGameBoard model;
    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 600);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

    public static GUI getGUIReference(){
        return gui;
    }

    public static ReducedGameBoard getModelReference(){
        return model;
    }

    public static void setInstanceReference(GUI view, ReducedGameBoard data){
        gui = view;
        model = data;
    }

    public static void loadMainRoot(ViewController controller, String fxmlPath){
        loadRoot(controller, fxmlPath);
        currentStage.setWidth(1100);
        currentStage.setHeight(765);
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.setFullScreenExitHint("");

    }

    public static void loadRoot(ViewController controller, String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);

        Pane parent;
        try {
            parent = fxmlLoader.load();
            currentStage.getScene().setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }
    }

    public static void newWindow(ViewController controller, String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, width, length));
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createHelperWindow(ViewController controller,String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, length));
            stage.setOnCloseRequest(event -> stage.hide());

            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            //stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNonCloseableWindow(ViewController controller,String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, length));
            Alert alert = new Alert(Alert.AlertType.ERROR, "First, you should perform your action");
            stage.setOnCloseRequest(event -> {
                alert.showAndWait();
                event.consume();
            } );

            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
