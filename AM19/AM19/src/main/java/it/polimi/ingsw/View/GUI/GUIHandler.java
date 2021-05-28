package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.ViewControllers.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIHandler {

    private static GUIHandler instance;
    private GUI gui;
    private ReducedGameBoard model;

    public static GUIHandler instance(){
        if (instance == null)
            instance = new GUIHandler();
        return instance;
    }

    public void setGUIinstance(GUI gui){
        this.gui = gui;
    }

    public GUI getGUIinstance(){
        return gui;
    }

    public ReducedGameBoard getModel() {
        return model;
    }

    public void setModel(ReducedGameBoard model) {
        this.model = model;
    }

    /**
     * this method is used to change the look of a given scene
     *
     * @param scene is the scene to be modified
     * @param fxmlPath is the path of the FXML file
     * @param <T> is the type of the returned controller
     * @return the controller of the scene
     */
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

    public static <T> T loadRoot(Scene scene, ViewController controller, String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);

        Pane parent;
        try {
            parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }

        return fxmlLoader.getController();
    }

    /**
     * this method is used to copen a new window
     *
     * @param fxmlPath is the path of the FXML file
     * @param <T> is the type of the returned controller
     * @return the controller of the scene
     */
    public static <T> T newWindow(String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));

        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();

            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }


    public static <T> T newWindow(ViewController controller, String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 500, 500));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }
}
