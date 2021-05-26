package it.polimi.ingsw.View.GUI;
import it.polimi.ingsw.Client.InteractionObserver;
import it.polimi.ingsw.Client.MessageSender;
import it.polimi.ingsw.GUI.LoadingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * this class implements methods to get the currently active controller and modify the current scene
 *
 * this class implements a singleton pattern
 */
public class GUIHandler {
    //qui salviamo tutti i controller di cui abbiamo bisogno e mettiamo setter/getter per prenderli.
    //Grazie al pattern Singleton, li salviamo con get.Instance(), .set().

    /**
     * this attribute represents the reference to the current instance of GUIHandler
     */
    private static GUIHandler instance;

    //mettere riferimento a GUI
    //private static viewObserver

    //handler = GUIHandler.instance();
    //handler.update(MessageFactory.Build...);

    private Scene currentScene;

    private InteractionObserver interactionObserver;

    private MessageSender messageSender;

    private LoadingController loadingController;

    private GUIHandler(){}

    /**
     * this method is used to retrieve an instance of GUIHandler
     *
     * @return the current instance of GUIHandler
     */
    public static GUIHandler instance(){
        if(instance == null){
            instance = new GUIHandler();
        }
        return instance;
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
        FXMLLoader fxmlLoader = new FXMLLoader(GUIHandler.class.getResource(fxmlPath));

        Pane parent;

        try {
            parent = fxmlLoader.load();
            scene.setRoot(parent);
        } catch (IOException e) {
            //Chiudo il client
        }

        return fxmlLoader.getController();
    }

    public static <T> T newWindow(String fxmlPath){
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(GUIHandler.class.getResource(fxmlPath));

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

    public static GUIHandler getInstance() {
        return instance;
    }

    public static void setInstance(GUIHandler instance) {
        GUIHandler.instance = instance;
    }

    public InteractionObserver getInteractionObserver() {
        return interactionObserver;
    }

    public void setInteractionObserver(InteractionObserver interactionObserver) {
        this.interactionObserver = interactionObserver;
    }

    public MessageSender getViewObserver() {
        return messageSender;
    }

    public void setViewObserver(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public LoadingController getLoadingController() {
        return loadingController;
    }

    public void setLoadingController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }
}
