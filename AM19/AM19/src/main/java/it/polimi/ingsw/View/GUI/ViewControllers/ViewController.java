package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;

public abstract class ViewController{

    private GUI gui;
    private ReducedGameBoard model;

    /**
     * this method is used to attach a ClientController to a view
     * @param gui is the instance of GUI associated to the controller
     */
    public void attachGUIReference(GUI gui) {
        this.gui = gui;
    }

    /**
     *
     * @return the instance of the GUI associated to the controller
     */
    public GUI getGUIReference(){
        return gui;
    }

    public void attachModelReference(ReducedGameBoard model){
        this.model = model;
    }

    public ReducedGameBoard getModelReference(){
        return model;
    }
}
