package it.polimi.ingsw;

/**
 * public interface implemented by the actions made possible only in a single player match
 */
public interface Action {

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    void doAction(SinglePlayer singlePlayer) throws LorenzoWonException;


}
