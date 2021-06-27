package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.ViewControllers.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.*;

import java.io.IOException;

/**
 * this class contains utilities to manage the GUI,
 * and a reference to the elements that are shared among all the controllers of the GUI
 */
public class GUIHandler extends Application {

    /**
     * this attribute contains a reference to the current instance of the GUI
     */
    private static GUI gui;

    /**
     * this attribute contains a reference to the current instance of the ReducedGameBoard
     */
    private static ReducedGameBoard model;

    /**
     * this attribute contains a reference to the current active main Stage
     */
    private static Stage currentStage;

    private static final String windowName = "Masters of Renaissance";

    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 600);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle(windowName);
        stage.getIcons().add(new Image("Images/icon.png"));
        stage.show();
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    /**
     * GUIHandler contains a main, in which launch() is called.
     * This approach is required to avoid a common bug of JavaFX, that
     * prevents the jar from being created correctly.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * this method is used to retrieve the reference of the current instance of GUI
     * @return a reference to the current instance of GUI
     */
    public static GUI getGUIReference(){
        return gui;
    }

    /**
     * this method is used to retrieve the reference of the current instance of ReducedGameBoard
     * @return a reference to the current instance of ReducedGameBoard
     */
    public static ReducedGameBoard getModelReference(){
        return model;
    }

    /**
     * this method is used to set the current instance of view and ReducedGameBoard
     * @param view is the reference to the current instance of GUI
     * @param data is the reference to the current instance of ReducedGameBoard
     */
    public static void setInstanceReference(GUI view, ReducedGameBoard data){
        gui = view;
        model = data;
    }

    /**
     * this method is used to load a new scene in the current stage
     * @param controller is a reference to the controller of the scene
     * @param fxmlPath is the path of the xml file that contains the positions of the Nodes
     */
    public static void loadRoot(ViewController controller, String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);

        Pane parent;
        try {
            parent = fxmlLoader.load();
            currentStage.getScene().setRoot(parent);
        } catch (IOException e) {
            System.out.println("Cannot open a new window. Closing the game...");
            gui.notifyParsingError();
        }
    }

    /**
     * this method is used to load the main scene of the game
     * @param controller is a reference to the controller of the scene
     * @param fxmlPath is the path of the xml file that contains the positions of the Nodes
     */
    public static void loadMainRoot(ViewController controller, String fxmlPath){
        loadRoot(controller, fxmlPath);
        currentStage.setWidth(1100);
        currentStage.setHeight(765);
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.setFullScreenExitHint("");

    }

    /**
     * this method is used to create a scene in a new stage
     * @param controller is a reference to the controller of the scene
     * @param fxmlPath is the path of the xml file that contains the positions of the Nodes
     * @param width is the width of the stage
     * @param length is the length of the stage
     */
    public static void newWindow(ViewController controller, String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(new Scene(root, width, length));
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.getIcons().add(new Image("Images/icon.png"));
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Cannot open a new window. Closing the game...");
            gui.notifyParsingError();
        }
    }

    /**
     * this method is used to create an helper window in a new stage.
     * An helper window is an invisible window.
     *
     * @param controller is a reference to the controller of the scene
     * @param fxmlPath is the path of the xml file that contains the positions of the Nodes
     * @param width is the width of the stage
     * @param length is the length of the stage
     */
    public static void createHelperWindow(ViewController controller,String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, length));
            stage.setOnCloseRequest(event -> stage.hide());
            stage.setTitle(windowName);
            stage.getIcons().add(new Image("Images/icon.png"));
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);

        }
        catch (IOException e) {
            System.out.println("Cannot open a new window. Closing the game...");
            gui.notifyParsingError();
        }
    }

    /**
     * this method is used to create an window in a new stage.
     * This kind of window cannot be closed directly. An exit button should be employed.
     *
     * @param controller is a reference to the controller of the scene
     * @param fxmlPath is the path of the xml file that contains the positions of the Nodes
     * @param width is the width of the stage
     * @param length is the length of the stage
     */
    public static void createNonCloseableWindow(ViewController controller,String fxmlPath, int width, int length){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, length));
            Alert alert = new Alert(Alert.AlertType.WARNING, "You can't close this window");
            stage.setOnCloseRequest(event -> {
                alert.showAndWait();
                event.consume();
            } );

            stage.setTitle(windowName);
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image("Images/icon.png"));
            stage.show();

        }
        catch (IOException e) {
            System.out.println("Cannot open a new window. Closing the game...");
            gui.notifyParsingError();
        }
    }
}
