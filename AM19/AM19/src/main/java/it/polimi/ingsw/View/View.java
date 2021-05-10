package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.ItemStatus;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * this interface is used to implement a view in a client
 */
public interface View {

    void initialize();
    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     */
    void showAnswer(boolean answer, String body);

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    void showMarketUpdate(List<Marble> tray);

    /**
     * this method is used to show an update of the shared decks on the GameBoard
     * @param decks contains the top cards of each deck
     */
    void showDecksUpdate(Map<Integer,String> decks);

    /**
     * this method is used to show an update of one's warehouse
     * @param warehouse represent the current state of the warehouse
     */
    void showWarehouseUpdate(Map<Integer, ResQuantity> warehouse);

    /**
     * this method is used to show an update of one's strongbox
     * @param strongBox represent the current state of the strongbox
     */
    void showStrongboxUpdate(List<ResQuantity> strongBox);

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     */
    void showSlotsUpdate(Map<Integer, String> slots);

    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     */
    void showLeaderCards(Map<String, ItemStatus> cards);

    /**
     * this method is used to show an update of one's FaithTrack
     * @param faith is the amount of faith obtained by the players
     * @param sections is the state of the players' sections
     * @param faithLorenzo is the faith obtained by Lorenzo
     * @param sectionsLorenzo is the status of Lorenzo's sections
     */
    void showFaithUpdate(Map<String, Integer> faith, Map<String,
            List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo);

    /**
     * this method is used to show
     * @param action is the ID of the top token
     */
    void showTopToken(Optional<String> action);

    /**
     * this method is used to show the points achieved at the end of the game
     * @param players contains the name of the players and the points obtained
     * @param winner is the name of the winner
     */
    void showEndGame(Map<String, Integer> players, String winner);

    /**
     * this method is used to show the disconnection of a player
     * @param nickname is the name of the disconnected player
     */
    void showDisconnection(String nickname);

    /**
     * this method is used to add a player to the view
     */
    void newPlayer();

    /**
     * this method is used to catch the player's selected turn
     * @param turns is the list of available turns
     * @param player is the nickname of the current player
     */
    void selectTurnAction(LinkedList<String> turns, String player);

    /**
     * this method is used to catch the LeaderCards selected by a player
     * @param cards is the list of cards given to a player
     */
    void selectLeaderAction(List<String> cards);

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    void selectMarketAction();

    /**
     * this method is used to catch the decision of a player on a selection of marbles
     * @param marbles is a set of marbles
     */
    void marbleAction(List<Marble> marbles);

    /**
     * this method is used to catch the action of a player on a LeaderCard
     */
    void leaderAction();

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    void buyCardAction();

    /**
     * this method is used to catch the action of a player on their productions
     */
    void doProductionsAction();

    /**
     * this action is used to catch the resources chosen by a player
     */
    void getResourcesAction();

    /**
     * this method show the player's personal production.
     */
    void showPersonalProduction();


}
