package it.polimi.ingsw.View.GUI.ViewControllers;
import it.polimi.ingsw.View.GUI.GUI;

public abstract class ViewController{

    private GUI gui;

    /**
     * this method is used to attach a ClientController to a view
     * @param gui is the instance of GUI associated to the controller
     */
    public void attachGUIReference(GUI gui) {
        this.gui = gui;
    }

    /**
     * this method is used to notify a message to the current instance of GUI
     * @param message is the message to be modified
     */
    public void notifyMessage(String message){
        gui.notifyInteraction(message);
    }

}
