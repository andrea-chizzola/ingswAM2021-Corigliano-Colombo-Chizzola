package it.polimi.ingsw;

/**
 * interface containing the necessary methods for managing the methods to buy a card
 */
public interface Requirements {

    /**
     * @param board represents the board associated to a player
     * @return returns true if the player related to the parameter board has the necessary requirements to buy the card
     */
    boolean checkReq(Board board);

    /**
     * buyCard() method buys a card and assigns it to the player related to the parameter board
     * @param board represents the board associated to a player
     */
    void buyCard(Board board);

}
