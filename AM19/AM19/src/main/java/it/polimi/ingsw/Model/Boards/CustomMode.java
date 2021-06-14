package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Messages.Enumerations.*;

import java.util.*;

/**
 * public interface containing endTurnAction method which sets a new current player in case of a multiplayer match and manages Lorenzo's actions in case of a single player match
 */
public interface CustomMode {

    /**
     * changes the current player in a multiplayer match or manages Lorenzo's actions in a single player match
     * @param gameBoard represents the game board
     */
    void endTurnAction(GameBoard gameBoard);

    /**
     * adds faith to the other players (or to Lorenzo in a single player match) when a resource is discarded
     * @param amount indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    void addFaithToOthers(int amount, GameBoard gameBoard);

    /**
     * @param boards contains the boards related to each player
     * @return returns a map (nickName - totalPoints)
     */
    String findWinner(ArrayList<Board> boards);

    /**
     * @return Optional Integer which represent, if not empty, the faith points of Lorenzo
     */
    Optional<Integer> showFaithLorenzo();

    /**
     * @return Optional List of ItemStatus which represents, if not empty, the status of the pope sections of Lorenzo
     */
    Optional<List<ItemStatus>> showSectionsLorenzo();

    /**
     * @return Optional String which represents, if not empty, the ID of the top token
     */
    Optional<String> showTopToken();
}
