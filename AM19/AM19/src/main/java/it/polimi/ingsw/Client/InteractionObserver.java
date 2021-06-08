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
    void updateInteraction(PlayerInteraction interaction);

    /**
     * this method is used to notify a performed interaction
     * @param message is the representation of the interaction
     */
    void updateInteraction(String message);

    /**
     * this method is used to notify the SOLO game
     * @param message is the representation of the interaction
     */
    void updateInteractionSolo(String message);

    /**
     * this method is used to notify the nickname chosen by the player
     * @param nickname is the nickname chosen by the player
     */
    void updatePersonalNickname(String nickname);

    /**
     * this method is used to close the client
     */
    void close();

}
