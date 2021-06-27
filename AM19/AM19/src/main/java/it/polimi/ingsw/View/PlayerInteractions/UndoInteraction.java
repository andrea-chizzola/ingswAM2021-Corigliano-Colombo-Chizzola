package it.polimi.ingsw.View.PlayerInteractions;

import it.polimi.ingsw.View.View;

/**
 * this class implements a method that is used to undo the current action
 */
public class UndoInteraction implements PlayerInteraction {

    /**
     * this method manages the interaction with a player
     *
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.undoAction();
    }
}
