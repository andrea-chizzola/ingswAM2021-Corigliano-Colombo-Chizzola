package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController.InteractionObserver;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;

/**
 * this interface implements the methods to manage subject-observer interaction between the View and the Client
 */
public interface SubjectView {

    /**
     * this method is used to attach a ClientController to a view
     * @param observer is the observer to be attached
     */
    void attachInteractionObserver(InteractionObserver observer);

    /**
     * this method is used to notify a performed interaction
     * @param interaction is the notified interaction
     */
    void notifyInteraction(PlayerInteraction interaction);

    /**
     * this method is used to notify a performed interaction
     * @param message is the representation of the interaction
     */
    void notifyInteraction(String message);

    /**
     * this method is used to notify the SOLO game
     * @param message is the representation of the interaction
     */
    void notifySoloInteraction(String message);

    /**
     * this method is used to notify the nickname selected by a player to the observers
     * @param nickname is the name chosen by the players
     */
    void notifyNickname(String nickname);

    /**
     * this method is used to notify a parsing error to the observer
     */
    void notifyClose();
}
