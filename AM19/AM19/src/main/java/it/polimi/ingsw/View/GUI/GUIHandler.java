package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.ViewControllers.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GUIHandler extends Application {

    private static GUI gui;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 575, 534);
        stage.setScene(scene);
        ViewController controller = fxmlLoader.getController();
        controller.attachGUIReference(gui);
        controller.attachModelReference(gui.getModelReference());
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

    public static void setGUIReference(GUI reference){
        gui = reference;
    }

    public static <T> T loadRoot(Scene scene, String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));

        Pane parent;

        try {
            parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }

        return fxmlLoader.getController();
    }

    public static void loadRoot(Scene scene, ViewController controller, String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);

        Pane parent;
        try {
            parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }
    }

    public static <T> T newWindow(String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));

        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }


    public static void newWindow(ViewController controller, String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();
            // Hide this current window
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createHelperWindow(ViewController controller, String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            //stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.setOnCloseRequest(event -> stage.hide());
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
