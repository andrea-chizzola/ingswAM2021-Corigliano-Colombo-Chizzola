package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * public class representing development cards and inheriting from abstract class Card
 */
public class DevelopmentCard extends Card{

    /**
     * DevColor represents the color associated to a development card, one between GREEN, PURPLE, BLUE and YELLOW
     * level represents the level associated to a development card, an integer from 1 to 3
     */
    private final CardColor color;
    private final int level;

    /**
     * @param victoryPoint indicates the victory points associated to the card
     * @param specialEffect indicates the production of a development card or the special effect of a leader card
     * @param requirements indicates the necessary requirements to buy the card
     * @param color indicates the color associated to the development card
     * @param level indicates the level associated to the development card
     */
    public DevelopmentCard(int victoryPoint, SpecialEffect specialEffect, Requirements requirements, CardColor color, int level) {

        super(victoryPoint, specialEffect, requirements);
        this.color = color;
        this.level = level;

    }

    /**
     * @return returns the color of the development card, one between BLUE, GREY, YELLOW and PURPLE
     */
    public CardColor getCardColor(){
        return color;
    }

    /**
     * @return returns the level of the development card which goes from 1 to 3
     */
    public int getCardLevel(){
        return level;
    }


    /**
     * This method checks if the requirements of the card are met, otherwise an InvalidActionException is thrown.
     * @param board
     * @param shelves
     * @param quantity
     * @param strongbox
     * @return true if the player related to the parameter board has the necessary requirements to buy the card
     * @throws InvalidActionException
     */
    @Override
    public boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        return super.checkReq(board, shelves, quantity, strongbox);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return level == that.level &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, level);
    }
}
