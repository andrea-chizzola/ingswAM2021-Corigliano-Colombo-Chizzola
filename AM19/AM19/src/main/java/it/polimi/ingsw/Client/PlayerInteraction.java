package it.polimi.ingsw.Client;

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

/**
 * this class implements a method used to manage a swap of the warehouse
 */
class SwapInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.swapAction();
    }
}

/**
 * this class implements a method to manage a the interaction of taking resources from the marketboard
 */
class TakeResourcesInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.selectMarketAction();
    }
}

/**
 * this class implements a method that is used to manage the interaction of activating and discarding leader cards
 */
class ManageLeaderInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.leaderAction();
    }
}

/**
 * this class implements a method that is used to manage the interaction of buying a card
 */
class BuyCardInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.buyCardAction();
    }
}

/**
 * this class implements a method that is used to manage a the interaction of doing a production
 */
class DoProductionInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view) {
        view.doProductionsAction();
    }
}

/**
 * this class implements a method that allow to manage the disconnection of the player
 */
class DisconnectInteraction implements PlayerInteraction{

    /**
     * this method manages the interaction with a player
     * @param view is the view on which the interaction will be performed
     */
    @Override
    public void manageInteraction(View view){
    }
}