package it.polimi.ingsw.Model.Boards;

import java.util.ArrayList;

/**
 * public interface containing endTurnAction method which sets a new current player in case of a multiplayer match and manages Lorenzo's actions in case of a single player match
 */
public interface CustomMode {

    /**
     * changes the current player in a multiplayer match or manages Lorenzo's actions in a single player match
     * @param gameBoard represents the game board
     */
    public void endTurnAction(GameBoard gameBoard);

    /**
     * adds faith to the other players (or to Lorenzo in a single player match) when a resource is discarded
     * @param amount indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    public void addFaithToOthers(int amount, GameBoard gameBoard);

    /**
     * @param boards contains the boards related to each player
     * @return returns the message showed to the players when the match is over
     */
    public String findWinnerMessage(ArrayList<Board> boards);

}
