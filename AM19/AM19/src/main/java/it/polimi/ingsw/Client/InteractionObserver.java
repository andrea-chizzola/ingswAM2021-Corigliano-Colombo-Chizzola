package it.polimi.ingsw.Client;

import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;

/**
 * this interface implements a method to notify the interaction performed by a player
 */
public interface InteractionObserver {

    /**
     * this method is used to notify a performed interaction
     * @param interaction is the notified interaction
     */
    void notifyInteraction(PlayerInteraction interaction);

    /**
     * this method is used to notify the nickname chosen by the player
     * @param nickname is the nickname chosen by the player
     */
    void notifySelectedNickname(String nickname);
}
