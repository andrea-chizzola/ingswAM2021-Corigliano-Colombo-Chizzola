package it.polimi.ingsw.View.GUI;
import javafx.application.Application;
import javafx.application.Platform;
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
    static <T> T loadRoot(Scene scene, String fxmlPath) {
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


}
