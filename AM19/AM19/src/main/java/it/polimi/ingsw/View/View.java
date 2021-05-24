package it.polimi.ingsw.View;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
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

    /**
     * this method is used to initialize the state of the view
     */
    void initialize();

    public void reply(boolean answer, String body, String nickName);

    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     * @param nickName represents the nickname of involved player
     * @param nickName represents the current state of the game
     */
    void showGameStatus(boolean answer, String body, String nickName, TurnType state);

    /**
     * this method is used to catch the player's selected turn
     * @param turns is the list of available turns
     * @param player is the nickname of the current player
     */
    void showAvailableTurns(List<String> turns, String player);

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    void showMarketUpdate(List<Marble> tray);

    /**
     * this method is used to catch an action of the player on a set of selected marbles
     * @param marblesTray represents the selected marbles
     * @param whiteModifications represents the available modifications for
     * @param nickName represents the nickname of the player who made the selection
     */
    //to be renamed . Magari si manda a tutti, così tutti vedono la selezione, ma solo il player specifico può agire
    void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName);


    /**
     * this method is used to show an update of the shared decks on the GameBoard
     * @param decks contains the top cards of each deck
     */
    void showDecksUpdate(Map<Integer,String> decks);

    /**
     * this method is used to show an update of one's warehouse and stringbox
     * @param warehouse represent the current state of the warehouse
     * @param strongBox represent the current state of the strongbox
     * @param nickName represents the name of the player affected by the changes
     */
    void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName);

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     * @param nickName represents the nickname of involved player
     */
    void showSlotsUpdate(Map<Integer, String> slots, String nickName);

    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     * @param nickName represents the nickname of involved player
     */
    void showLeaderCards(Map<Integer,String> cards, Map<Integer,ItemStatus> status,  String nickName);

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
     */
    void showEndGame(Map<String, Integer> players);

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
     * this method is used to catch the LeaderCards selected by a player
     */
    void selectLeaderAction();

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    void selectMarketAction();


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

    /**
     * this method is used to catch a swap in the Warehouse
     */
    boolean swapAction();
}

