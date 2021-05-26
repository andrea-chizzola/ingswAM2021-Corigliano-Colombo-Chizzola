package it.polimi.ingsw.View.PlayerInteractions;

import it.polimi.ingsw.View.View;

/**
 * this interface implements a method to manage the interaction of a player
 */
public interface PlayerInteraction {
    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    void manageInteraction(View view);
}

