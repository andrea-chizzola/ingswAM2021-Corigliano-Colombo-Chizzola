package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;

/**
 * public interface implemented by the actions made possible only in a single player match
 */
public abstract class Action {
    /**
     * this attribute represents the ID of the ActionToken
     */
    private String id;

    /**
     * this method is the constructor of the class
     * @param id is the id of the ActionToken
     */
    public Action(String id){
        this.id = id;
    }

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    public abstract void doAction(SinglePlayer singlePlayer);

    /**
      * @return the id of the ActionToken
     */
    public String getId() {
        return id;
    }
}
