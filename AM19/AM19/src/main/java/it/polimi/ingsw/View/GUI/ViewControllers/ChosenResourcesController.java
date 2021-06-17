package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.View.GUI.Messages.Accumulator;
import it.polimi.ingsw.View.GUI.Messages.BuildDoProduction;
import it.polimi.ingsw.View.GUI.Messages.BuildMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * controller of the selection of the resources during the production
 */
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
    @FXML
    private Text materials;
    @FXML
    private Text products;

    private Accumulator accumulator;
    private BuildMessage builder;
    private int customMaterials;
    private int customProducts;

    @FXML
    private void initialize(){
        bindAction();
        builder = new BuildDoProduction();
    }

    public ChosenResourcesController(Accumulator accumulator, int customMaterials, int customProducts ){
        this.accumulator = accumulator;
        this.customMaterials = customMaterials;
        this.customProducts = customProducts;
    }

    /**
     * initializes the buttons
     */
    private void bindAction(){
        actionButton.setOnAction(actionEvent -> {
            getGUIReference().notifyInteraction(builder.buildMessage(accumulator));
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

        materials.setText("Custom Materials: " + customMaterials);
        products.setText("Custom Products: " + customProducts);
    }

    /**
     * this method adds an event handler to the button
     * it is useful for the buttons related to the chosen materials
     * @param button the button
     * @param text TextField from which the text is get
     * @param resource the resource associated with the button
     */
    private void addEventMaterials(Button button, TextField text, String resource){
        button.setOnAction(actionEvent -> {
            accumulator.setChosenMaterials(resource);
            accumulator.setChosenMaterials(text.getText());
            button.setOpacity(0.3);
            button.setDisable(true);
        });
    }

    /**
     * this method adds an event handler to the button
     * it is useful for the buttons related to the chosen products
     * @param button the button
     * @param text TextField from which the text is get
     * @param resource the resource associated with the button
     */
    private void addEventProducts(Button button, TextField text, String resource){
        button.setOnAction(actionEvent -> {
            accumulator.setChosenProducts(resource);
            accumulator.setChosenProducts(text.getText());
            button.setOpacity(0.3);
            button.setDisable(true);
        });
    }

    /**
     * this method sets the accumulator passed as parameter
     * @param accumulator the accumulator
     */
    public void setAccumulator(Accumulator accumulator){
        this.accumulator = accumulator;
    }

}
