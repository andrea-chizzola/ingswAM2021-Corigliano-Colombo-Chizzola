package it.polimi.ingsw;

/**
 * interface containing the methods for managing the production in case of development cards and special effects in case of leader cards
 */
public interface SpecialEffect {

    /**
     * Applies the special effect related to the leader card (not implemented in Production class)
     * @param board represents the board associated to a player
     */
    public void applyEffect(Board board);

    //getProduction() method missing

}
