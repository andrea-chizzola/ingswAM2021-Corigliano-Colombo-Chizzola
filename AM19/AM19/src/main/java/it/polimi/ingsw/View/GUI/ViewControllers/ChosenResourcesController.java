package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChosenResourcesController extends ViewController{

    @FXML
    private TextField coins1;
    @FXML
    private Button selectCoins1;
    @FXML
    private Button actionButton;
    @FXML
    private TextField shields1;
    @FXML
    private Button selectShields1;
    @FXML
    private TextField servants1;
    @FXML
    private Button selectServants1;
    @FXML
    private TextField stones1;
    @FXML
    private Button selectStones1;
    @FXML
    private TextField coins2;
    @FXML
    private Button selectCoins2;
    @FXML
    private TextField shields2;
    @FXML
    private Button selectShields2;
    @FXML
    private TextField servants2;
    @FXML
    private Button selectServants2;
    @FXML
    private TextField stones2;
    @FXML
    private Button selectStones2;

    private Accumulator accumulator;

    @FXML
    private void initialize(){
        bindAction();
    }

    private void bindAction(){
        actionButton.setOnAction(actionEvent -> {
            actionButton.getScene().getWindow().hide();
        });
        addEventMaterials(selectCoins1,coins1,"COIN");
        addEventMaterials(selectShields1,shields1,"SHIELD");
        addEventMaterials(selectServants1,servants1,"SERVANT");
        addEventMaterials(selectStones1,stones1,"STONE");

        addEventProducts(selectCoins2,coins2,"COIN");
        addEventProducts(selectShields2,shields2,"SHIELD");
        addEventProducts(selectServants2,servants2,"SERVANT");
        addEventProducts(selectStones2,stones2,"STONE");
    }

    private void addEventMaterials(Button button, TextField text, String resource){
        button.setOnAction(actionEvent -> {
            accumulator.setChosenMaterials(resource);
            accumulator.setChosenMaterials(text.getText());
            button.setOpacity(0.3);
            button.setDisable(true);
        });
    }

    private void addEventProducts(Button button, TextField text, String resource){
        button.setOnAction(actionEvent -> {
            accumulator.setChosenProducts(resource);
            accumulator.setChosenProducts(text.getText());
            button.setOpacity(0.3);
            button.setDisable(true);
        });
    }

    public void setAccumulator(Accumulator accumulator){
        this.accumulator = accumulator;
    }

}
