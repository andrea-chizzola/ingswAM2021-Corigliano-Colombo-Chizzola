package it.polimi.ingsw.View.GUI.ViewControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MarketboardController extends ViewController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button column1;

    @FXML
    private Button column2;

    @FXML
    private Button column3;

    @FXML
    private Button column4;

    @FXML
    private Button row1;

    @FXML
    private Button row2;

    @FXML
    private Button row3;

    List<ImageView> viewTray;
    List<Button> positions;


    @FXML
    public void initialize(){
        column1.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("column1"));
        column2.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("column2"));
        column3.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("column3"));
        column4.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("column4"));
        row1.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("row1"));
        row2.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("row2"));
        row3.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                System.out.println("row3"));
        initializeTray();

        positions = List.of(column1, column2, column3, column4, row1, row2, row3);
    }

    private void initializeTray(){

        viewTray = new LinkedList<>();
        Image image = new Image("/Images/market/MarbleWhite.PNG");
        ImageView view = new ImageView(image);
        view.setFitWidth(33);
        view.setFitHeight(33);
        view.setLayoutX(227);
        view.setLayoutY(60);
        viewTray.add(view);

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++) {
                image = new Image("/Images/market/MarbleWhite.PNG");
                view = new ImageView(image);
                view.setFitWidth(33);
                view.setFitHeight(33);
                view.setLayoutX(j*34+104);
                view.setLayoutY(i*34+93);
                view.setVisible(true);
                viewTray.add(view);
            }
        }
        mainPane.getChildren().addAll(viewTray);
    }

    public void showMarketUpdate(List<String> tray) {
        for(int i=0; i<viewTray.size(); i++){
            Image image = new Image(tray.get(i));
            viewTray.get(i).setImage(image);
        }
    }

    public void disableButtons(){
        for(Button b : positions){
            b.setVisible(false);
        }
    }

    public void activateButtons(){
        for(Button b : positions){
            b.setVisible(true);
        }
    }

    public void showWindow(){
        ((Stage) mainPane.getScene().getWindow()).show();
    }

    public void hideWindow(){
        mainPane.getScene().getWindow().hide();
    }

}
