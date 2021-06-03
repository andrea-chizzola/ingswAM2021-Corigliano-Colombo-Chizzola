package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import it.polimi.ingsw.View.GUI.Messages.BuildMarketSelection;
import it.polimi.ingsw.View.GUI.Messages.BuildMessage;
import it.polimi.ingsw.View.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MarketboardController extends ViewController implements HelperWindow{

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
    Accumulator accumulator;
    BuildMessage builder;
    private final String path = "/Images/market/";


    @FXML
    public void initialize(){
        column1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("COLUMN", 1));
        column2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("COLUMN", 2));
        column3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("COLUMN", 3));
        column4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("COLUMN", 4));
        row1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("ROW", 1));
        row2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("ROW", 2));
        row3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> doAction("ROW", 3));

        initializeTray();
        positions = List.of(column1, column2, column3, column4, row1, row2, row3);
        disableButtons();
    }

    /**
     * this method is called when a row or a column is clicked to notify
     * the interaction of the player to the observer of the view
     * @param tray is the kind of selection (row or column)
     * @param position is the selected position
     */
    private void doAction(String tray, int position){
        accumulator.setMarketTray(tray);
        accumulator.setMarketNumber(position);
        disableButtons();
        hideWindow();
        getGUIReference().notifyInteraction(builder.buildMessage(accumulator));

    }

    /**
     * this method is used to initialize the market tray
     */
    private void initializeTray(){

        viewTray = new LinkedList<>();
        Image image;
        ImageView view;

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++) {
                image = new Image(path+"MarbleWhite.PNG");
                view = new ImageView(image);
                view.setFitWidth(33);
                view.setFitHeight(33);
                view.setLayoutX(j*34+104);
                view.setLayoutY(i*34+93);
                view.setVisible(true);
                viewTray.add(view);
            }
        }

        image = new Image(path+"MarbleWhite.PNG");
        view = new ImageView(image);
        view.setFitWidth(33);
        view.setFitHeight(33);
        view.setLayoutX(227);
        view.setLayoutY(60);
        viewTray.add(view);
        mainPane.getChildren().addAll(viewTray);
    }

    /**
     * this method is used to update the market tray
     * @param tray is the current state of the market tray
     */
    public void showMarketUpdate(List<Marble> tray) {
        for(int i=0; i<viewTray.size(); i++){
            Image image = new Image(path + tray.get(i).getImage());
            viewTray.get(i).setImage(image);
        }
    }

    /**
     * this method is used to disable all the buttons in the scene
     */
    public void disableButtons(){
        for(Button b : positions){
            b.setVisible(false);
        }
    }

    /**
     * this method is used to enable all the buttons in the scene
     */
    public void activateButtons(){
        for(Button b : positions){
            b.setVisible(true);
        }
    }

    /**
     * this method is used to set the reference to the message builder
     */
    public void setAccumulator(){
        this.accumulator = new Accumulator(GUIHandler.getGUIReference().getModelReference());
        this.builder = new BuildMarketSelection();
    }

    /**
     * this method is used to show the helper window
     */
    public void showWindow(){
        ((Stage) mainPane.getScene().getWindow()).show();
    }

    /**
     * this method is used to hide the helper window
     */
    public void hideWindow(){
        mainPane.getScene().getWindow().hide();
    }

}
