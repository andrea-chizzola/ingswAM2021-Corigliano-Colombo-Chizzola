package it.polimi.ingsw.View;

import java.util.Map;

public interface Painter {
    /**
     * this method is used to paint a set of LeaderCards on the screen
     * @param position is the position of the leader card
     */
    void paintLeaderCards(int position);

    /**
     * this method is used to paint the Warehouse and its content
     */
    void paintWarehouse();

    /**
     * this method is used to paint the StrongBox and its content
     */
    void paintStrongBox();

    /**
     * this method is used to paint the FaithTrack of a player
     * @param nickname is the name of the player
     */
    void paintFaithTrack(String nickname);

    /**
     * this method is used to paint the top card in a player slot
     * @param position is the position of the slot
     */
    void paintCardSlots(int position);

    /**
     * this method is used to paint the top card in a shared deck
     * @param position is the position of the deck
     */
    void paintDeck(int position);

    /**
     * this method is used to paint the MarketBoard and its content
     */
    void paintMarketBoard();

    /**
     * this method is used to paint the end game scores
     * @param scores represent the points achieved by each player
     */
    void paintEndGame(Map<String, Integer> scores);


}
