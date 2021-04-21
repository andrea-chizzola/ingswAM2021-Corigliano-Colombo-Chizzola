package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;

/**
 * public interface implemented by the actions made possible only in a single player match
 */
public interface Action {

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    void doAction(SinglePlayer singlePlayer);


}
