package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application  {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("/FXML/login.fxml"), 575, 534);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource(fxml));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }
}
