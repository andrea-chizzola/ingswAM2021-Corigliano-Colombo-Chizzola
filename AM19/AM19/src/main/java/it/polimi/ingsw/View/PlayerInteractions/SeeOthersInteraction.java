package it.polimi.ingsw.View.PlayerInteractions;

import it.polimi.ingsw.View.View;

/**
 * this class implements a method that is used to manage the request of showing other player's boards
 */
public class SeeOthersInteraction implements PlayerInteraction {

    /**
     * this attribute represents the nickname of the player whose board will be showed
     */
    private String target;

    /**
     * this method is the constructor of the class
     * @param target is the nickname of the player whose board will be showed
     */
    public SeeOthersInteraction(String target){
        this.target = target;
    }

    /**
     * this method manages the interaction with a player
     *
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.showOthers(target);
    }
}
