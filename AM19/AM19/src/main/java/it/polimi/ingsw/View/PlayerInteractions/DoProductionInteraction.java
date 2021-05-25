package it.polimi.ingsw.View.PlayerInteractions;

import it.polimi.ingsw.View.View;

/**
 * this class implements a method that is used to manage a the interaction of doing a production
 */
public class DoProductionInteraction implements PlayerInteraction {

    /**
     * this method manages the interaction with a player
     *
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.doProductionsAction();
    }
}
