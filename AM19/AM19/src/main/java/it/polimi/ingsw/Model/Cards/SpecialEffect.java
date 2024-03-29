package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.LinkedList;

/**
 * interface containing the methods for managing the production in case of development cards and special effects in case of leader cards
 */
public abstract class SpecialEffect {

    /**
     * Applies the special effect related to the leader card.
     * This method is overridden by each subclass, except for Production
     * @param board represents the board associated to a player
     */
    public void applyEffect(Board board){}

    /**
     *
     * @return an empty production. This method is overridden by the subclass Production
     * to return the class Production.
     */
    public Production getProduction() {
        //construction of an empty list of required materials
        LinkedList<ResQuantity> materials = new LinkedList<>();
        //construction of an empty list of products
        LinkedList<ResQuantity> products = new LinkedList<>();
        return new Production(materials, products, 0 , 0);
    }

    /**
     * @return a ResQuantity which represents the special effect
     */
    public abstract ResQuantity getSpecialEffect();

    public boolean isDiscount(){return false;}
    public boolean isWhiteMarble(){return false;}
    public boolean isExtraShelf(){return false;}

}
