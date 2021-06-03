package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIHandler;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;

/**
 * this abstract class represents a controller of a Scene
 */
public abstract class ViewController{

    /**
     * this attribute is a reference to the current instance of GUI
     */
    private GUI gui;

    /**
     * this attribute is a reference to the current instance of ReducedGameBoard
     */
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

    /**
     *
     * @return the instance of the ReducedGameBoard associated to the controller
     */
    public ReducedGameBoard getModelReference(){
        return model;
    }
}
