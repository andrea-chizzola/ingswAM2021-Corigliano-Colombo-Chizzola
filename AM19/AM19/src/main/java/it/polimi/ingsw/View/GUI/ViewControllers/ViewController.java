package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;

public abstract class ViewController{

    private GUI gui;
    private ReducedGameBoard model;

    public ViewController(){
        gui = GUIHandler.getGUIReference();
        model = GUIHandler.getModelReference();
    }

    /**
     *
     * @return the instance of the GUI associated to the controller
     */
    public GUI getGUIReference(){
        return gui;
    }

    public ReducedGameBoard getModelReference(){
        return model;
    }
}
