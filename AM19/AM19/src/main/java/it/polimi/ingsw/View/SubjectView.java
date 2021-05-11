package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ViewObserver;

/**
 * this interface implements the methods to manage subject-observer interaction between the View and the Client
 */
public interface SubjectView {

    /**
     * this method is used to attach a Client to a view
     * @param observer is the observer to be attached
     */
    void attachObserver(ViewObserver observer);

    /**
     * this method is used to notify a Client of an action
     * @param message is the String that represent the action
     */
    void notifyObserver(String message);
}
