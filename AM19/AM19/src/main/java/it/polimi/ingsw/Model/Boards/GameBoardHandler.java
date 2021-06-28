package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Messages.Enumerations.*;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.View.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface provides all the methods useful to interact with the model
 */
public interface GameBoardHandler {

    /**
     * This method distributes the leader cards and updates the view
     * @param file configuration file
     */
    void initializeGame(String file);

    /**
     * This method allows the current player to end his turn.
     * @throws InvalidActionException if the player can't do this action
     */
    void exit() throws InvalidActionException;

    /**
     * this method checks if the nickname belongs to the current player
     * @param nickname the nickname of the palyer
     * @return true if the nickname belongs to the current player, false otherwise
     */
    boolean isCurrentPlayer(String nickname);

    /**
     * This method manages the disconnection of a player
     * @param nickname the nickname of the player disconnected
     */
    void disconnectPlayer(String nickname);

    /**
     * This method manages the reconnection of a player
     * @param nickname the nickname of the player reconnected
     * @throws InvalidActionException if the player can't do this action
     */
    void reconnectPlayer(String nickname) throws InvalidActionException;

    /**
     * This method attaches the virtualView to the model
     * @param virtualView the virtual view observer of the model
     */
    void attachView(Update virtualView);

    /**
     * this method gets the production of the development card present in the indicated slot
     * @param slot the number of the slot
     * @return the production of the card present in the slot
     * @throws InvalidActionException if the slot does not exist or if it is empty
     */
    Production getDevProduction(int slot) throws InvalidActionException;

    /**
     * this method gets the production of the development card present in the indicated position
     * @param position the position of the leader card
     * @return the production of the card present in the indicated position
     * @throws InvalidActionException if the position is not correct
     */
    Production getLeaderProduction(int position) throws InvalidActionException;

    /**
     * this method discards the leader card in the indicated position without adding faith points to the player's faithTrack
     * @param leaderStatus the position of the leader card
     * @throws InvalidActionException if the position is not correct
     */
    void initializeLeaderCard(Map<Integer,Boolean> leaderStatus) throws InvalidActionException;

    /**
     * @return the production associated with the personal board
     */
    Production getBoardProduction();


    /**
     * This method allows to take a specific row from the MarketBoard of the current player.
     * @param row the row the player wants to take
     * @throws InvalidActionException if the action is invalid
     */
    void takeMarketRow(int row) throws InvalidActionException;

    /**
     * This method allows to take a specific column from the MarketBoard of the current player.
     * @param column the column the player wants to take
     * @throws InvalidActionException if the action is invalid
     */
    void takeMarketColumn(int column) throws InvalidActionException;


    /**
     * This method allows the current player to manage the marbles taken from the marketBoard.
     * @param marblesPlayer the marbles selected by the player
     * @param actions the actions to be performed on each marble
     * @param shelves the shelves where the resources associated to the marbles have to be inserted
     * @throws InvalidActionException if the action is invalid
     */
    void actionMarbles( List<Marble> marblesPlayer, List<PlayerAction> actions, List<Integer> shelves) throws InvalidActionException;

    /**
     * This method allows the current player to manage resources during the initialization.
     * @param resources the resources selected by the player
     * @param shelves the shelves where the resources have to be inserted
     * @throws InvalidActionException if the action is invalid
     */
    void insertResources(List<Resource> resources, List<Integer> shelves) throws InvalidActionException;


    /**
     * This method allows the current player two swap the content of two shelves of his warehouse.
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @throws InvalidActionException if the action is invalid
     */
    void swapWarehouse(int source, int target) throws InvalidActionException;


    /**
     * This method allows to buy the development card with color 'color' and level 'level' on the top of the deck
     * and it allows to add that card in the slot with number 'slot' of the current player.
     * @param slot the number of the slot in which the player wants to add the card
     * @param color the color of the card the player wants to add
     * @param level the level of the card the player wants to add
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    void buyCard(int slot, CardColor color, int level, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException;

    /**
     * This method allows the current player to activate the production of all the productions passed as parameters.
     * @param productions ArrayList of Production which represents all the productions the player wants to activate
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    void doProduction(ArrayList<Production> productions, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException;

    /**
     * This method allows the current player to activate the leader card in position with number 'position'.
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    void activateCard(int position) throws InvalidActionException;

    /**
     * This method allows the current player to remove the leader card in position with number 'position'.
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    void removeCard(int position) throws InvalidActionException;

}
